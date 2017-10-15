package com.gitrekt.resort.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class for the browse rooms screen.
 */
public class BrowseRoomsScreenController implements Initializable {

    @FXML
    private Button backButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Closes the browse-rooms screen and returns the application to the guest
     * home screen.
     * 
     * @throws IOException
     */
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
