package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index;

import com.google.common.collect.ImmutableMap;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;

import java.util.List;

/**
 * Defines an index interface. Index should allow data indexation,
 * as well as retrieval of its indexed data structure.
 * Indexed data can be changed only through indexation methods.
 */
public interface Index {

    /**
     * Indexes new documents into the index.
     *
     * @param documents Documents.
     * @param ofField   Indexed field.
     */
    public void indexDocuments(List<ProcessedDocument> documents, int ofField);

    /**
     * Returns inverted index.
     *
     * @return Inverted index.
     */
    public ImmutableMap<String, List<IndexedDocument>> exposeInvertedIndex();

    /**
     * Returns indexed documents.
     *
     * @return Indexed documents.
     */
    public ImmutableMap<Long, ProcessedDocument> exposeDocuments();

    /**
     * For each document normalizes its term vector.
     *
     * @param documentsIds DocumentIds.
     */
    public void normalizeEntries(List<Long> documentsIds);
}
