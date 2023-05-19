package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;

public class IndexedDocument {
    private double tfidf;
    private final ProcessedDocument processedDocument;

    public IndexedDocument(double tfidf, ProcessedDocument processedDocument) {
        this.tfidf = tfidf;
        this.processedDocument = processedDocument;
    }

    public double getTfidf() {
        return tfidf;
    }

    public ProcessedDocument getProcessedDocument() {
        return processedDocument;
    }

    public void setTfidf(double tfidf) {
        this.tfidf = tfidf;
    }
}