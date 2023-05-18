package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.SearcherApplication;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.io.DocumentLoader;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.io.DataLoader;
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

        List<Document> documents = DataLoader.loadDocumentsFromDirectory(newIndexStage);
        if (documents == null) return;

        /* load template */
        FXMLLoader fxmlLoader = new FXMLLoader(SearcherApplication.class.getResource("searcher-view.fxml"));
        Parent root = fxmlLoader.load();
        SearcherController controller = fxmlLoader.getController();

        /* create index controller */
        controller.indexStorage = PreparedIndexStorage.createCzechIndexController();
        controller.insertNewData(documents);

        /* javafx stuff */
        Scene newIndexScene = new Scene(root);
        newIndexStage.setScene(newIndexScene);
        newIndexStage.setTitle(GUIText.STAGE_TITLE);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        newIndexStage.show();
    }
}