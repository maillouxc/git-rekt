package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
    
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/StaffHomeScreen.fxml"
        );
    }
    
    public void onBookingPercentagesReportButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/BookingsReportScreen.fxml"
        );
    }
    
     public void onFeedbackReportButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/FeedbackReportScreen.fxml"
        );
    }
}
