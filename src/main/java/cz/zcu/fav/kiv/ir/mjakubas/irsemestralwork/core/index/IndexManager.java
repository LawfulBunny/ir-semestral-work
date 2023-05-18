package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.PreprocessingSolution;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Manages data-flow for specific index. Has the ability to perform query over the index.
 */
@AllArgsConstructor
public class IndexManager {

    private final PreprocessingSolution preprocessingSolution;
    private final Index index;

    public void indexDocuments(List<Document> documents, int ofField) throws InvalidFieldIndex {
        List<ProcessedDocument> processed = preprocessingSolution.documentProcessor().processDocuments(documents);
        index.indexDocuments(processed, ofField);
    }

    private boolean validateFieldExistence() {
        return false;
    }
}
