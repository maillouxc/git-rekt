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
import javafx.stage.Stage;

    
/**
 * FXML Controller class for the staff login dialog.
 */
public class StaffLoginDialogController implements Initializable {

    @FXML
    private Button cancelButton;
    
    @FXML
    private Button loginButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    protected void onCancelClicked() throws IOException {
        Stage closeStaffWindow = (Stage) cancelButton.getScene().getWindow();
        closeStaffWindow.close();
    }
    
    @FXML
    protected void onLoginClicked() throws IOException {
        Stage staffLoginStage = (Stage) loginButton.getScene().getWindow();
        Parent staffHomeScreenRoot = FXMLLoader.load(
            getClass().getResource("/fxml/StaffHomeScreen.fxml")
        );
        //Scene staffHomeScreenDialog = new Scene(staffHomeScreenRoot);
        //staffLoginStage.setScene(staffHomeScreenDialog);
        
        
        
    }
    
}
