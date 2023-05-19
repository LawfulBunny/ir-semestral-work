package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query;

import com.google.common.collect.ImmutableList;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueriedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueryResult;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.DocumentProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.Index;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.IndexedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.utils.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Used to perform query over a given index.
 */
public abstract class QueryProcessor {

    private static final Logger LOGGER = LogManager.getLogger(QueryProcessor.class);

    protected final Index index;

    public QueryProcessor(Index index) {
        this.index = index;
    }

    /**
     * Performs query of text over objects index. Returns up to nHit results.
     *
     * @param text Queried text.
     * @param nHit Interested in n results.
     * @return Queried documents up to nHit results.
     */
    public QueryResult performQuery(String text, int nHit, DocumentProcessor documentProcessor) {
        if (text.equals("")) {
            return new QueryResult(ImmutableList.of());
        }

        LOGGER.trace("Performing query for '{}'. Max results: {}", text, nHit);
        /* get all unique query terms */
        List<String> queryWords = getQueryTerms(text, documentProcessor);
        LOGGER.trace("Query words '{}'", queryWords);
        /* create query vector */
        Map<String, Double> queryVector = createQueryVector(queryWords);
        LOGGER.trace("Query vector '{}'", queryVector);
        /* get query-related documents */
        List<Long> documentIds = getQueryRelatedDocuments(queryWords);
        index.normalizeEntries(documentIds);
        LOGGER.trace("Document ids '{}'", documentIds);
        /* perform search based on query */
        List<QueriedDocument> returnedDocuments = performSearch(queryWords, queryVector, documentIds);
        returnedDocuments.sort(Comparator.comparingDouble(QueriedDocument::relevance).reversed());
        returnedDocuments = returnedDocuments.subList(0, Math.min(returnedDocuments.size(), nHit + 1));
        return new QueryResult(ImmutableList.copyOf(returnedDocuments));
    }

    /**
     * Gets query terms from the query.
     *
     * @param text              Text.
     * @param documentProcessor Document processor.
     * @return Query words.
     */
    protected List<String> getQueryTerms(String text, DocumentProcessor documentProcessor) {
        Document queryAsDocument = new Document(-1, null, text, text);
        return documentProcessor.processDocument(queryAsDocument).processedFields().get(0).wordFrequencies().keySet().stream().toList();
    }

    /**
     * Creates query vector for the query.
     *
     * @param queryWords query words.
     * @return query vector.
     */
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

    /**
     * The actual query.
     *
     * @param queryWords  Query words.
     * @param queryVector Query vector.
     * @param documentIds Relevant documents.
     * @return Query result.
     */
    protected List<QueriedDocument> performSearch(List<String> queryWords, Map<String, Double> queryVector, List<Long> documentIds) {
        // calculate rank for each document
        List<QueriedDocument> queriedDocuments = new ArrayList<>();
        for (long id : documentIds) {
            double rank = 0;
            // cycle through each query term
            for (String term : queryWords) {
                double dv = getDocumentValue(term, id);
                double qv = queryVector.get(term);
                rank += dv * qv;
            }
            queriedDocuments.add(new QueriedDocument(rank, index.exposeDocuments().get(id).document()));
        }

        return queriedDocuments;
    }

    /**
     * Returns a document tf-idf by the indexed term if it exists.
     *
     * @param term  index term
     * @param docId document id
     * @return index tf-idf value
     */
    private double getDocumentValue(String term, long docId) {
        List<IndexedDocument> iDocuments = this.index.exposeInvertedIndex().getOrDefault(term, new ArrayList<>()); // fuck null
        for (IndexedDocument iDoc : iDocuments) {
            if (iDoc.getProcessedDocument().document().id() == docId)
                return iDoc.getTfidf();
        }

        return 0;
    }

    /**
     * Get documents.
     *
     * @param queryWords Query words.
     * @return Relevant documents
     */
    protected abstract List<Long> getQueryRelatedDocuments(List<String> queryWords);
}
