package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document;

import java.util.List;

/**
 * Defines preprocessing process.
 */
public interface Preprocessing {

    /**
     * Performs preprocessing over a text.
     *
     * @param text Text.
     * @return Preprocessed text.
     */
    public List<String> preprocess(String text);
}
