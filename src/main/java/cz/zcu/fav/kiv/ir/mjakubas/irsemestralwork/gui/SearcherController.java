package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.SearcherApplication;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueriedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.storage.IndexStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class SearcherController {

    private static final Logger LOGGER = LogManager.getLogger(SearcherController.class);
    public IndexStorage indexStorage;

    /**
     * Inserts new data into existing indexes.
     *
     * @param documents Documents.
     */
    public void insertNewData(List<Document> documents) {
        try {
            this.indexStorage.titleManager().indexDocuments(documents, 0);
            this.indexStorage.textManager().indexDocuments(documents, 1);
        } catch (Exception e) {
            LOGGER.error(e);
            showIndexationExceptionAlert();
        }
        showIndexationSuccessAlert();
        documentCount
                .setText(GUIText.LABEL_STATS_INDEX_DATA_COUNT + indexStorage.titleManager().getIndexedDocumentCount());
    }

    private void showIndexationExceptionAlert() {
        Alert dataExceptionAlert = new Alert(Alert.AlertType.ERROR);
        dataExceptionAlert.show();
    }

    private void showIndexationSuccessAlert() {

    }

    @FXML
    private ListView<QueriedDocument> resultList;
    @FXML
    public TextField hitField;
    @FXML
    private TextField queryField;
    @FXML
    public Label queryStats;
    @FXML
    public Label documentCount;
    @FXML
    public ComboBox<String> searchOfField;

    @FXML
    public void onNewDataClick(ActionEvent event) {
    }

    @FXML
    private void onNewIndexClick(ActionEvent event) throws IOException {
        LOGGER.info("Launching new index data select window");

        FXMLLoader fxmlLoader = new FXMLLoader(SearcherApplication.class.getResource("data-select-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage dataSelectStage = new Stage();
        dataSelectStage.setTitle(GUIText.STAGE_TITLE);
        dataSelectStage.setScene(scene);
        dataSelectStage.show();
    }

    @FXML
    private void onSearchClick(ActionEvent event) {
        String query = queryField.getText();
        LOGGER.info("Query accepted for '{}'", query);
    }

    @FXML
    public void onVectorModelClick(ActionEvent event) {
    }

    @FXML
    public void onBooleanModelClick(ActionEvent event) {
    }

    @FXML
    public void onMixModelClick(ActionEvent event) {
    }

    @FXML
    public void onHelpClick(ActionEvent event) {
    }
}
