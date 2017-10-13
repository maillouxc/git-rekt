package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * The JavaFX controller class for the home screen.
 * 
 * The home screen is shown when the application first starts, and allows the
 * user to select which mode to use the program in: guest or staff.
 */
public class HomeScreenController implements Initializable {
    
    @FXML 
    private Button guestModeButton;
    
    @FXML 
    private Button staffModeButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Implement
    }
    
    /**
     * Trigger the pop-up dialog 
     */
    @FXML
    protected void onStaffModeButtonClicked() {
        // TODO: Implement.
    }
    
    @FXML
    protected void onGuestModeButtonClicked() {
        // TODO: Implement.
    }
}
