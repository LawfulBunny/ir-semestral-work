package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueriedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.Index;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BooleanQueryProcessor extends MixedQueryProcessor {
    public BooleanQueryProcessor(Index index) {
        super(index);
    }

    @Override
    protected List<QueriedDocument> performSearch(List<String> queryWords, Map<String, Double> queryVector, List<Long> documentIds) {
        List<QueriedDocument> result = new ArrayList<>();
        documentIds.forEach(aLong -> {
            result.add(new QueriedDocument(1, index.exposeDocuments().get(aLong).document()));
        });
        return result;
    }
}
