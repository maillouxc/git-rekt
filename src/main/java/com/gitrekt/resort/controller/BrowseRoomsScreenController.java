package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.RoomSearchResult;
import com.gitrekt.resort.model.entities.Package;
import com.gitrekt.resort.model.entities.RoomCategory;
import com.gitrekt.resort.model.services.BookingService;
import com.gitrekt.resort.model.services.PackageService;
import com.gitrekt.resort.view.BrowseRoomsListItem;
import com.gitrekt.resort.view.PackageListItem;
import com.gitrekt.resort.view.SelectedRoomListItem;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class BrowseRoomsScreenController implements Initializable, PackageListController {

    @FXML
    private ListView<RoomSearchResult> roomsListView;

    @FXML
    private ListView<RoomSearchResult> selectedRoomsListView;

    @FXML
    private ListView<Package> packagesListView;

    @FXML
    private DatePicker checkInDatePicker;

    @FXML
    private DatePicker checkOutDatePicker;

    @FXML
    private Button findAvailableRoomsButton;

    @FXML
    private Button addPackageButton;

    private final ObservableList<RoomSearchResult> roomSearchResults;

    private final ObservableList<RoomSearchResult> selectedRooms;

    private final ObservableList<Package> availablePackages;

    private final Map<Package, Integer> packageQtys = new HashMap<>();

    public BrowseRoomsScreenController() {
        roomSearchResults = FXCollections.observableArrayList();
        selectedRooms = FXCollections.observableArrayList();
        availablePackages = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roomsListView.setItems(roomSearchResults);
        roomsListView.setCellFactory(
            param -> new BrowseRoomsListItem(this) {
            {
                // Don't touch. Magic.
                prefWidthProperty().bind(roomsListView.widthProperty());
            }
        });
        roomsListView.setPlaceholder(new Label("No results."));
        selectedRoomsListView.setCellFactory(param -> new SelectedRoomListItem(this));
        selectedRoomsListView.setItems(selectedRooms);
        selectedRoomsListView.setPlaceholder(new Label("No rooms selected"));
        initializeDatePickers();

        // Initialize packages list
        PackageService packageService = new PackageService();
        List<Package> allPackages = packageService.getAllPackages();
        availablePackages.setAll(allPackages);
        packagesListView.setItems(availablePackages);
        packagesListView.setCellFactory(
            param -> new PackageListItem(this) {
            {
                // Don't touch. Magic.
                prefWidthProperty().bind(packagesListView.widthProperty());
            }
            }
        );
    }

    /**
     * Called by the search result list item controller to notify this class that the
     * "add to booking" button for the class has been clicked.
     *
     * @param roomData The RoomSearchResult contained in the clicked item.
     */
    public void onRoomSelected(RoomSearchResult roomData) {
        // If the currently selected room was the last in it's category, hide it from the list.
        if(roomData.getNumAvailable() == 0) {
            roomSearchResults.remove(roomData);
        }
        selectedRooms.add(roomData);
    }

    /**
     * Called by the selected rooms list item controller to notify this controller that the room
     * has been unselected and should be removed from the selected rooms list.
     *
     * @param roomData The RoomSearchResult for the room that was unselected.
     */
    public void onRoomUnselected(RoomSearchResult roomData) {
        // If the unselected room was the last available, add back it to the list of results.
        if(roomData.getNumAvailable() == 1) {
            roomSearchResults.add(roomData);
        }
        selectedRooms.remove(roomData);
    }

    /**
     * Ensures that the date pickers only allow selection of dates within the valid booking date
     * range, as defined in the specifications document.
     *
     * Chief among these rules is that bookings may not be placed more than one day in advance.
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
     * Clears the results list; doesn't really need to do anything else at the moment.
     */
    @FXML
    private void onCheckInDateSelected() {
        roomSearchResults.clear();
    }

    /**
     * Validates the checkout date to ensure that it is at least one day after the checkin date.
     *
     * If it isn't, disables the room search button until it is.
     */
    @FXML
    private void onCheckOutDateSelected() {
        LocalDate checkOutDate = checkOutDatePicker.getValue();
        LocalDate checkInDate = checkInDatePicker.getValue();
        if(checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
            checkOutDatePicker.getStyleClass().add("invalidField");
            checkOutDatePicker.setTooltip(
                new Tooltip("Checkout date cannot be on or before checkin date!")
            );
            findAvailableRoomsButton.setDisable(true);
        } else {
            checkOutDatePicker.getStyleClass().remove("invalidField");
            checkOutDatePicker.setTooltip(null);
            findAvailableRoomsButton.setDisable(false);
        }
        roomSearchResults.clear();
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
        ScreenManager.getInstance().switchToScreen("/fxml/GuestHomeScreen.fxml");
    }

     @FXML
    private void onAddPackageButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/PackageScreen.fxml");
    }

     /**
     * Searches the database for rooms that are available in the given date range.
     */
    @FXML
    private void onFindAvailableRoomsButtonClicked() {
        // The new date api is great. Converting back and forth, not so much.
        LocalDate checkInDateTemp = checkInDatePicker.getValue();
        LocalDate checkOutDateTemp = checkOutDatePicker.getValue();
        Instant temp1 = Instant.from(checkInDateTemp.atStartOfDay(ZoneId.systemDefault()));
        Instant temp2 = Instant.from(checkOutDateTemp.atStartOfDay(ZoneId.systemDefault()));
        Date checkInDate = Date.from(temp1);
        Date checkOutDate = Date.from(temp2);

        // Clear any existing results
        roomSearchResults.clear();
        selectedRooms.clear();

        // Get the new results
        BookingService bookingService = new BookingService();
        roomSearchResults.addAll(bookingService.getRoomTypesAvailable(checkInDate, checkOutDate));
    }

    @FXML
    private void onNextButtonClicked() {
        if(selectedRooms.size() <= 0) {
            return;
        }

        Object temp = ScreenManager.getInstance().switchToScreen("/fxml/PlaceBookingScreen.fxml");
        PlaceBookingScreenController controller = (PlaceBookingScreenController) temp;
        Map<RoomCategory, Integer> roomsData = new HashMap<>();
        LocalDate checkInDate = checkInDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();
        roomSearchResults.forEach((r) -> {
            roomsData.merge(r.getRoomCategory(), 1, Integer::sum);
        });
        controller.initializeData(roomsData, this.packageQtys, checkInDate, checkOutDate);
    }

    @Override
    public void updatePackageQty(Package p, int newValue) {
        packageQtys.merge(p, 1, Integer::sum);
    }

}
