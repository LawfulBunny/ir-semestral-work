package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui;

import com.google.common.collect.ImmutableList;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.SearcherApplication;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueriedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueryResult;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.BooleanQueryProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.MixedQueryProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.QueryProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.VectorQueryProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.io.DataLoader;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.storage.IndexStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class SearcherController {
    private static final Logger LOGGER = LogManager.getLogger(SearcherController.class);
    public IndexStorage indexStorage;
    public QueryProcessor queryProcessorTitle;
    public QueryProcessor queryProcessorText;

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
        //not necessary
    }

    private void showIndexationSuccessAlert() {
        Alert dataAlert = new Alert(Alert.AlertType.INFORMATION);
        dataAlert.setTitle(GUIText.ALERT_INFO_TITLE);
        dataAlert.setHeaderText(GUIText.ALERT_INDEXATION_HEADER);
        dataAlert.setContentText(GUIText.ALERT_INDEXATION_CONTENT);
        dataAlert.showAndWait();
    }

    private void showQueryModelChangeAlert() {
        Alert queryModelChange = new Alert(Alert.AlertType.INFORMATION);
        queryModelChange.setTitle(GUIText.ALERT_INFO_TITLE);
        queryModelChange.setHeaderText(GUIText.ALERT_MODEL_CHANGE_HEADER);
        queryModelChange.setContentText(GUIText.ALERT_MODEL_CHANGE_CONTENT);
        queryModelChange.showAndWait();
    }

    @FXML
    public ListView<QueriedDocument> resultList;

    public static final int DEFAULT_HIT_VALUE = 10;
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
        Stage stage = (Stage) searchOfField.getScene().getWindow();
        List<Document> documents = DataLoader.loadDocumentsFromDirectory(stage);
        this.insertNewData(documents);
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
        QueryProcessor queryProcessor = queryProcessorText;
        if (searchOfField.getValue().equals("Title")) {
            queryProcessor = queryProcessorTitle;
        }
        QueryResult result = new QueryResult(ImmutableList.of());
        long startTime = System.nanoTime();
        try {
            result = queryProcessor.performQuery(query, Integer.parseInt(hitField.getText()), indexStorage.textManager().getDocumentProcessor());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(GUIText.ALERT_ERROR_TITLE);
            alert.setHeaderText(GUIText.ALERT_QUERY_ERROR_HEADER);
            alert.setContentText(GUIText.ALERT_QUERY_ERROR_CONTENT);
            LOGGER.error(e);
        }
        long elapsedTime = (System.nanoTime() - startTime) / 1000000;
        LOGGER.info("Elapsed time: {} ms", elapsedTime);
        queryStats.setText(String.format("Dotaz zabral: " + elapsedTime + " ms"));
        result.result().forEach(queriedDocument -> {
            resultList.getItems().add(queriedDocument);
        });
    }

    @FXML
    public void onVectorModelClick(ActionEvent event) {
        queryProcessorTitle = new VectorQueryProcessor(indexStorage.titleManager().getIndex());
        queryProcessorText = new VectorQueryProcessor(indexStorage.textManager().getIndex());
        showQueryModelChangeAlert();
    }

    @FXML
    public void onBooleanModelClick(ActionEvent event) {
        queryProcessorTitle = new BooleanQueryProcessor(indexStorage.titleManager().getIndex());
        queryProcessorText = new BooleanQueryProcessor(indexStorage.textManager().getIndex());
        showQueryModelChangeAlert();
    }

    @FXML
    public void onMixModelClick(ActionEvent event) {
        queryProcessorTitle = new MixedQueryProcessor(indexStorage.titleManager().getIndex());
        queryProcessorText = new MixedQueryProcessor(indexStorage.textManager().getIndex());
        showQueryModelChangeAlert();
    }

    @FXML
    public void onHelpClick(ActionEvent event) {
        /* load template view */
        FXMLLoader fxmlLoader = new FXMLLoader(SearcherApplication.class.getResource("searcher-help-view.fxml"));
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            /* set stage */
            stage.setTitle(GUIText.STAGE_TITLE);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ignored) {
        }
    }
}
