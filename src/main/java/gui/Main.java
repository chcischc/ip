package gui;

import java.io.IOException;

import capybara.Capybara;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Capybara using FXML.
 */
public class Main extends Application {

    private Capybara capybara = new Capybara("Data/taskStorage.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            MainWindow controller = fxmlLoader.getController();
            controller.setCapybara(capybara);
            controller.showWelcomeBubble(capybara.getWelcome());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
