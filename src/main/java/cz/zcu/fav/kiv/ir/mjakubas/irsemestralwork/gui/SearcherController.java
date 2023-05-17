package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.SearcherApplication;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueriedDocument;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class SearcherController {

    private static final Logger LOGGER = LogManager.getLogger(SearcherController.class);

    @FXML
    private ListView<QueriedDocument> resultList;

    @FXML
    private TextField queryField;

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
}
