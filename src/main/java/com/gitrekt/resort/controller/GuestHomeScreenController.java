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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class for the guest home screen.
 */
public class GuestHomeScreenController implements Initializable {
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button browseRoomsButton;
    
    @FXML
    private Button leaveFeedbackButton;
    
    @FXML
    private Button viewBookingButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void onBrowseRoomsButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/BrowseRoomsScreen.fxml"
        );
    }
    
    public void onLeaveFeedbackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/LeaveFeedbackScreen.fxml"
        );
    }
     
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/HomeScreen.fxml"
        );
    }
      
    public void onViewBookingButtonClicked() throws IOException {
        Stage bookingNumberDialogStage = new Stage();
        Parent bookingNumberDialogRoot = FXMLLoader.load(
            getClass().getResource("/fxml/BookingNumberDialog.fxml")
        );
        Scene bookingNumberDialog = new Scene(bookingNumberDialogRoot);
        
        bookingNumberDialogStage.setScene(bookingNumberDialog);
        bookingNumberDialogStage.initModality(Modality.APPLICATION_MODAL);
        bookingNumberDialogStage.initOwner(
            viewBookingButton.getScene().getWindow()
        );
        bookingNumberDialogStage.setResizable(false);
        bookingNumberDialogStage.setTitle("Find Booking");
        bookingNumberDialogStage.centerOnScreen();
        
        bookingNumberDialogStage.show();
    }
}
