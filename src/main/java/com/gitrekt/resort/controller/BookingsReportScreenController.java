package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class for the management bookings report screen.
 */
public class BookingsReportScreenController implements Initializable {

    @FXML
    private StackedBarChart barChart;
    
    @FXML
    private Label monthYearLabel;
    
    @FXML
    private Button nextMonthButton;
    
    @FXML
    private Button previousMonthButton;
    
    @FXML
    private Button pickMonthButton;
    
    @FXML
    private Button backButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onPickMonthButtonClicked() {
        // TODO
    }
    
    @FXML
    private void onNextMonthButtonClicked() {
        // TODO
    }
    
    @FXML
    private void onPreviousMonthButtonClicked() {
        // TODO
    }
    
    @FXML
    private void onBackButtonClicked() {
        // TODO
    }
    
}
