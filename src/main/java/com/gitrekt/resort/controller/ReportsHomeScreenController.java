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
 * FXML Controller class for reports home screen.
 */
public class ReportsHomeScreenController implements Initializable {

    @FXML
    private Button backButton;
    
    @FXML
    private Button bookingPercentagesReportButton;
    
    @FXML
    private Button feedbackReportButton;
    
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
        mainStage.centerOnScreen();
        mainStage.setMaximized(true);
    }
    
    public void onBookingPercentagesReportButtonClicked() throws IOException {
        Stage mainStage = 
            (Stage) bookingPercentagesReportButton.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(
            getClass().getResource("/fxml/BookingsReportScreen.fxml")
        );
        Scene bookingPercentagesReportScreen = new Scene(newRoot);
        mainStage.setScene(bookingPercentagesReportScreen);
        mainStage.centerOnScreen();
        mainStage.setMaximized(true);
    }
    
    //Opens FeedbackReportScreen.fxml when button Feedback Report button is clicked
     public void onFeedbackReportButtonClicked() throws IOException{
        Stage mainStage = (Stage) feedbackReportButton.getScene().getWindow();
        Parent staffHomeScreenRoot = FXMLLoader.load(
            getClass().getResource("/fxml/FeedbackReportScreen.fxml")
        );
        Scene staffHomeScreen = new Scene(staffHomeScreenRoot);
        mainStage.setScene(staffHomeScreen);
        mainStage.centerOnScreen();
        mainStage.setMaximized(true);
    }
}
