package mang.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mang.Mang;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Mang mang = new Mang();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Mang");
            stage.setScene(scene);

            // inject the Mang instance
            fxmlLoader.<MainWindow>getController().setMang(mang);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

