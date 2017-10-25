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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class.
 */
public class StaffAccountsScreenController implements Initializable {

    @FXML
    private Button backButton;
    
    @FXML
    private Button removeEmployeeButton;
    
    @FXML
    private Button resetEmployeePasswordButton;
    
    @FXML
    private Button addNewEmployeeButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void onBackButtonClicked() throws IOException {
        Stage mainStage = (Stage) backButton.getScene().getWindow();
        Parent staffHomeScreenRoot = FXMLLoader.load(
            getClass().getResource("/fxml/StaffHomeScreen.fxml")
        );
        Scene staffHomeScreen = new Scene(staffHomeScreenRoot);
        mainStage.setScene(staffHomeScreen);
    }
    
    public void onRemoveEmployeeButtonClicked() {
        // TODO
    }
    
    public void onResetEmployeePasswordButtonClicked() throws IOException {
        Stage dialogStage = new Stage();
        Parent dialogRoot = FXMLLoader.load(
            getClass().getResource("/fxml/ResetEmployeePasswordDialog.fxml")
        );
        Scene resetPasswordDialog = new Scene(dialogRoot);
        dialogStage.setScene(resetPasswordDialog);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(
            resetEmployeePasswordButton.getScene().getWindow()
        );
        dialogStage.setResizable(false);
        dialogStage.setTitle("Authentication Required");
        dialogStage.centerOnScreen();
        
        dialogStage.show();
    }
    
    public void onAddNewEmployeeButtonClicked() {
        // TODO
    }
    
}
