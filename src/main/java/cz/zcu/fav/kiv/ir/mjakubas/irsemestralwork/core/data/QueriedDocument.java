package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data;

/**
 * Record contains document wrapped with additional information gathered during search.
 *
 * @param rank      Document rank amongst returned documents after a search.
 * @param relevance Document-Query relevance.
 * @param document  Document.
 */
public record QueriedDocument(int rank, double relevance, Document document) {
}
