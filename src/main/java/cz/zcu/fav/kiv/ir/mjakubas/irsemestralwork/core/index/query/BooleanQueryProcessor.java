package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.Index;

import java.util.List;
import java.util.Set;

public class BooleanQueryProcessor extends QueryProcessor {

    private static final String TOKEN_PARENTHESES_START = "(";
    private static final String TOKEN_PARENTHESES_END = ")";
    private static final String TOKEN_NOT = "NOT";
    private static final String TOKEN_AND = "AND";
    private static final String TOKEN_OR = "OR";

    public BooleanQueryProcessor(Index index) {
        super(index);
    }

    @Override
    protected List<Long> getQueryRelatedDocuments(List<String> queryWords) {
        return null;
    }
}
