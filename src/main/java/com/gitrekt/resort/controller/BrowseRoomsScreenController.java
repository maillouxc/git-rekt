package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.RoomSearchResult;
import com.gitrekt.resort.model.services.BookingService;
import com.gitrekt.resort.view.BrowseRoomsListItem;
import com.gitrekt.resort.view.DeletableListItem;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

/**
 * FXML Controller class for the browse rooms screen.
 */
public class BrowseRoomsScreenController implements Initializable,
        DeletableListItemDeletionListener {
    
    @FXML
    private ListView<RoomSearchResult> roomsListView;
    
    @FXML
    private ListView<RoomSearchResult> selectedRoomsListView;
    
    @FXML
    private DatePicker checkInDatePicker;
    
    @FXML
    private DatePicker checkOutDatePicker;
    
    @FXML
    private Button findAvailableRoomsButton;
    
    private final ObservableList<RoomSearchResult> roomSearchResults;
    
    private final ObservableList<RoomSearchResult> selectedRooms;
    
    public BrowseRoomsScreenController() {
        roomSearchResults = FXCollections.observableArrayList();
        selectedRooms = FXCollections.observableArrayList();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roomsListView.setItems(roomSearchResults);
        roomsListView.setCellFactory(param -> new BrowseRoomsListItem() {
            {
                // Don't touch. Magic.
                prefWidthProperty().bind(roomsListView.widthProperty());
            }
        });
        roomsListView.setPlaceholder(new Label("No results."));
        selectedRoomsListView.setCellFactory(
            param -> new DeletableListItem(this)
        );
        selectedRoomsListView.setItems(selectedRooms);
        selectedRoomsListView.setPlaceholder(new Label("No rooms selected"));
        initializeDatePickers();
    }
    
    /**
     * Ensures that the date pickers only allow selection of dates within the
     * valid booking date range, as defined in the specifications document.
     * 
     * Chief among these rules is that bookings may not be placed more than one
     * day in advance.
     */
    private void initializeDatePickers() {
        Callback<DatePicker, DateCell> dayCellFactory = 
            (final DatePicker datePicker) -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                
                    if(item.isAfter(LocalDate.now().plusYears(1))) {
                        setDisable(true);
                    }
                    if(item.isBefore(ChronoLocalDate.from(LocalDate.now()))) {
                        setDisable(true);
                    }
                }
            };
        
        // Disable selecting invalid check-in/check-out dates
        checkInDatePicker.setDayCellFactory(dayCellFactory);
        checkOutDatePicker.setDayCellFactory(dayCellFactory);
    }
    
    /**
     * Doesn't do anything at the moment, but may be needed later.
     */
    @FXML
    private void onCheckInDateSelected() {
        // Intentionally blank.
    }
    
    /**
     * Validates the checkout date to ensure that it is at least one day after 
     * the checkin date.
     * 
     * If it isn't, disables the room search button until it is.
     */
    @FXML
    private void onCheckOutDateSelected() {
        LocalDate checkOutDate = checkOutDatePicker.getValue();
        LocalDate checkInDate = checkInDatePicker.getValue();
        if(checkOutDate.isBefore(checkInDate)
            || checkOutDate.isEqual(checkInDate)) {
            checkOutDatePicker.getStyleClass().add("invalidField");
            checkOutDatePicker.setTooltip(
                new Tooltip(
                    "Checkout date cannot be on or before checkin date!"
                )
            );
            findAvailableRoomsButton.setDisable(true);
        } else {
            checkOutDatePicker.getStyleClass().remove("invalidField");
            checkOutDatePicker.setTooltip(null);
            findAvailableRoomsButton.setDisable(false);
        }
    }
    
    /**
     * Sorts results list by price, high to low.
     */
    private void sortResultsByPrice() {
        roomSearchResults.sort(
            (RoomSearchResult r1, RoomSearchResult r2) -> 
                r1.getRoomPrice().compareTo(r2.getRoomPrice())
        );
    }
    
    /**
     * Returns to the previous screen.
     */
    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/GuestHomeScreen.fxml"
        );
    }
    
    @FXML
    private void onFindAvailableRoomsButtonClicked() {
        // The new date api is great. Converting back and forth, not so much.
        LocalDate checkInDateTemp = checkInDatePicker.getValue();
        LocalDate checkOutDateTemp = checkOutDatePicker.getValue();
        Instant temp1 = Instant.from(
            checkInDateTemp.atStartOfDay(ZoneId.systemDefault())
        );
        Instant temp2 = Instant.from(
            checkOutDateTemp.atStartOfDay(ZoneId.systemDefault())
        );
        Date checkInDate = Date.from(temp1);
        Date checkOutDate = Date.from(temp2);
        
        // Clear any existing results
        roomSearchResults.clear();
        
        // Get the new results
        BookingService bookingService = new BookingService();
        roomSearchResults.addAll(
            bookingService.getRoomTypesAvailable(checkInDate, checkOutDate)
        );
    }
    
    @FXML
    private void onNextButtonClicked() {
        // TODO replace with the packages screen first - this is temporary
        
        if(selectedRooms.size() > 0) {
            ScreenManager.getInstance().switchToScreen(
                "/fxml/PlaceBookingScreen.fxml"
            );
        }
    }
    
    // TODO: Determine if really necessary
    /**
     * Handles item deletion events for deletable list items.
     */
    @Override
    public void onItemDeleted(DeletableListItem item) {
        // TODO implement
    }
    
}
