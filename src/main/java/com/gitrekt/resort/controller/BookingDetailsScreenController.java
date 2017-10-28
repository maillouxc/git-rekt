package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

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
    
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/GuestHomeScreen.fxml"
        );
    }
    
    public void onViewBillButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/CustomerBillScreen.fxml"
        );
    }
    
    public void onCancelBookingButtonClicked() {
        // TODO
    }
    
}
