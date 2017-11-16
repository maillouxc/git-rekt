package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Employee;
import com.gitrekt.resort.model.services.EmployeeService;
import com.gitrekt.resort.model.services.PasswordValidatorUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.PersistenceException;
import org.passay.PasswordData;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;

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
    
    @FXML
    private Label firstNameLabel;
    
    @FXML
    private Label lastNameLabel;
    
    @FXML
    private Label employeeIdLabel;
    
    @FXML
    private Label staffErrorLabel;

    @FXML 
    private Label errorLabel;
    
    @FXML
    private Label lengthLabel;
    
    @FXML
    private Label numAndSpecialCharLabel;
    
    @FXML
    private Label mixedCaseLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Assign update listeners for the password fields for validation
        passwordField.setOnKeyPressed(e ->
            onPasswordFieldUpdated()
        );
        confirmPasswordField.setOnKeyPressed(e -> 
            onConfirmPasswordFieldUpdated()
        );
    }
    
    /**
     * Closes the dialog.
     */
    @FXML
    private void onCancelButtonClicked() {
        Stage dialogStage = (Stage) cancelButton.getScene().getWindow();
        dialogStage.close();
    }
    
    /**
     * Creates a new account with the data from the form.
     * 
     * Handles cases where the account already exists by display an error, and
     * validates the input fields at a basic level.
     */
    @FXML
    private void onConfirmButtonClicked() {
        // Hide any currently showing account creation errors
        staffErrorLabel.setVisible(false);
        // Gather data from form
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        boolean isManager = managerCheckBox.isSelected();
        long employeeId = Long.valueOf(employeeIdField.getText());
        // Validate and check if we can proceed
        if(validatePasswords() && validateStaffAccount()) {
            Employee newEmployee = new Employee(
                employeeId, password, isManager, firstName, lastName
            ); 
            EmployeeService employeeService = new EmployeeService();
            try {
                employeeService.createEmployeeAccount(newEmployee);
                Stage dialogStage = (Stage) cancelButton.getScene().getWindow();
                dialogStage.close();
            } catch (PersistenceException e) {
                // This exception is thrown when the entity already exists
                // You'd think it would throw EntityAlreadyExistsException
                // but Hibernate is weird like that
                onAccountAlreadyExists();
            }
            employeeService.cleanup();
        }
    }
    
    /**
     * Called when trying to create a new account with an id that already exists
     * in the database.
     */
    private void onAccountAlreadyExists() {
        staffErrorLabel.setVisible(true);
        staffErrorLabel.setText("Account with this ID already exists.");
    }

    /**
     * Validates the password fields.
     */
    private void onPasswordFieldUpdated() {
        validatePasswords();
    }
    
    /**
     * Validates the password fields.
     */
    private void onConfirmPasswordFieldUpdated() {
        validatePasswords();
    }
    
    /**
     * Performs form validation for the create employee account dialog.
     * Also updates UI cues to display information related to form validation
     * errors.
     * 
     * Currently only checks that fields are not empty.
     * 
     * Should later be expanded to include checks for incorrect data types.
     * 
     * @return True if the form fields are valid.
     */
    private boolean validateStaffAccount() {
        boolean result = true;
        
        // Gather data from form fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        long employeeId = Long.valueOf(employeeIdField.getText());
        
        //Hide any already showing errors
        staffErrorLabel.setVisible(false);
        firstNameLabel.getStyleClass().remove("validationErrorText");
        lastNameLabel.getStyleClass().remove("validationErrorText");
        employeeIdLabel.getStyleClass().remove("validationErrorText");
        
        // Ensure fields are not empty - show error if they are
        if(firstName.isEmpty() 
                || lastName.isEmpty() 
                || String.valueOf(employeeId).isEmpty()) {
            staffErrorLabel.setVisible(true);
            staffErrorLabel.setText("Fields cannot be empty");
            result = false;
        }

        return result;
    }
    
    
    /**
     * In addition to determining whether the passwords are valid, this method
     * also handles the UI cues that let the user know what is wrong with their
     * password.
     * 
     * @return True if the passwords entered are valid
     */
    private boolean validatePasswords() {
        boolean result = true; // Innocent until proven guilty lol
        
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // Hide any errors that are showing already
        errorLabel.setVisible(false);
        lengthLabel.getStyleClass().remove("validationErrorText");
        numAndSpecialCharLabel.getStyleClass().remove("validationErrorText");
        mixedCaseLabel.getStyleClass().remove("validationErrorText");
        
        // Check if there is any text written in the passwords fields
        if(password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Fields cannot be empty");
            result =   false;
        }
        
        // Check if password matches confirmation password
        if(!password.equals(confirmPassword)) {
            errorLabel.setVisible(true);
            errorLabel.setText("Passwords don't match");
            result = false;
        }
        
        RuleResult ruleResult = PasswordValidatorUtil.validator
                .validate(new PasswordData(password));
        
        // If the password is invalid, set the flag
        if(!ruleResult.isValid()) {
            result = false;
        }
        
        //Update the label requirements
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
                case "INSUFFICIENT_SPECIAL":
                    numAndSpecialCharLabel.getStyleClass()
                            .add("validationErrorText");
                    break;
            }
        }
        return result;
    }
    
}
