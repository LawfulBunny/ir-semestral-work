package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index;

import com.google.common.collect.ImmutableMap;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;

import java.util.List;

public interface Index<Entry> {

    public void indexDocuments(List<ProcessedDocument> documents, int ofField);

    public ImmutableMap<String, Entry> exposeInvertedIndex();

    public ImmutableMap<Long, ProcessedDocument> exposeDocuments();
}
