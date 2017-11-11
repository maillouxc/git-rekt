package com.gitrekt.resort.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class for the management bookings report screen.
 */
public class BookingsReportScreenController implements Initializable {

    @FXML
    private StackedBarChart<String, Number> barChart;
    
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

    private XYChart.Series<Number, Number> roomCategoriesDataSeries;
    
    private CategoryAxis xAxis;
    
    private NumberAxis yAxis;
    
    private ObservableList<String> daysInCurrentMonth;
    
    private LocalDateTime selectedMonth;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Default month is the current month.
        selectedMonth = LocalDateTime.now().withDayOfMonth(1);
        daysInCurrentMonth = FXCollections.observableArrayList();
        prepareGraph();
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
        prepareGraph();
    }
    
    @FXML
    private void onPreviousMonthButtonClicked() {
        selectedMonth = selectedMonth.minusMonths(1);
        selectedMonth = selectedMonth.withDayOfMonth(1);
        monthYearLabel.setText(getCurrentMonthYearString());
        prepareGraph();
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
        prepareGraph();
    }
    
    private void prepareGraph() {
        // Prepare the x-axis, the days in the month
        daysInCurrentMonth.clear();
        int selectedYear = selectedMonth.getYear();
        int numDaysInMonth = selectedMonth.getMonth()
                .length(Year.isLeap(selectedYear));
        for(int i = 1; i <= numDaysInMonth; i++) {
            daysInCurrentMonth.add(String.valueOf(i));
        }
        barChart.getXAxis().setAutoRanging(false);
        CategoryAxis x  = (CategoryAxis) barChart.getXAxis();
        x.setCategories(daysInCurrentMonth);
    }
    
    private String getCurrentMonthYearString() {
        return selectedMonth.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.US)
                + " "
                + selectedMonth.getYear();
    }
    
}
