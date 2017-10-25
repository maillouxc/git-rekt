package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for the dialog in which employees reset their password.
 */
public class ResetEmployeePasswordDialogController implements Initializable {
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button confirmButton;
    
    @FXML
    private TextField authorizingManagerIdField;
    
    @FXML
    private TextField authorizingManagerPasswordField;
    
    @FXML
    private TextField confirmEmployeeIdField;
    
    @FXML
    private TextField newPasswordField;
    
    @FXML
    private TextField confirmPasswordField;
    
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
    
    public void onConfirmButtonClicked() {
        // TODO
    }
    
    public void onConfirmPasswordFieldUpdated() {
        // TODO
    }
    
    public void onNewPasswordFieldUpdated() {
        // TODO
    }
    
}
