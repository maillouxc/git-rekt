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
import javafx.stage.Stage;
import javafx.scene.control.Button;
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
    
    @FXML
    private Button submitButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void onCancelClicked() {
        ScreenManager.getInstance().switchToScreen(
                "/fxml/GuestHomeScreen.fxml"
        );
    }

    /**
     * Action button for when submit button is clicked.
     *
     * @throws IOException
     */
    public void onSubmitClicked() throws IOException {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        if (!feedbackTextArea.getText().equals("") && !guestEmailTextField.getText().equals("")) {
            GuestFeedbackService guestfeedbackservice = new GuestFeedbackService();
            guestfeedbackservice.createNewGuestFeedback(new GuestFeedback(feedbackTextArea.getText(), guestEmailTextField.getText()));
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("THANK YOU!");
            a.setHeaderText("Thanks for your feeback");
            a.showAndWait();
            if (a.getResult() == ButtonType.OK) {
                onCancelClicked();
            }           
        }
    }
}
