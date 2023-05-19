package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.io;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.io.DocumentLoader;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.GUIText;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 *
 */
public class DataLoader {
    private static final Logger LOGGER = LogManager.getLogger(DataLoader.class);

    public static List<Document> loadDocumentsFromDirectory(Stage newIndexStage) {
        /* select data directory */
        Path dataDirectoryPath = DataLoader.selectDataDirectory(newIndexStage);
        if (dataDirectoryPath == null) {
            DataLoader.showNoDataDirectorySelectedAlert();
            return null;
        }
        /* load data from directory */
        List<Document> documents = loadDocumentsData(dataDirectoryPath);
        if (documents == null) {
            DataLoader.showDataExceptionAlert();
            return null;
        }
        return documents;
    }

    /**
     * Opens file system dialog for selecting a directory.
     *
     * @param owner Dialog owner.
     * @return Selected path or null.
     */
    private static Path selectDataDirectory(Stage owner) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dataDirectory = directoryChooser.showDialog(owner);
        if (dataDirectory == null) {
            LOGGER.debug("No index data directory was selected.");
            return null;
        }
        Path dataDirectoryPath = dataDirectory.toPath();
        LOGGER.debug("Selected index data directory path: {}", dataDirectoryPath.toAbsolutePath());
        return dataDirectoryPath;
    }

    /**
     * Loads all documents from given directory path.
     * If data are not valid returns null.
     *
     * @param dataDirectoryPath Documents directory path
     * @return Documents data or null.
     */
    private static List<Document> loadDocumentsData(Path dataDirectoryPath) {
        List<Document> documentsData;
        try {
            documentsData = DocumentLoader.LoadFromJSON(dataDirectoryPath);
        } catch (IOException e) {
            LOGGER.error(e);
            return null;
        }
        return documentsData;
    }

    private static void showNoDataDirectorySelectedAlert() {
        Alert noDataAlert = new Alert(Alert.AlertType.INFORMATION);
        noDataAlert.setTitle(GUIText.ALERT_INFO_TITLE);
        noDataAlert.setHeaderText(GUIText.ALERT_NO_DATA_HEADER);
        noDataAlert.setContentText(GUIText.ALERT_NO_DATA_CONTENT);

        noDataAlert.show();
    }

    private static void showDataExceptionAlert() {
        Alert dataExceptionAlert = new Alert(Alert.AlertType.ERROR);

        dataExceptionAlert.show();
    }
}
