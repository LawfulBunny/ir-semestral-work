package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index;

import com.google.common.collect.ImmutableMap;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;

import java.util.List;

public class HashIndex implements Index<HashIndex.IndexEntry> {

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

    public record IndexEntry() {
    }
}
