package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query;

import com.google.common.collect.ImmutableList;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueriedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueryResult;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.DocumentProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.Index;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.utils.Vector;
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

    protected final Index index;

    /**
     * Performs query of text over objects index. Returns up to nHit results.
     *
     * @param text Queried text.
     * @param nHit Interested in n results.
     * @return Queried documents up to nHit results.
     */
    public QueryResult performQuery(String text, int nHit, DocumentProcessor documentProcessor) {
        LOGGER.trace("Performing query for '{}'. Max results: {}", text, nHit);
        /* get all unique query terms */
        List<String> queryWords = getQueryTerms(text, documentProcessor);
        LOGGER.trace("Query words '{}'", queryWords);
        /* create query vector */
        Map<String, Double> queryVector = createQueryVector(queryWords);
        LOGGER.trace("Query vector '{}'", queryVector);
        /* get query-related documents */
        List<Long> documentIds = getQueryRelatedDocuments(queryWords);
        LOGGER.trace("Document ids '{}'", documentIds);
        /* perform search based on query */
        List<QueriedDocument> returnedDocuments = performSearch(queryWords, queryVector, documentIds);
        LOGGER.trace("Query returned documents: {}",
                returnedDocuments.stream().map(queriedDocument -> queriedDocument.document().id()));
        return new QueryResult(ImmutableList.copyOf(returnedDocuments));
    }

    protected List<String> getQueryTerms(String text, DocumentProcessor documentProcessor) {
        Document queryAsDocument = new Document(-1, null, text, text);

        return documentProcessor.processDocument(queryAsDocument).processedFields().get(0).wordFrequencies().keySet().stream().toList();
    }

    protected Map<String, Double> createQueryVector(List<String> queryWords) {
        double[] vector = new double[index.exposeInvertedIndex().size()];
        int i = 0;
        Set<String> cleanUp = new HashSet<>(queryWords);
        for (String term : cleanUp) {
            vector[i++] = 1;
        }
        Vector.normalize(vector);
        i = 0;
        Map<String, Double> result = new HashMap<>();
        for (String term : cleanUp) {
            result.put(term, vector[i++]);
        }
        return result;
    }

    protected List<QueriedDocument> performSearch(List<String> queryWords, Map<String, Double> queryVector, List<Long> documentIds) {
        // calculate rank for each document
        List<QueriedDocument> queriedDocuments = new ArrayList<>();
        for (long id : documentIds) {
            double rank = 0;
            // cycle through each query term
            for (String term : queryWords) {
                double dv = 0; //getDocumentValue(term, id);
                double qv = queryVector.get(term);
                rank += dv * qv;
            }
            queriedDocuments.add(new QueriedDocument(rank, index.exposeDocuments().get(id).document()));
        }

        return queriedDocuments;
    }

    protected abstract List<Long> getQueryRelatedDocuments(List<String> queryWords);
}
