package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;

import java.util.List;

/**
 * Index as a hashmap.
 */
public class HashIndex implements Index {

    /**
     * Hashmap index entry.
     */
    public record IndexEntry() {
    }

    @Override
    public void indexDocuments(List<ProcessedDocument> documents, int ofField) {

    }

    @Override
    public ImmutableMap<String, ImmutableList<IndexedDocument>> exposeInvertedIndex() {
        return null;
    }


    @Override
    public ImmutableMap<Long, ProcessedDocument> exposeDocuments() {
        return null;
    }
}
