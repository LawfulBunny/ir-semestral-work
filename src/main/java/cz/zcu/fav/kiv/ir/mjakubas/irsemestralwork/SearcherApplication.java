package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.GUIText;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Application launching point.
 */
public class SearcherApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        /* load template view */
        FXMLLoader fxmlLoader = new FXMLLoader(SearcherApplication.class.getResource("data-select-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        /* set stage */
        stage.setTitle(GUIText.STAGE_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}