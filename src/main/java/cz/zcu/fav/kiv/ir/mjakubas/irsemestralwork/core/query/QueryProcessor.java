package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.query;

import com.google.common.collect.ImmutableList;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueriedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueryResult;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.Index;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.DocumentProcessor;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Used to perform query over a given index.
 *
 * @param <Entry> Index entry.
 */
@AllArgsConstructor
public abstract class QueryProcessor<Entry> {

    private static final Logger LOGGER = LogManager.getLogger(QueryProcessor.class);

    private final Index<Entry> index;

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
        List<QueriedDocument> returnedDocuments = performSearch(queryWords, documentIds);
        LOGGER.trace("Query returned documents: {}",
                returnedDocuments.stream().map(queriedDocument -> queriedDocument.document().id()));
        return new QueryResult(ImmutableList.copyOf(returnedDocuments));
    }

    protected Set<String> getQueryTerms(String text) {
        Document queryAsDocument = new Document(-1, null, null, text);

        return null;
    }

    protected Map<String, Double> createQueryVector(Set<String> queryWords) {
        return null;
    }

    protected List<QueriedDocument> performSearch(Set<String> queryWords, List<Integer> documentIds) {
        return null;
    }

    protected abstract List<Integer> getQueryRelatedDocuments(Set<String> queryWords);
}
