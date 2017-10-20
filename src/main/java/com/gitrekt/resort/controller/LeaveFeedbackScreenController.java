package com.gitrekt.resort.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
    private Button backButton;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
     
    /**
     * Closes the window and saves the user's feedback to a database
     * @param event 
     */  
    @FXML
    private void onSubmitButtonClicked(ActionEvent event) {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        //TODO Add feedback to database
        stage.close();
    }
    
    @FXML
    protected void onBackButtonClicked() throws IOException {
        Stage mainStage = (Stage) backButton.getScene().getWindow();
        Parent guestHomeScreenRoot = FXMLLoader.load(
                getClass().getResource("/fxml/GuestHomeScreen.fxml")
        );
        Scene guestHomeScreen = new Scene(guestHomeScreenRoot);
        
        mainStage.setScene(guestHomeScreen);
    }
}
