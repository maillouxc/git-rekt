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
    
    @FXML
    protected void onViewBookingButtonClicked() throws IOException {
        String bookingNumberEntered = bookingNumberField.getText();
        
        // TODO retrieve booking info from backend
        showBookingDetailsScreen();
    }
    
    private void showBookingDetailsScreen() throws IOException {
        Stage dialogStage = (Stage) viewBookingButton.getScene().getWindow();
        Stage mainStage = (Stage) dialogStage.getOwner();
        Parent bookingDetailsScreenRoot = FXMLLoader.load(
            getClass().getResource("/fxml/BookingDetailsScreen.fxml")
        );
        Scene bookingDetailsScreen = new Scene(bookingDetailsScreenRoot);
        mainStage.setScene(bookingDetailsScreen);
        dialogStage.close();
    }
    
}
