package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.services.EmployeeService;
import com.gitrekt.resort.model.services.EmployeeService.AuthenticationResult;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    
    @FXML
    private Label errorLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    /**
     * Closes the dialog.
     */
    public void onCancelButtonClicked() {
        Stage dialogStage = (Stage) cancelButton.getScene().getWindow();
        dialogStage.close();
    }
    
    /**
     * Authenticates the user and takes appropriate action.
     * 
     * @throws IOException because I'm too lazy to fix it right now
     */
    public void onLoginButtonClicked() throws IOException {
        // Hide any previously displayed errors
        errorLabel.setVisible(false);
        // Validate form fields and show error if needed
        boolean fieldsAreValid = validateFormFields();
        if(!fieldsAreValid) {
            errorLabel.setText("Invalid input!");
            errorLabel.setVisible(true);
            return;
        }
        // Authenticate the employee and take appropriate action on result
        EmployeeService employeeService = new EmployeeService();
        Long id = new Long(employeeIdField.getText());
        String password = passwordField.getText();
        AuthenticationResult authResult 
                = employeeService.authenticate(id, password);
        switch(authResult) {
            case SUCCESS:
                showStaffHomeScreen();
                break;
            case FAILURE:
                errorLabel.setText("Authentication failed");
                errorLabel.setVisible(true);
                break;
        }
    }
    
    private void showStaffHomeScreen() {
        Stage dialogStage = (Stage) loginButton.getScene().getWindow();
        ScreenManager.getInstance().switchToScreen(
            "/fxml/StaffHomeScreen.fxml"
        );
        dialogStage.close();
    }
    
    private boolean validateFormFields() {
        // Ensure fields are not empty
        if(employeeIdField.getText().isEmpty()) {
            return false;
        }
        if(passwordField.getText().isEmpty()) {
            return false;
        }
        // Ensure the the entered id is only digits
        for(Character c : employeeIdField.getText().toCharArray()) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        // If we're here, the fields are valid
        return true;
    }
    
}
