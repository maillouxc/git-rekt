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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 * FXML Controller class for the management bookings report screen.
 */
public class BookingsReportScreenController implements Initializable {

    @FXML
    private LineChart<String, Number> lineChart;
    
    @FXML
    private Label monthYearLabel;
    
    private List<String> categories;
    
    private ObservableList<String> daysInCurrentMonth;
    
    private ObservableList<XYChart.Series<String, Number>> data;
    
    private LocalDateTime selectedMonth;
    
    private List<Booking> bookingsForMonth;
    
    /**
     * Initializes the controller class.
     * 
     * This class needs really major refactoring, but that's just a matter of
     * time. When I can, I'll be ripping this class apart and putting it back
     * together much more nicely.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Default month is the current month
        selectedMonth = LocalDateTime.now().withDayOfMonth(1);
        monthYearLabel.setText(getCurrentMonthYearString());
        daysInCurrentMonth = FXCollections.observableArrayList();
        initializeChart();
    }    
    
    /**
     * Displays the graph data for the next month.
     */
    @FXML
    private void onNextMonthButtonClicked() {
        selectedMonth = selectedMonth.with(
            TemporalAdjusters.firstDayOfNextMonth()
        );
        monthYearLabel.setText(getCurrentMonthYearString());
        initializeChart();
    }
    
    /**
     * Displays the data for the previous month.
     */
    @FXML
    private void onPreviousMonthButtonClicked() {
        selectedMonth = selectedMonth.minusMonths(1);
        selectedMonth = selectedMonth.withDayOfMonth(1);
        monthYearLabel.setText(getCurrentMonthYearString());
        initializeChart();
    }
    
    /**
     * Returns to the home screen for staff reports.
     */
    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/ReportsHomeScreen.fxml"
        );
    }
    
    /**
     * Initializes the chart.
     */
    private void initializeChart() {
        // Clear any previous data
        daysInCurrentMonth.clear();
        data = FXCollections.observableArrayList();
        data.clear();
                
        prepareCategoriesList();
        getBookingsForMonth();
        
        // Prepare the x-axis - this is a little weird but necessary
        int selectedYear = selectedMonth.getYear();
        int numDaysInMonth = selectedMonth.getMonth()
                .length(Year.isLeap(selectedYear));
        for(int i = 1; i <= numDaysInMonth; i++) {
            daysInCurrentMonth.add(String.valueOf(i));
        }
        CategoryAxis x  = (CategoryAxis) lineChart.getXAxis();
        x.setCategories(daysInCurrentMonth);
        
        showDataForAllCategories();
    }
    
    /**
     * Displays on the chart the report data for the selected month.
     */
    private void showDataForAllCategories() {
        // Clear any existing data
        this.data.clear();
        
        for(String category : categories) {
            XYChart.Series<String, Number> categoryData;
            categoryData = new XYChart.Series<>();
            categoryData.setName(category);
        
            for(int i = 1; i <= daysInCurrentMonth.size(); i++) {
                XYChart.Data<String, Number> dayData = new XYChart.Data<>();
                dayData.setXValue(String.valueOf(i));
                double percentBooked;
                percentBooked = getPercentBookedInCategoryOnDay(category, i);
                dayData.setYValue(percentBooked);
                categoryData.getData().add(dayData);
            }
            
            this.data.add(categoryData);   
        }
        lineChart.setData(this.data);
    }
    
    /**
     * Gathers the list of all room categories from the database, and also adds
     * a category called "All" which is used to display data about all 
     * categories.
     */
    private void prepareCategoriesList() {
        this.categories = FXCollections.observableArrayList();
        
        RoomService roomService = new RoomService();
        List<RoomCategory> categories = roomService.getAllRoomCategories();
        List<String> result = new ArrayList<>();
        for(RoomCategory cat : categories) {
            this.categories.add(cat.getName());
        }
        this.categories.add("All");
    }
    
    /**
     * @return A string represenation of the currently selected month and year,
     * for example, "November 2017".
     */
    private String getCurrentMonthYearString() {
        return selectedMonth.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.US)
                + " "
                + selectedMonth.getYear();
    }
    
    /**
     * Initializes the class field containing the list of all bookings at the
     * resort for the given month.
     */
    private void getBookingsForMonth() {
        BookingService bookingService = new BookingService();
        // We have to use the old date API here because of JPA specs
        Date d1 = Date.from(selectedMonth.toInstant(ZoneOffset.UTC));
        LocalDateTime lastDayInSelectedMonth = 
            this.selectedMonth.with(TemporalAdjusters.lastDayOfMonth());
        Date d2 = Date.from(lastDayInSelectedMonth.toInstant(ZoneOffset.UTC));
        this.bookingsForMonth = bookingService.getBookingsBetweenDates(d1, d2);
    }
    
    /**
     * @param category The room category name in question.
     * @param dayOfMonth The day of the selected month in question.
     * 
     * @return The number of rooms booked in the given category on the given
     * day of the selected month.
     */
    private int getNumBookedInCategoryOnDay(
        String category, int dayOfMonth
    ) {    
        LocalDateTime day = selectedMonth.withDayOfMonth(dayOfMonth);
        Date date = Date.from(day.toInstant(ZoneOffset.UTC));
        
        int result = 0;
        
        // Handle special case of "All" category
        if(category.equals("All")) {           
            for(Booking b : bookingsForMonth) {
                if(b.getCheckInDate().compareTo(date) <= 0
                        && b.getCheckOutDate().compareTo(date) >= 0) {
                    result += b.getBookedRooms().size();
                }
            }  
        }    
        
        // Handle general case of specific category
        for(Booking b : bookingsForMonth) {            
            if(b.getCheckInDate().compareTo(date) <= 0
                    && b.getCheckOutDate().compareTo(date) >= 0) {                
                for(Room room : b.getBookedRooms()) {
                    String cat = room.getRoomCategory().getName();
                    if(cat.equals(category)) {
                        result++;
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * Returns the total percentage of the rooms booked in the given category
     * on the given day of the month.
     * 
     * @param category The room category in question.
     * @param dayOfMonth The day of the month (e.g. 1-31) in question.
     * @return The percentage booked as a double.
     */
    private double getPercentBookedInCategoryOnDay(
        String category, int dayOfMonth
    ) {
        int numBookedInCat = getNumBookedInCategoryOnDay(
            category, dayOfMonth
        );
        int numInCat = getNumRoomsInCategory(category);
        
        try {
            return (numBookedInCat * 100) / numInCat;
        } catch (ArithmeticException e) { // Catch divide by 0
            return 0.0;
        }
    }
    
    /** 
     * Gets the number of total rooms in the resort in the provided category.
     */
    private int getNumRoomsInCategory(String category) {
        // Handle special case of "All" category
        if(category.equals("All")) {
            int totalNumRoomsInResort = 0;
            for(String cat : this.categories) {
                if(!cat.equals("All")) {
                    totalNumRoomsInResort += getNumRoomsInCategory(cat);
                }
            }
            return totalNumRoomsInResort;
        }
        
        // Handle general case
        RoomService roomService = new RoomService();
        List<Room> allRoomsInCat = roomService.getAllRoomsInCategory(category);
        return allRoomsInCat.size();
    }
    
}
