package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Index as a hashmap.
 */
public class HashIndex implements Index {

    private static final Logger LOGGER = LogManager.getLogger(HashIndex.class);

    private final Map<String, List<IndexedDocument>> invertedIndex = new HashMap<>();
    private final Map<Long, ProcessedDocument> indexedDocuments = new HashMap<>();

    private ImmutableMap<String, List<IndexedDocument>> cacheIndex;
    private ImmutableMap<Long, ProcessedDocument> cacheDocuments;

    @Override
    public void indexDocuments(List<ProcessedDocument> documents, int ofField) {
        LOGGER.trace("storing documents...");
        this.storeDocuments(documents);
        LOGGER.trace("populating inverted index with unprocessed entries...");
        this.populateWithRawEntries(documents, ofField);
        LOGGER.trace("processing inverted index entries...");
        this.processRawEntries(ofField);
        cacheIndex = ImmutableMap.copyOf(this.invertedIndex);
        cacheDocuments = ImmutableMap.copyOf(this.indexedDocuments);
    }

    private void storeDocuments(List<ProcessedDocument> documents) {
        documents.forEach(document -> this.indexedDocuments.put(document.document().id(), document));
    }

    private void populateWithRawEntries(List<ProcessedDocument> documents, int ofField) {
        documents.forEach(document -> {
            ProcessedField field = document.processedFields().get(ofField);
            if (field == null) {
                LOGGER.error(String.format("Document of id: %s has null field[%s]." +
                        "Skipping populating inverted index for said document.", document.document().id(), ofField));
            } else {
                for (String word : field.words()) {
                    this.invertedIndex.putIfAbsent(word, new ArrayList<>());
                    this.invertedIndex.get(word).add(new IndexedDocument(0, document));
                }
            }
        });
    }

    private void processRawEntries(int ofField) {
        int documentsCount = indexedDocuments.size();
        this.invertedIndex.forEach((word, hashEntry) -> {
            /* sort the list */
            hashEntry.sort(Comparator.comparingLong(of -> of.getProcessedDocument().document().id()));
            /* calculate tf-idf */
            double idf = Math.log((double) documentsCount / hashEntry.size());
            hashEntry.forEach(document -> {
                //noinspection DataFlowIssue : cant happen due to initial check
                int tf = document.getProcessedDocument().processedFields().get(ofField).wordFrequencies()
                        .getOrDefault(word, 0);
                if (tf <= 0)
                    return;
                double tfidf = (1 + Math.log10(tf)) * idf;
                document.setTfidf(tfidf);
            });
        });
    }

    @Override
    public ImmutableMap<String, List<IndexedDocument>> exposeInvertedIndex() {
        return cacheIndex;
    }

    @Override
    public ImmutableMap<Long, ProcessedDocument> exposeDocuments() {
        return cacheDocuments;
    }
}
