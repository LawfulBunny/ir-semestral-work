package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data;

/**
 * Record contains document wrapped with additional information gathered during search.
 *
 * @param relevance Document-Query relevance.
 * @param document  Document.
 */
public record QueriedDocument(double relevance, Document document) {
}
