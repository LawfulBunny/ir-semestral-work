package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index;

import com.google.common.collect.ImmutableMap;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;

import java.util.List;

/**
 * Index as a hashmap.
 */
public class HashIndex implements Index<HashIndex.IndexEntry> {

    /**
     * Hashmap index entry.
     */
    public record IndexEntry() {
    }

    @Override
    public void indexDocuments(List<ProcessedDocument> documents, int ofField) {

    }

    @Override
    public ImmutableMap<String, IndexEntry> exposeInvertedIndex() {
        return null;
    }

    @Override
    public ImmutableMap<Long, ProcessedDocument> exposeDocuments() {
        return null;
    }
}
