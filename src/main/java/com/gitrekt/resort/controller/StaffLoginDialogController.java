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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
    
/**
 * FXML Controller class for the staff login dialog.
 */
public class StaffLoginDialogController implements Initializable {

    @FXML
    private Button cancelButton;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private TextField employeeIdField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void onCancelButtonClicked() {
        Stage dialogStage = (Stage) cancelButton.getScene().getWindow();
        dialogStage.close();
    }
    
    public void onLoginButtonClicked() throws IOException {
        boolean validCredentials = areEmployeeCredentialsValid();
        if(validCredentials) {
            Stage dialogStage = (Stage) loginButton.getScene().getWindow();
            Stage mainStage = (Stage) dialogStage.getOwner();
            Parent staffHomeScreenRoot = FXMLLoader.load(
                getClass().getResource("/fxml/StaffHomeScreen.fxml")
            );
            Scene staffHomeScreenScene = new Scene(staffHomeScreenRoot);
            mainStage.setScene(staffHomeScreenScene);
            dialogStage.close();
        } else {
            // TODO
        }
    }
    
    private boolean areEmployeeCredentialsValid() {
        // Read the data from the fields to validate it
        String employeeIdEntered = employeeIdField.getText();
        String passwordEntered = passwordField.getText();
        
        // For now, just return true.
        return true;
    }
    
}
