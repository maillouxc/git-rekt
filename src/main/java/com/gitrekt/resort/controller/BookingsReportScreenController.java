package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
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

    private XYChart.Series<String, Number> roomCategoriesDataSeries;
    
    private ObservableList<Data<String, Number>> roomCategoriesList;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Test using test data, then implement with real logic.
        Data<String,Number> testData = new Data<String,Number>();
        roomCategoriesList = FXCollections.observableArrayList();
        roomCategoriesDataSeries = new XYChart.Series<>();
        roomCategoriesDataSeries.setData(roomCategoriesList);
    }    
    
    public void onPickMonthButtonClicked() {
        // TODO
    }
    
    public void onNextMonthButtonClicked() {
        // TODO
    }
    
    public void onPreviousMonthButtonClicked() {
        // TODO
    }
    
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/ReportsHomeScreen.fxml"
        );
    }
    
    public void onSelectMonthButtonClicked() {
        // TODO
    }
    
}
