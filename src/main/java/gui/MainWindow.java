package gui;

import capybara.Capybara;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;



/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Capybara capybara;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image capyImage = new Image(this.getClass().getResourceAsStream("/images/DaCapybara.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Capybara instance */
    public void setCapybara(Capybara c) {
        capybara = c;
    }

    public void showWelcomeBubble(String text) {
        dialogContainer.getChildren().add(
                DialogBox.getCapyDialog(text, capyImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = capybara.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getCapyDialog(response, capyImage)
        );
        userInput.clear();
        if (capybara.lastWasExit()) {
            userInput.setDisable(true);
            sendButton.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.millis(1500));
            delay.setOnFinished(ev -> {
                Stage stage = (Stage) dialogContainer.getScene().getWindow();
                stage.close();
            });
            delay.play();
        }
    }
}
