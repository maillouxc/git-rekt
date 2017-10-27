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
import javafx.scene.image.Image;
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
    
    private final Image appLogo = new Image("images/Logo.png");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/StaffHomeScreen.fxml"
        );
    }
    
    public void onRemoveEmployeeButtonClicked() {
        // TODO
    }
    
    public void onResetEmployeePasswordButtonClicked() throws IOException {
        Stage dialogStage = new Stage();
        dialogStage.getIcons().add(appLogo);
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
    
    public void onAddNewEmployeeButtonClicked() throws IOException {
        Stage dialogStage = new Stage();
        dialogStage.getIcons().add(appLogo);
        Parent dialogRoot = FXMLLoader.load(
            getClass().getResource("/fxml/CreateStaffAccountDialog.fxml")
        );
        Scene createStaffAccountDialog = new Scene(dialogRoot);
        dialogStage.setScene(createStaffAccountDialog);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(
            addNewEmployeeButton.getScene().getWindow()
        );
        dialogStage.setResizable(false);
        dialogStage.setTitle("Authentication Required");
        dialogStage.centerOnScreen();
        
        dialogStage.show();
    }
    
}
