package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data;

import java.net.URL;

/**
 * Represents loaded unprocessed document.
 *
 * @param id    Internal id of a document.
 * @param url   Document origin.
 * @param title Document title.
 * @param text  Document content
 */
public record Document(long id, URL url, String title, String text) {
}
