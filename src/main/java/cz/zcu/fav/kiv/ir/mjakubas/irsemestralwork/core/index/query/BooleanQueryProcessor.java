package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueriedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.DocumentProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.Index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BooleanQueryProcessor extends QueryProcessor {

    public BooleanQueryProcessor(Index index) {
        super(index);
    }

    private final List<String> tokens = new ArrayList<>();

    @Override
    protected List<Long> getQueryRelatedDocuments(List<String> queryWords) {
        return null;
    }

    @Override
    protected List<String> getQueryTerms(String text, DocumentProcessor documentProcessor) {
        String[] words = text.split(" +");
        tokens.clear();
        for (String word : words) {
            if (word.startsWith("(")) {
                tokens.add("(");
                tokens.add(word.substring(1));
            } else if (word.endsWith(")")) {
                tokens.add(word.substring(0, word.length() - 1));
                tokens.add(")");
            } else {
                tokens.add(word);
            }
        }
        String clearText = text.replaceAll("(AND)|(OR)|(NOT)|[()]", " ");
        return documentProcessor.processDocument(new Document(-1, null, clearText, clearText)).processedFields().get(0).words();
    }

    /* CUSTOM SEARCH IMPLEMENTATION */

    private static final String TOKEN_PARENTHESES_START = "(";
    private static final String TOKEN_PARENTHESES_END = ")";
    private static final String TOKEN_NOT = "NOT";
    private static final String TOKEN_AND = "AND";
    private static final String TOKEN_OR = "OR";

    private List<Long> processExpression(Iterator<String> remainingQuery, Iterator<String> remainingWords) {
        return processOrExpression(remainingQuery, remainingWords);
    }

    /* ----- */

    private List<Long> processOrExpression(Iterator<String> remainingQuery, Iterator<String> remainingWords) {
        List<Long> orCollector = processAndExpression(remainingQuery, remainingWords);
        while (remainingQuery.hasNext() && remainingQuery.next().equals(TOKEN_OR)) {
            orCollector.addAll(processOrExpression(remainingQuery, remainingWords));
        }
        return orCollector;
    }

    /* ----- */

    private List<Long> processAndExpression(Iterator<String> remainingQuery, Iterator<String> remainingWords) {
        List<Long> andCollector1 = processNotModifier(remainingQuery, remainingWords);
        while (remainingQuery.hasNext() && remainingQuery.next().equals(TOKEN_AND)) {
            andCollector1.retainAll(processNotModifier(remainingQuery, remainingWords));
        }
        return andCollector1;
    }

    /* ----- */

    private List<Long> processNotModifier(Iterator<String> remainingQuery, Iterator<String> remainingWords) {
        boolean negate = false;
        PeekingIterator<String> tokenOracle = Iterators.peekingIterator(remainingQuery);
        if (tokenOracle.peek().equals(TOKEN_NOT)) {
            remainingQuery.next();
            negate = true;
        }

        List<Long> processed = processContentExpression(remainingQuery, remainingWords);

        if (negate) {
            processed.removeAll(index.exposeDocuments().asMultimap().keys());
        }

        return processed;
    }

    /* ----- */

    private List<Long> processContentExpression(Iterator<String> remainingQuery, Iterator<String> remainingWords) {
        String token = remainingQuery.next();
        if (token.equals(TOKEN_PARENTHESES_START)) {
            return processSubQuery(remainingQuery, remainingWords);
        }
        return processSimpleTerm(remainingQuery, remainingWords);
    }

    /* ----- */

    private List<Long> processSubQuery(Iterator<String> remainingQuery, Iterator<String> remainingWords) {
        return processExpression(remainingQuery, remainingWords);
    }

    private List<Long> processSimpleTerm(Iterator<String> remainingQuery, Iterator<String> remainingWords) {
        remainingQuery.next();
        return index.exposeInvertedIndex().get(remainingWords.next()).stream().map(indexedDocument -> indexedDocument.getProcessedDocument().document().id()).toList();
    }

    @Override
    protected List<QueriedDocument> performSearch(List<String> queryWords, Map<String, Double> queryVector, List<Long> documentIds) {
        List<QueriedDocument> result = new ArrayList<>();
        documentIds.forEach(aLong -> {
            result.add(new QueriedDocument(1, 1, index.exposeDocuments().get(aLong).document()));
        });
        return result;
    }
}
