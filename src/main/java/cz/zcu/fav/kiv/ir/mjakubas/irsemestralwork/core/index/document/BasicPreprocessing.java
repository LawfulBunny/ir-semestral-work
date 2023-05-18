package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.stemmer.Stemmer;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.tokenizer.Tokenizer;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines basic text preprocessing. Preprocessing consists of stemmer and tokenizer.
 */
@AllArgsConstructor
public class BasicPreprocessing implements Preprocessing {

    private final Stemmer stemmer;
    private final Tokenizer tokenizer;

    @Override
    public List<String> preprocess(String text) {
        String processedInput = text.toLowerCase();

        List<String> processedTokens = new ArrayList<>();

        for (String token : tokenizer.tokenize(processedInput)) {
            String processedToken = stemmer.stem(token);
            processedTokens.add(processedToken);
        }

        return processedTokens;
    }
}
