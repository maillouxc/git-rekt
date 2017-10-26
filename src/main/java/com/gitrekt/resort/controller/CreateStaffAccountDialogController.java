package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for the dialog in which managers create new staff accounts.
 */
public class CreateStaffAccountDialogController implements Initializable {
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button confirmButton;
    
    @FXML
    private TextField firstNameField;
    
    @FXML 
    private TextField lastNameField;
    
    @FXML
    private CheckBox managerCheckBox;
    
    @FXML
    private TextField employeeIdField;
    
    @FXML
    private TextField passwordField;
    
    @FXML
    private TextField confirmPasswordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
    
    public void onCancelButtonClicked() {
        Stage dialogStage = (Stage) cancelButton.getScene().getWindow();
        dialogStage.close();
    }
    
    public void onConfirmButtonClicked() {
        //TODO
    }
    
}
