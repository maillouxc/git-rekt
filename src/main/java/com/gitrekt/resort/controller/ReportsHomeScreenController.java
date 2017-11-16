package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class for reports home screen.
 */
public class ReportsHomeScreenController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Returns to the staff home screen.
     */
    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/StaffHomeScreen.fxml"
        );
    }
    
    /**
     * Displays the booking percentages report screen.
     */
    @FXML
    private void onBookingPercentagesReportButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/BookingsReportScreen.fxml"
        );
    }
    
    /**
     * Displays the guest feedback report screen.
     */
    @FXML
    private void onFeedbackReportButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/FeedbackReportScreen.fxml"
        );
    }
}
