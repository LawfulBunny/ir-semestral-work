package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.tokenizer;

/**
 * Defines a tokenizer.
 */
public interface Tokenizer {

    /**
     * Performs text tokenization.
     *
     * @param text Text.
     * @return Tokenized text.
     */
    public String[] tokenize(String text);
}
