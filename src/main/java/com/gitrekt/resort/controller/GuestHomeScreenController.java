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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    /**
     * Displays the browse rooms screen.
     * 
     * @throws IOException 
     */
    public void onBrowseRoomsButtonClicked() throws IOException {
        Stage mainStage = (Stage) browseRoomsButton.getScene().getWindow();
        Parent browseRoomsScreenRoot = FXMLLoader.load(
                getClass().getResource("/fxml/BrowseRoomsScreen.fxml")
        );
        Scene browseRoomsScreen = new Scene(browseRoomsScreenRoot);
        mainStage.setScene(browseRoomsScreen);
        mainStage.centerOnScreen();
    }
    
    /**
     * Displays the leave feedback screen.
     * 
     * @throws IOException 
     */
    public void onLeaveFeedbackButtonClicked() throws IOException {
        Stage mainStage = (Stage) leaveFeedbackButton.getScene().getWindow();
        Parent leaveFeedbackScreenRoot = FXMLLoader.load(
                getClass().getResource("/fxml/LeaveFeedbackScreen.fxml")
        );
        Scene leaveFeedbackScreen = new Scene(leaveFeedbackScreenRoot);
        mainStage.setScene(leaveFeedbackScreen);
        mainStage.centerOnScreen();
    }
     
     /**
     * Back button when click will take you to home screen
     * @throws IOException 
     */
    public void onBackButtonClicked() throws IOException {
        Stage mainStage = (Stage) backButton.getScene().getWindow();
        Parent HomeScreenRoot = FXMLLoader.load(
            getClass().getResource("/fxml/HomeScreen.fxml")
        );
        Scene HomeScreen = new Scene(HomeScreenRoot);
        mainStage.centerOnScreen();
        mainStage.setScene(HomeScreen);
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
