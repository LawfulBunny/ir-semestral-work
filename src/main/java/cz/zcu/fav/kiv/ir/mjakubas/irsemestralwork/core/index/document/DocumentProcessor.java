package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedField;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class DocumentProcessor {

    private final Preprocessing basicPreprocessing;

    public List<ProcessedDocument> processDocuments(List<Document> documents) {
        List<ProcessedDocument> processedDocuments = new ArrayList<>();

        for (Document document : documents) {
            processedDocuments.add(processDocument(document));
        }

        return processedDocuments;
    }

    public ProcessedDocument processDocument(Document document) {
        List<String> processedTitle = basicPreprocessing.preprocess(document.title());
        List<String> processedText = basicPreprocessing.preprocess(document.text());

        Map<String, Integer> titleWordFrequencies = createWordFrequencies(processedTitle);
        Map<String, Integer> textWordFrequencies = createWordFrequencies(processedText);

        ProcessedField titleField =
                new ProcessedField(ImmutableList.copyOf(processedTitle), ImmutableMap.copyOf(titleWordFrequencies));
        ProcessedField textField =
                new ProcessedField(ImmutableList.copyOf(processedText), ImmutableMap.copyOf(textWordFrequencies));

        return new ProcessedDocument(document, ImmutableList.of(titleField, textField));
    }

    private Map<String, Integer> createWordFrequencies(List<String> words) {
        Map<String, Integer> wordFrequencies = new HashMap<>();

        for (String word : words) {
            wordFrequencies.putIfAbsent(word, 0);
            wordFrequencies.put(word, wordFrequencies.get(word) + 1);
        }

        return wordFrequencies;
    }
}
