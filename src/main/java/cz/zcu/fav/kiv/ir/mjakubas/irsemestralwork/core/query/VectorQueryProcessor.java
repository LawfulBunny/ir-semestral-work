package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.query;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.HashIndex;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.Index;

import java.util.List;
import java.util.Set;

public class VectorQueryProcessor extends QueryProcessor {

    public VectorQueryProcessor(Index index) {
        super(index);
    }

    @Override
    protected List<Integer> getQueryRelatedDocuments(Set<String> queryWords) {
        return null;
    }
}
