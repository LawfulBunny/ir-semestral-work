package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data;

import com.google.common.collect.ImmutableList;

/**
 * Record represents query result.
 *
 * @param result Documents returned by the query.
 */
public record QueryResult(ImmutableList<QueriedDocument> result) {
}
