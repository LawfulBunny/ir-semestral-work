package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.Index;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MixedQueryProcessor extends QueryProcessor {
    public MixedQueryProcessor(Index index) {
        super(index);
    }

    @Override
    protected List<Long> getQueryRelatedDocuments(Set<String> queryWords) {
        return null;
    }
}
