package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.stemmer.Stemmer;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.tokenizer.Tokenizer;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BasicPreprocessing extends Preprocessing {

    private final Stemmer stemmer;
    private final Tokenizer tokenizer;

    public List<String> process(String input) {
        String processedInput = input.toLowerCase();

        List<String> processedTokens = new ArrayList<>();

        for (String token : tokenizer.tokenize(processedInput)) {
            String processedToken = stemmer.stem(token);
            processedTokens.add(processedToken);
        }

        return processedTokens;
    }
}
