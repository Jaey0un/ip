package mang.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mang.Mang;

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

    private Mang mang;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image mangImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getMangDialog(
                        """
                                Hello! I'm Mang, your friendly neighborhood chatbot!
                                What can I do for you?
                                Type 'bye' whenever you want to end our chat.""", mangImage)
        );
    }

    /**
     * Injects the Mang instance
     */
    public void setMang(Mang m) {
        mang = m;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Mang's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isBlank()) {
            return;
        }
        String response = mang.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMangDialog(response, mangImage)
        );
        userInput.clear();
        if (input.equalsIgnoreCase("bye")) {
            javafx.application.Platform.exit();
        }
    }
}
