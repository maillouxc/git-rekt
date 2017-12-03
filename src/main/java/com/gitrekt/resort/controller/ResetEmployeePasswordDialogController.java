package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Employee;
import com.gitrekt.resort.model.services.EmployeeService;
import com.gitrekt.resort.model.services.PasswordValidatorUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityNotFoundException;
import org.passay.PasswordData;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;

/**
 * Controller class for the dialog in which employees reset their password.
 */
public class ResetEmployeePasswordDialogController implements Initializable {
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button confirmButton;
    
    @FXML
    private TextField newPasswordField;
    
    @FXML
    private TextField confirmPasswordField;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private Label lengthLabel;
    
    @FXML
    private Label specialCharLabel;
    
    @FXML
    private Label mixedCaseLabel;
    
    @FXML
    private Label numberLabel;
    
    private Long employeeId;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configure text change listeners for the two fields
       
    }  
    
    /**
     * Called to initialize the requisite data for the dialog.
     * 
     * @param employeeId The employee id of the employee to reset the password
     * for.
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    
    @FXML
    private void onCancelButtonClicked() {
        Stage dialogStage = (Stage) cancelButton.getScene().getWindow();
        dialogStage.close();
    }
    
    @FXML
    private void onConfirmButtonClicked() {
        if(validatePasswords()) {
            String password = newPasswordField.getText();
            changePassword(password);
            closeDialog();
        }
    }
    
    private void onConfirmPasswordFieldUpdated() {
        validatePasswords();
    }
    
    private void onNewPasswordFieldUpdated() {
        validatePasswords();
    }
    
    /**
     * In addition to determining whether the passwords are valid, this method
     * also handles the ui cues that let the user know what is wrong with their
     * password. Maybe not the best instance of the SRP, but it's simple enough
     * in practice.
     * 
     * @return True if the passwords entered are valid.
     */
    private boolean validatePasswords() {
        boolean result = true;
        
        String newPassword = newPasswordField.getText();
        String matchingPassword = confirmPasswordField.getText();
        
        // Hide any already showing errors
        errorLabel.setVisible(false);
        lengthLabel.getStyleClass().remove("validationErrorText");
        specialCharLabel.getStyleClass().remove("validationErrorText");
        numberLabel.getStyleClass().remove("validationErrorText");
        mixedCaseLabel.getStyleClass().remove("validationErrorText");
        
        if(newPassword.isEmpty() || matchingPassword.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Fields cannot be empty");
            result = false;
        }
        
        if(!newPassword.equals(matchingPassword)) {
            errorLabel.setVisible(true);
            errorLabel.setText("Passwords don't match");
            result = false;
        }
        
        RuleResult ruleResult = PasswordValidatorUtil.validator
                .validate(new PasswordData(newPassword));
        
        if(!ruleResult.isValid()) {
            result = false;
        }
        
        // Update the requirements labels
        for(RuleResultDetail d : ruleResult.getDetails()) {
            String errorCode = d.getErrorCode();
            switch(errorCode) {
                case "TOO_SHORT":
                    lengthLabel.getStyleClass().add("validationErrorText");
                    break;
                case "INSUFFICIENT_LOWERCASE":
                case "INSUFFICIENT_UPPERCASE":
                    mixedCaseLabel.getStyleClass().add("validationErrorText");
                    break;
                case "INSUFFICIENT_DIGIT":
                    numberLabel.getStyleClass().add("validationErrorText");
                    break;
                case "INSUFFICIENT_SPECIAL":
                    specialCharLabel.getStyleClass().add("validationErrorText");
                    break;
            }
        }
        
        return result;
    }
    
    /**
     * Handles the actual act of changing the user's password.
     * 
     * @param newPassword The password, which should be already validated. 
     */
    private void changePassword(String newPassword) {
        // Ensure that we have an employee to change the password for
        if(this.employeeId == null) {
            throw new IllegalStateException("Must initialize employeeId");
        }
        
        EmployeeService employeeService = new EmployeeService();
        Employee employee = employeeService.getEmployeeById(this.employeeId);
        try {
            employee.setPassword(newPassword);
            employeeService.updateEmployee(employee);
        } catch (EntityNotFoundException e) {
            // TODO
        }
    }
    
    private void closeDialog() {
        Stage dialogStage = (Stage) this.lengthLabel.getScene().getWindow();
        dialogStage.close();
    }
    
}
