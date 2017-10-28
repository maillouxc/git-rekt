package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class for the staff home screen.
 */
public class StaffHomeScreenController implements Initializable {
    
    @FXML
    private Button backButton;

    @FXML
    private Button registryButton;
    
    @FXML
    private Button viewReportsButton;
    
    @FXML
    private Button editPricesButton;
    
    @FXML
    private Button manageStaffAccountsButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void onRegistryButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/GuestRegistryScreen.fxml"
        );
    }
    
    public void onViewReportsButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/ReportsHomeScreen.fxml"
        );
    }
    
    public void onEditPricesButtonClicked() {
        // TODO
    }
    
    public void onManageStaffAccountsButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/StaffAccountsScreen.fxml"
        );
    }
    
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/HomeScreen.fxml"
        );
    }
}
