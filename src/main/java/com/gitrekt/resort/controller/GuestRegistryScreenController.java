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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

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
    
    public void onBackButtonClicked() throws IOException {
        Stage mainStage = (Stage) backButton.getScene().getWindow();
        Parent staffHomeScreenRoot = FXMLLoader.load(
            getClass().getResource("/fxml/StaffHomeScreen.fxml")
        );
        Scene staffHomeScreen = new Scene(staffHomeScreenRoot);
        mainStage.setScene(staffHomeScreen);
        mainStage.centerOnScreen();
    }
    
}
