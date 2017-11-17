package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.RoomSearchResult;
import com.gitrekt.resort.model.entities.RoomCategory;
import com.gitrekt.resort.model.services.BookingService;
import com.gitrekt.resort.view.BrowseRoomsListItem;
import com.gitrekt.resort.view.DeletableListItem;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

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
    
    private void initializeDatePickers() {
        // TODO
    }
    
    @FXML
    private void onCheckInDateSelected() {
        // TODO
    }
    
    @FXML
    private void onCheckOutDateSelected() {
        // TODO
    }
    
    private void sortResultsByPrice() {
        roomSearchResults.sort(
            (RoomSearchResult r1, RoomSearchResult r2) -> 
                r1.getRoomPrice().compareTo(r2.getRoomPrice())
        );
    }
    
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
