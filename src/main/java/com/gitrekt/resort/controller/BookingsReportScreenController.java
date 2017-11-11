package com.gitrekt.resort.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
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
    
    private LocalDateTime selectedMonth;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Default month is the current month.
        selectedMonth = LocalDateTime.now().withDayOfMonth(1);
        // TODO: Test using test data, then implement with real logic.
        Data<String,Number> testData = new Data<String,Number>();
        roomCategoriesList = FXCollections.observableArrayList();
        roomCategoriesDataSeries = new XYChart.Series<>();
        roomCategoriesDataSeries.setData(roomCategoriesList);
    }    
    
    @FXML
    private void onPickMonthButtonClicked() {
        // TODO
    }
    
    @FXML
    private void onNextMonthButtonClicked() {
        selectedMonth = selectedMonth.with(
            TemporalAdjusters.firstDayOfNextMonth()
        );
        monthYearLabel.setText(getCurrentMonthYearString());
    }
    
    @FXML
    private void onPreviousMonthButtonClicked() {
        selectedMonth = selectedMonth.minusMonths(1);
        selectedMonth = selectedMonth.withDayOfMonth(1);
        monthYearLabel.setText(getCurrentMonthYearString());
    }
    
    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/ReportsHomeScreen.fxml"
        );
    }
    
    public void onSelectMonthButtonClicked() {
        // TODO
        monthYearLabel.setText(getCurrentMonthYearString());
    }
    
    private void populateData() {
        // TODO
    }
    
    private String getCurrentMonthYearString() {
        return selectedMonth.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.US)
                + " "
                + selectedMonth.getYear();
    }
    
}
