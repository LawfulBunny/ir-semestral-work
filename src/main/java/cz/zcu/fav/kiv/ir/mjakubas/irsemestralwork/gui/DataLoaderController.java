package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.SearcherApplication;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.io.DocumentLoader;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.storage.PreparedIndexStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
 * Controller for data-select-view view.
 */
public class DataLoaderController {

    private final static Logger LOGGER = LogManager.getLogger(DataLoaderController.class);

    @FXML
    protected void onDataLoadButtonClick(ActionEvent event) throws IOException {
        Stage newIndexStage = new Stage();

        /* select data directory */
        Path dataDirectoryPath = selectDataDirectory(newIndexStage);
        if (dataDirectoryPath == null) {
            showNoDataDirectorySelectedAlert();
            return;
        }
        /* load data from directory */
        List<Document> documents = loadDocumentsData(dataDirectoryPath);
        if (documents == null) {
            showDataExceptionAlert();
            return;
        }

        /* load template */
        FXMLLoader fxmlLoader = new FXMLLoader(SearcherApplication.class.getResource("searcher-view.fxml"));
        Parent root = fxmlLoader.load();
        SearcherController controller = fxmlLoader.getController();

        /* create index controller */
        controller.indexStorage = PreparedIndexStorage.createCzechIndexController();

        /* javafx stuff */
        Scene newIndexScene = new Scene(root);
        newIndexStage.setScene(newIndexScene);
        newIndexStage.setTitle(GUIText.STAGE_TITLE);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        newIndexStage.show();
    }

    /**
     * Opens file system dialog for selecting a directory.
     *
     * @param owner Dialog owner.
     * @return Selected path or null.
     */
    private Path selectDataDirectory(Stage owner) {
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
    private List<Document> loadDocumentsData(Path dataDirectoryPath) {
        List<Document> documentsData;
        try {
            documentsData = DocumentLoader.LoadFromJSON(dataDirectoryPath);
        } catch (IOException e) {
            LOGGER.error(e);
            return null;
        }
        return documentsData;
    }

    private void showNoDataDirectorySelectedAlert() {
        Alert noDataAlert = new Alert(Alert.AlertType.INFORMATION);
        noDataAlert.setTitle(GUIText.ALERT_INFO_TITLE);
        noDataAlert.setHeaderText(GUIText.ALERT_NO_DATA_HEADER);
        noDataAlert.setContentText(GUIText.ALERT_NO_DATA_CONTENT);

        noDataAlert.show();
    }

    private void showDataExceptionAlert() {
        Alert dataExceptionAlert = new Alert(Alert.AlertType.ERROR);

        dataExceptionAlert.show();
    }
}