package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.SearcherApplication;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.stemmer.Stemmer;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.tokenizer.Tokenizer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Defines basic text preprocessing. Preprocessing consists of stemmer and tokenizer.
 */
public class BasicPreprocessing implements Preprocessing {

    private final Stemmer stemmer;
    private final Tokenizer tokenizer;

    @Override
    public List<String> preprocess(String text) {
        String processedInput = text.toLowerCase();

        List<String> processedTokens = new ArrayList<>();

        for (String token : tokenizer.tokenize(processedInput)) {
            if (stopWords.contains(token))
                continue;
            String processedToken = stemmer.stem(token);
            processedTokens.add(processedToken);
        }

        return processedTokens;
    }

    Set<String> stopWords;

    public BasicPreprocessing(Stemmer stemmer, Tokenizer tokenizer) {
        this.stemmer = stemmer;
        this.tokenizer = tokenizer;
        try {
            var path = Path.of(SearcherApplication.class.getResource("cz.txt").toURI());
            String[] words = Files.readString(path).split("\n");
            this.stopWords = new HashSet<>();
            for (String w : words) {
                stopWords.add(stemmer.stem(w));
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
