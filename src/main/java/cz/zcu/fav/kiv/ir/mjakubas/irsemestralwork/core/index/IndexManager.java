package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueryResult;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.PreprocessingSolution;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.QueryProcessor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Manages data-flow for specific index. Has the ability to perform query over the index.
 */
@AllArgsConstructor
public class IndexManager {

    private final PreprocessingSolution preprocessingSolution;
    private final Index index;

    /**
     * Indexes documents into index.
     *
     * @param documents Documents
     * @param ofField   Indexed field.
     * @throws InvalidFieldIndex If field doesn't exist.
     */
    public void indexDocuments(List<Document> documents, int ofField) throws InvalidFieldIndex {
        List<ProcessedDocument> processed = preprocessingSolution.documentProcessor().processDocuments(documents);
        index.indexDocuments(processed, ofField);
    }

    public QueryResult searchIndex(String text, int nHit, QueryProcessor queryProcessor) {
        return queryProcessor.performQuery(text, nHit, preprocessingSolution);
    }
}
