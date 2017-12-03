package com.gitrekt.resort.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 * FXML Controller class for the leave feedback screen.
 */
public class LeaveFeedbackScreenController implements Initializable {
     
    @FXML 
    private Button submitButton;
    
    @FXML
    private Button cancelButton;
    
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
     * @throws IOException 
     */
    public void onSubmitClicked() throws IOException {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        //TODO Add feedback to database
    }
}
