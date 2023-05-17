package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.io;

import com.google.gson.Gson;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains a solution for loading documents from a file system.
 */
public class DocumentLoader {

    private static final Logger LOGGER = LogManager.getLogger(DocumentLoader.class);
    private static long nextId = 1; // only in-memory solution
    private static final Gson GSON = new Gson();

    /**
     * Loads files from given directory. Files are expected to be in JSON format.
     * File format should contain following attributes:
     * <ul>
     *     <li>url - origin of the document</li>
     *     <li>title - title of the document</li>
     *     <li>text - content of the document</li>
     * </ul>
     *
     * @param directoryPath Path of the directory that contains properly formatted files.
     * @return Loaded documents.
     * @throws IOException If IO exception occurs.
     */
    public static List<Document> LoadFromJSON(Path directoryPath) throws IOException {
        List<Document> loadedDocuments = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
            for (Path filePath : stream) {
                String fileData = new String(Files.readAllBytes(filePath));
                LoadedDocument document = GSON.fromJson(fileData, LoadedDocument.class);
                loadedDocuments.add(document.tag());
            }
        } catch (RuntimeException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e.getMessage());
            }
            throw e;
        }

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Successfully loaded [%s] documents.", loadedDocuments.size()));
        }

        return loadedDocuments;
    }

    /**
     * Internal representation of loaded document from a file system.
     *
     * @param url   Url.
     * @param title Title.
     * @param text  Text.
     */
    private record LoadedDocument(URL url, String title, String text) {
        public Document tag() {
            return new Document(nextId++, url, title, text);
        }
    }
}
