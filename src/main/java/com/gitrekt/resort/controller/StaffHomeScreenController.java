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
 * FXML Controller class for the staff home screen.
 */
public class StaffHomeScreenController implements Initializable {

    @FXML
    private Button viewReportsButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void onViewReportsButtonClicked() throws IOException {
        Stage mainStage = (Stage) viewReportsButton.getScene().getWindow();
        Parent viewReportsScreenRoot = FXMLLoader.load(
            getClass().getResource("/fxml/ViewReportsScreenController.fxml")
        );
        Scene viewReportsScreen = new Scene(viewReportsScreenRoot);
        mainStage.setScene(viewReportsScreen);
    }
    
}
