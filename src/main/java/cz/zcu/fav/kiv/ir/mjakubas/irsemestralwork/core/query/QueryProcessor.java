package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.query;

import com.google.common.collect.ImmutableList;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueriedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueryResult;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.Index;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.BasicPreprocessing;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.DocumentProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.stemmer.CzechStemmerAggressive;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.tokenizer.AdvancedTokenizer;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.query.utils.Vector;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Used to perform query over a given index.
 */
@AllArgsConstructor
public abstract class QueryProcessor {

    private static final Logger LOGGER = LogManager.getLogger(QueryProcessor.class);

    private final Index index;

    /**
     * Performs query of text over objects index. Returns up to nHit results.
     *
     * @param text Queried text.
     * @param nHit Interested in n results.
     * @return Queried documents up to nHit results.
     */
    public QueryResult performQuery(String text, int nHit) {
        LOGGER.trace("Performing query for '{}'. Max results: {}", text, nHit);
        /* get all unique query terms */
        Set<String> queryWords = getQueryTerms(text);
        LOGGER.trace("Query words '{}'", queryWords);
        /* create query vector */
        Map<String, Double> queryVector = createQueryVector(queryWords);
        LOGGER.trace("Query vector '{}'", queryVector);
        /* get query-related documents */
        List<Integer> documentIds = getQueryRelatedDocuments(queryWords);
        LOGGER.trace("Document ids '{}'", documentIds);
        /* perform search based on query */
        List<QueriedDocument> returnedDocuments = performSearch(queryWords, queryVector, documentIds);
        LOGGER.trace("Query returned documents: {}",
                returnedDocuments.stream().map(queriedDocument -> queriedDocument.document().id()));
        return new QueryResult(ImmutableList.copyOf(returnedDocuments));
    }

    protected Set<String> getQueryTerms(String text) {
        Document queryAsDocument = new Document(-1, null, null, text);
        DocumentProcessor documentProcessor = new DocumentProcessor(new BasicPreprocessing(new CzechStemmerAggressive(), new AdvancedTokenizer()));

        return documentProcessor.processDocument(queryAsDocument).processedFields().get(1).wordFrequencies().keySet();
    }

    protected Map<String, Double> createQueryVector(Set<String> queryWords) {
        double[] vector = new double[index.exposeInvertedIndex().size()];
        int i = 0;
        for (String term : queryWords) {
            vector[i++] = 1;
        }
        Vector.normalize(vector);
        i = 0;
        Map<String, Double> result = new HashMap<>();
        for (String term : queryWords) {
            result.put(term, vector[i++]);
        }
        return result;
    }

    protected List<QueriedDocument> performSearch(Set<String> queryWords, Map<String, Double> queryVector, List<Integer> documentIds) {
        // calculate rank for each document
        List<QueriedDocument> queriedDocuments = new ArrayList<>();
        for (Integer id : documentIds) {
            double rank = 0;
            // cycle through each query term
            for (String term : queryWords) {
                double dv = 0; //getDocumentValue(term, id);
                double qv = queryVector.get(term);
                rank += dv * qv;
            }
            queriedDocuments.add(new QueriedDocument(-1, rank, null));
        }

        return queriedDocuments;
    }

    protected abstract List<Integer> getQueryRelatedDocuments(Set<String> queryWords);
}
