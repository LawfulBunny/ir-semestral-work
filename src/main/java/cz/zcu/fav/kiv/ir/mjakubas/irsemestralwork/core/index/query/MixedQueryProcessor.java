package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.Sets;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.DocumentProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.Index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TF-IDF with boolean expressions.
 */
public class MixedQueryProcessor extends QueryProcessor {
    public MixedQueryProcessor(Index index) {
        super(index);
    }

    private final List<String> tokens = new ArrayList<>();

    @Override
    protected List<Long> getQueryRelatedDocuments(List<String> queryWords) {
        return Sets.newHashSet(processExpression(Iterators.peekingIterator(tokens.iterator()), queryWords.iterator())).stream().toList();
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
    /* recursive descend basically */
    /* only using guava for set operations */
    /* each step returns relevant documents that passed the previous expression */

    private static final String TOKEN_PARENTHESES_START = "(";
    private static final String TOKEN_PARENTHESES_END = ")";
    private static final String TOKEN_NOT = "NOT";
    private static final String TOKEN_AND = "AND";
    private static final String TOKEN_OR = "OR";

    private List<Long> processExpression(PeekingIterator<String> remainingQuery, Iterator<String> remainingWords) {
        return processOrExpression(remainingQuery, remainingWords);
    }

    /* ----- */

    private List<Long> processOrExpression(PeekingIterator<String> remainingQuery, Iterator<String> remainingWords) {
        List<Long> orCollector = processAndExpression(remainingQuery, remainingWords);
        while (remainingQuery.hasNext() && remainingQuery.peek().equals(TOKEN_OR)) {
            remainingQuery.next();
            orCollector.addAll(processOrExpression(remainingQuery, remainingWords));
        }
        return orCollector;
    }

    /* ----- */

    private List<Long> processAndExpression(PeekingIterator<String> remainingQuery, Iterator<String> remainingWords) {
        List<Long> andCollector1 = processNotModifier(remainingQuery, remainingWords);
        while (remainingQuery.hasNext() && remainingQuery.peek().equals(TOKEN_AND)) {
            remainingQuery.next();
            andCollector1.retainAll(processNotModifier(remainingQuery, remainingWords));
        }
        return andCollector1;
    }

    /* ----- */

    private List<Long> processNotModifier(PeekingIterator<String> remainingQuery, Iterator<String> remainingWords) {
        boolean negate = false;
        if (remainingQuery.peek().equals(TOKEN_NOT)) {
            remainingQuery.next();
            negate = true;
        }
        List<Long> processed = processContentExpression(remainingQuery, remainingWords);

        if (negate) {
            List<Long> a2 = index.exposeDocuments().asMultimap().keys().asList();
            processed = Sets.difference(Sets.newHashSet(a2), Sets.newHashSet(processed)).stream().toList();
        }

        return processed;
    }

    /* ----- */

    private List<Long> processContentExpression(PeekingIterator<String> remainingQuery, Iterator<String> remainingWords) {
        String token = remainingQuery.peek();
        if (token.equals(TOKEN_PARENTHESES_START)) {
            remainingQuery.next();
            List<Long> result = processSubQuery(remainingQuery, remainingWords);
            remainingQuery.next(); // for /
            return result;
        }
        return processSimpleTerm(remainingQuery, remainingWords);
    }

    /* ----- */

    private List<Long> processSubQuery(PeekingIterator<String> remainingQuery, Iterator<String> remainingWords) {
        return processExpression(remainingQuery, remainingWords);
    }

    private List<Long> processSimpleTerm(PeekingIterator<String> remainingQuery, Iterator<String> remainingWords) {
        remainingQuery.next();
        return new ArrayList<>(index.exposeInvertedIndex().get(remainingWords.next()).stream().map(indexedDocument -> indexedDocument.getProcessedDocument().document().id()).toList());
    }
}
