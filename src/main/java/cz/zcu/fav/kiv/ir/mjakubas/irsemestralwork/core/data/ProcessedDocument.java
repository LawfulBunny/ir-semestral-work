package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data;

import com.google.common.collect.ImmutableList;

/**
 * Record represent a document that has processed fields.
 *
 * @param document        Document.
 * @param processedFields Processed fields.
 */
public record ProcessedDocument(Document document, ImmutableList<ProcessedField> processedFields) {
}
