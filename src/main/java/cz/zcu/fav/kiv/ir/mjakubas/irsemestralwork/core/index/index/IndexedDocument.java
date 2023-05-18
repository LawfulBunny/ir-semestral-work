package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.ProcessedDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
@Getter
public class IndexedDocument {

    private double tfidf;

    private final ProcessedDocument processedDocument;
}