package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * TODO
 */
public class GuestRegistryScreenController implements Initializable {
    
    @FXML
    private Button checkInButton;
    
    @FXML
    private Button checkOutButton;
    
    @FXML
    private Button backButton;
    
    // TODO: Fix rawtype
    @FXML
    private TableView registryTable;
    
    // TODO: Fix rawtype
    @FXML
    private TableColumn guestNameColumn;
    
    // TODO: Fix rawtype
    @FXML
    private TableColumn checkedInColumn;
    
    // TODO: Fix rawtype
    @FXML
    private TableColumn bookingNumberColumn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void onCheckInButtonClicked() {
        // TODO
    }
    
    public void onCheckOutButtonClicked() {
        // TODO
    }
    
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/StaffHomeScreen.fxml"
        );
    }
    
}
