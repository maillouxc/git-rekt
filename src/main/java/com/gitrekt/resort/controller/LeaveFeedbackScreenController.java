package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class for the leave feedback screen.
 */
public class LeaveFeedbackScreenController implements Initializable {
     
     @FXML 
     private javafx.scene.control.Button submitButton;
    
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
}
    

    
    


    
