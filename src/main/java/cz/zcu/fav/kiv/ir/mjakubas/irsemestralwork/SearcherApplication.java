package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.GUIText;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.SearcherController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SearcherApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SearcherApplication.class.getResource("data-select-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle(GUIText.STAGE_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}