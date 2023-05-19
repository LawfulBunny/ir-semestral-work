package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueryResult;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.DocumentProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.Index;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.InvalidFieldIndex;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.QueryProcessor;

import java.util.List;

/**
 * Manages data-flow for specific index. Has the ability to perform query over the index.
 */
public class IndexManager {

    private final DocumentProcessor documentProcessor;
    private final Index index;

    public IndexManager(DocumentProcessor documentProcessor, Index index) {
        this.documentProcessor = documentProcessor;
        this.index = index;
    }

    /**
     * Indexes documents into index.
     *
     * @param documents Documents
     * @param ofField   Indexed field.
     * @throws InvalidFieldIndex If field doesn't exist.
     */
    public void indexDocuments(List<Document> documents, int ofField) throws InvalidFieldIndex {
        List<ProcessedDocument> processed = documentProcessor.processDocuments(documents);
        index.indexDocuments(processed, ofField);
    }

    public QueryResult searchIndex(String text, int nHit, QueryProcessor queryProcessor) {
        return queryProcessor.performQuery(text, nHit, documentProcessor);
    }

    public int getIndexedDocumentCount() {
        return index.exposeDocuments().size();
    }

    public DocumentProcessor getDocumentProcessor() {
        return documentProcessor;
    }

    public Index getIndex() {
        return index;
    }
}
