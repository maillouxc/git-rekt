package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 */
public class BookingNumberDialogController implements Initializable {

    @FXML
    private Button viewBookingButton;
    
    @FXML
    private TextField bookingNumberField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void onViewBookingButtonClicked() {
        String bookingNumberEntered = bookingNumberField.getText();
        
        // TODO retrieve booking info from backend
        
        ScreenManager.getInstance().switchToScreen(
            "/fxml/BookingDetailsScreen.fxml"
        );  
        Stage dialogStage = (Stage) viewBookingButton.getScene().getWindow();
        dialogStage.close();
    }
    
}
