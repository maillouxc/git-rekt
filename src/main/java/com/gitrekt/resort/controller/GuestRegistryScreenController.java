package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.services.BookingService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 * The FXML controller class for the guest registry screen.
 *
 * NOTE: Even though this screen is intended to give information about guests, you'll find that
 * most of the things in this class refer to bookings. This is because a guest can have more than
 * one booking at a time; we want to be able to handle this behavior without complicated duplicate
 * handling logic, so we chose to display the bookings and treat them as though they were guests.
 */
public class GuestRegistryScreenController implements Initializable {

    @FXML
    private TableView<Booking> registryTable;

    @FXML
    private TableColumn<Booking, String> guestNameColumn;

    @FXML
    private TableColumn<Booking, Boolean> checkedInColumn;

    @FXML
    private TableColumn<Booking, Long> bookingNumberColumn;

    private ObservableList<Booking> bookings;

    /**
     * Initializes the FXML controller class.
     *
     * Called by JavaFX.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Prepare to display the data
        bookings = FXCollections.observableArrayList();
        registryTable.setItems(bookings);
        guestNameColumn.setCellValueFactory(
            (param) -> {
                return new SimpleStringProperty(
                    String.valueOf(param.getValue().getGuest().getLastName() + " , "
                        + param.getValue().getGuest().getFirstName())
                );
            }
        );

        checkedInColumn.setCellValueFactory((param) -> {
            return new SimpleBooleanProperty(param.getValue().isCheckedIn());
        });

        // Use a check box to display booleans rather than a string
        checkedInColumn.setCellFactory(
            (param) -> {
                return new CheckBoxTableCell<>();
            }
        );

        bookingNumberColumn.setCellValueFactory(
            (param) -> {
                return new SimpleLongProperty(
                    param.getValue().getId()
                ).asObject();
            }
        );

        // Load the registry data from the database
        BookingService bookingService = new BookingService();
        bookings.addAll(bookingService.getDailyRegistry());
    }

    /**
     * Checks in the selected guest/booking.
     */
    public void onCheckInButtonClicked() {
        Booking selectedBooking = getSelectedBooking();
        BookingService bookingService = new BookingService();
        try {
            selectedBooking.setCheckedIn(true);
        } catch (IllegalStateException e) {
            displayBookingHasAlreadyBeenCheckedInError();
        }
        bookingService.updateBooking(selectedBooking);
        registryTable.refresh();
    }

    private void displayBookingHasAlreadyBeenCheckedInError() {
        Alert errorDialog = new Alert(AlertType.ERROR);
        errorDialog.setTitle("Error");
        errorDialog.setHeaderText("Cannot check in the selected booking");
        errorDialog.setContentText("This booking has already been checked in previously");
        errorDialog.showAndWait();
    }

    /**
     * Checks out the selected guest/booking.
     */
    public void onCheckOutButtonClicked() {
        Booking selectedBooking = getSelectedBooking();
        BookingService bookingService = new BookingService();
        selectedBooking.setCheckedIn(false);
        bookingService.updateBooking(selectedBooking);
        registryTable.refresh();
    }

    /**
     * Returns to the staff home screen.
     */
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/StaffHomeScreen.fxml");
    }

    /**
     * @return The currently selected guest/booking in the registry table view.
     */
    private Booking getSelectedBooking() {
        return registryTable.getSelectionModel().getSelectedItem();
    }
}
