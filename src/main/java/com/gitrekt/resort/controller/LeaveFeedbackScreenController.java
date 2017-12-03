package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.GuestFeedback;
import com.gitrekt.resort.model.services.GuestFeedbackService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class for the leave feedback screen.
 */
public class LeaveFeedbackScreenController implements Initializable {

    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private TextField guestEmailTextField;

    /**
     * Called by JavaFX
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Intentionally blank.
    }

    /**
     * Closes the dialog.
     */
    public void onCancelClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/GuestHomeScreen.fxml");
    }

    /**
     * Action button for when submit button is clicked.
     *
     * @throws IOException
     */
    public void onSubmitClicked() throws IOException {
        if (!feedbackTextArea.getText().isEmpty()
                && !guestEmailTextField.getText().isEmpty()) {

            GuestFeedbackService guestfeedbackservice = new GuestFeedbackService();
            guestfeedbackservice.createNewGuestFeedback(
                new GuestFeedback(feedbackTextArea.getText(), guestEmailTextField.getText())
            );

            // Display an alert dialog to confirm the submission.
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("THANK YOU!");
            a.setHeaderText("Thanks for your feeback. As promised, we will now ignore it.");
            a.showAndWait();
            if (a.getResult() == ButtonType.OK) {
                // User clicked OK
                onCancelClicked(); // This just closes the dialog, but it's a bit hacky
            }
        }
    }
}
