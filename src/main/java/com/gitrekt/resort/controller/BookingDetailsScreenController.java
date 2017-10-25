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
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class for the booking details screen.
 */
public class BookingDetailsScreenController implements Initializable {

    @FXML
    private Button backButton;
    
    @FXML
    private Button cancelBookingButton;
    
    @FXML
    private Button viewBillButton;
    
    // TODO fix rawtype
    @FXML
    private TableView bookedRoomsTableView;
    
    // TODO fix rawtype
    @FXML
    private TableView bookedPackagesTableView;
    
    @FXML
    private Label guestNameLabel;
    
    @FXML
    private Label checkInDateLabel;
    
    @FXML
    private Label checkOutDateLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void onBackButtonClicked() throws IOException {
        switchToGuestHomeScreen();
    }
    
    public void onViewBillButtonClicked() throws IOException {
        Stage mainStage = (Stage) backButton.getScene().getWindow();
        Parent customerBillScreenRoot = FXMLLoader.load(
            getClass().getResource("/fxml/CustomerBillScreen.fxml")
        );
        Scene customerBillScreen = new Scene(customerBillScreenRoot);
        mainStage.setScene(customerBillScreen);
        mainStage.centerOnScreen();
    }
    
    public void onCancelBookingButtonClicked() {
        // TODO
    }
    
    private void switchToGuestHomeScreen() throws IOException {
        Stage mainStage = (Stage) backButton.getScene().getWindow();
        Parent guestHomeScreenRoot = FXMLLoader.load(
            getClass().getResource("/fxml/GuestHomeScreen.fxml")
        );
        Scene guestHomeScreen = new Scene(guestHomeScreenRoot);
        mainStage.setScene(guestHomeScreen);
    }
    
}
