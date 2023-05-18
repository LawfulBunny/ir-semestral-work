package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
class IndexedDocument {

    private double tfidf;

    private final ProcessedDocument processedDocument;
}