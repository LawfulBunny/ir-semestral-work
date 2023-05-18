package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.stemmer;

/**
 * Defines stemmer.
 */
public interface Stemmer {

    /**
     * Performs stemming of certain word.
     *
     * @param word Word.
     * @return Stemmed word.
     */
    public String stem(String word);
}
