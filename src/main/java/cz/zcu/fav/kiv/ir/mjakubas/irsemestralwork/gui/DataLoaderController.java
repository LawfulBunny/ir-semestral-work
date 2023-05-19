package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.SearcherApplication;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.QueriedDocument;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.MixedQueryProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.io.DocumentLoader;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.io.DataLoader;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.storage.PreparedIndexStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
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
        controller.queryProcessorTitle = new MixedQueryProcessor(controller.indexStorage.titleManager().getIndex());
        controller.queryProcessorText = new MixedQueryProcessor(controller.indexStorage.textManager().getIndex());
        controller.hitField.setText("" + SearcherController.DEFAULT_HIT_VALUE);
        controller.hitField.setTextFormatter(new TextFormatter<Integer>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*") && newText.length() > 0) {
                return change;
            }
            if (newText.equals("")) {
                controller.hitField.setText("1");
                return change;
            }
            return null;
        }));
        controller.resultList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(QueriedDocument item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    HBox hbox = new HBox(10);
                    Button button = new Button("Click");
                    button.setOnAction(event -> {
                        System.out.println("Button clicked for item: " + item);
                    });
                    hbox.getChildren().addAll(
                            // Customize the text and button as needed
                            new javafx.scene.text.Text("asda" + item.relevance()),
                            button
                    );
                    setGraphic(hbox);
                } else {
                    setGraphic(null);
                }
            }
        });

        /* javafx stuff */
        Scene newIndexScene = new Scene(root);
        newIndexStage.setScene(newIndexScene);
        newIndexStage.setTitle(GUIText.STAGE_TITLE);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        newIndexStage.show();
    }
}