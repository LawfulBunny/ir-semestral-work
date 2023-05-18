package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query;

import com.google.common.collect.ImmutableMap;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.Index;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.IndexedDocument;

import java.util.*;

public class VectorQueryProcessor extends QueryProcessor {

    public VectorQueryProcessor(Index index) {
        super(index);
    }

    @Override
    protected List<Long> getQueryRelatedDocuments(Set<String> queryWords) {
        Set<Long> ids = new HashSet<>();
        ImmutableMap<String, List<IndexedDocument>> invertedIndex = index.exposeInvertedIndex();
        for (String term : queryWords) {
            invertedIndex.getOrDefault(term, new ArrayList<>()).forEach(indexedDocument -> {
                ids.add(indexedDocument.getProcessedDocument().document().id());
            });
        }

        return ids.stream().toList();
    }
}
