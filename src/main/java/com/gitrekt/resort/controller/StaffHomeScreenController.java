package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.services.EmployeeService;
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
        if(!EmployeeService.isManagerLoggedIn) {
            viewReportsButton.setDisable(true);
            editPricesButton.setDisable(true);
            manageStaffAccountsButton.setDisable(true);
        }
    }

    public void onRegistryButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/GuestRegistryScreen.fxml");
    }

    public void onViewReportsButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/ReportsHomeScreen.fxml");
    }

    public void onEditPricesButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/EditPricesScreen.fxml");
    }

    public void onManageStaffAccountsButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/StaffAccountsScreen.fxml");
    }

    public void onBackButtonClicked() {
        EmployeeService.isManagerLoggedIn = false;
        ScreenManager.getInstance().switchToScreen("/fxml/HomeScreen.fxml");
    }
}
