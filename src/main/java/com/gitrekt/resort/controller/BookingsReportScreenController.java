package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.entities.Room;
import com.gitrekt.resort.model.entities.RoomCategory;
import com.gitrekt.resort.model.services.BookingService;
import com.gitrekt.resort.model.services.RoomService;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javafx.scene.chart.XYChart.Series;
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
    
    private ObservableList<Series<String,Number>> categoryData;
    
    private CategoryAxis xAxis;
    
    private NumberAxis yAxis;
    
    private ObservableList<String> daysInCurrentMonth;
    
    private LocalDateTime selectedMonth;
    
    private List<Booking> bookingsForMonth;
    
    // TODO: Remove
    private List<String> printStatements = new ArrayList<>();
    
    /**
     * Initializes the controller class.
     * 
     * This class needs really major refactoring, but that's just a matter of
     * time. When I can, I'll be ripping this class apart and putting it back
     * together much more nicely.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Default month is the current month.
        selectedMonth = LocalDateTime.now().withDayOfMonth(1);
        daysInCurrentMonth = FXCollections.observableArrayList();
        categoryData = FXCollections.observableArrayList();
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
        // Clear any previous data
        daysInCurrentMonth.clear();
        categoryData.clear();
        
        // Prepare the x-axis
        int selectedYear = selectedMonth.getYear();
        int numDaysInMonth = selectedMonth.getMonth()
                .length(Year.isLeap(selectedYear));
        for(int i = 1; i <= numDaysInMonth; i++) {
            daysInCurrentMonth.add(String.valueOf(i));
        }
        barChart.getXAxis().setAutoRanging(false);
        CategoryAxis x  = (CategoryAxis) barChart.getXAxis();
        x.setCategories(daysInCurrentMonth);
        
        // Prepare the graphed data
        prepareCategoryData();
        
        // Display the now prepared data
        barChart.setData(categoryData);
        
        // TODO: Remove
        for(String s : printStatements) {
            System.out.println(s);
        }
    }
    
    private String getCurrentMonthYearString() {
        return selectedMonth.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.US)
                + " "
                + selectedMonth.getYear();
    }
    
    private void getBookingsForMonth() {
        BookingService bookingService = new BookingService();
        // We have to use the old date API here because of JPA specs
        Date d1 = new Date(
            this.selectedMonth.toEpochSecond(ZoneOffset.UTC)
        );
        LocalDateTime lastDayInSelectedMonth = 
            this.selectedMonth.with(TemporalAdjusters.lastDayOfMonth());
        Date d2 = new Date(
            lastDayInSelectedMonth.toEpochSecond(ZoneOffset.UTC)
        );
        this.bookingsForMonth = bookingService.getBookingsBetweenDates(d1, d2);
    }
    
    private void prepareCategoryData() {
        getBookingsForMonth();
        List<String> roomCategories = getRoomCategories();
        
        for(String category : roomCategories) {
            XYChart.Series<String, Number> categoryData;
            categoryData = new XYChart.Series<>();
            
            for(int i = 1; i <= daysInCurrentMonth.size(); i++) {
                XYChart.Data<String, Number> dayData = new XYChart.Data<>();
                dayData.setXValue(String.valueOf(i));
                double bookedPercent = getPercentBookedInCategoryOnDay(
                    category, i
                );
                dayData.setYValue(bookedPercent);
                categoryData.getData().add(dayData);
            }
            
            this.categoryData.add(categoryData);
        }
    }
    
    private int getNumBookedInCategoryOnDay(
        String category, int dayOfMonth
    ) {        
        LocalDateTime temp = (selectedMonth.withDayOfMonth(dayOfMonth));
        Date d = new Date(temp.toEpochSecond(ZoneOffset.UTC));
        List<Booking> bookingsOnDayInCat = new ArrayList<>();
        for(Booking b : bookingsForMonth) {
            if(b.getCheckInDate().compareTo(d) <= 1
                    && b.getCheckOutDate().compareTo(d) >= 1) {
                
                for(Room room : b.getBookedRooms()) {
                    if(room.getRoomCategory().getName().equals(category)) {
                        bookingsOnDayInCat.add(b);
                    }
                }
            }
        }
        return bookingsOnDayInCat.size();
    }
    
    private double getPercentBookedInCategoryOnDay(
        String category, int dayOfMonth
    ) {
        int numBookedInCat = getNumBookedInCategoryOnDay(
            category, dayOfMonth
        );
        int numInCat = getNumRoomsInCategory(category);
        
        // TODO REMOVE
        printStatements.add("There are " + ((numBookedInCat * 100) / numInCat) + " " + category + " rooms booked on day " + dayOfMonth);
        
        return (numBookedInCat * 100) / numInCat;
    }
    
    private int getNumRoomsInCategory(String category) {
        RoomService roomService = new RoomService();
        List<Room> allRoomsInCat = roomService.getAllRoomsInCategory(category);
        return allRoomsInCat.size();
    }
    
    private List<String> getRoomCategories() {
        RoomService roomService = new RoomService();
        List<RoomCategory> categories = roomService.getAllRoomCategories();
        List<String> result = new ArrayList<>();
        for(RoomCategory cat : categories) {
            result.add(cat.getName());
        }
        return result;
    }
    
}
