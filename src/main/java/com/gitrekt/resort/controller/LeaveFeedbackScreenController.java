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
    
    /**
     * Action button for when cancel button is clicked
     * takes you back to the guest home screen.
     * @throws IOException 
     */
    public void onCancelClicked() throws IOException {
       Stage mainStage = (Stage) cancelButton.getScene().getWindow();
        Parent guestHomeScreenRoot = FXMLLoader.load(
                getClass().getResource("/fxml/GuestHomeScreen.fxml")
        );
        Scene guestHomeScreen = new Scene(guestHomeScreenRoot);
        
        mainStage.setScene(guestHomeScreen);
        mainStage.centerOnScreen();
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
