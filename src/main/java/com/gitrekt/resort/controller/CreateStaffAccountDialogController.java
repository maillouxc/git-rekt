package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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
    private TextField authorizingManagerIdField;
    
    @FXML
    private TextField authorizingManagerPasswordField;
    
    @FXML
    private TextField name;
    
    @FXML 
    private TextField lastName;
    
    @FXML
    private RadioButton staffMemberYes;
    
    @FXML 
    private RadioButton staffMemberNo;
    
    @FXML
    private TextField employeeIdNumber;
    
    @FXML
    private TextField passWordField;
    
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
    
    public void onPasswordField() {
        //TODO
    }
    
    public void onConfirmPasswordField() {
        //TODO
    }
    
    public void onConfirmButtonClicked() {
        //TODO
    }

    
    
}
