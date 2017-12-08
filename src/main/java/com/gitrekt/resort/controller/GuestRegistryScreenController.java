package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.entities.Guest;
import com.gitrekt.resort.model.services.BookingService;
import com.gitrekt.resort.model.services.GuestService;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 * The FXML controller class for the guest registry screen.
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
        LocalDate today = LocalDate.now();
        Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        BookingService bookingtService = new BookingService();
        bookings.addAll(bookingtService.getBookingsBetweenDates(date,date));
    }

    /**
     * Checks in the selected guest.
     */
    public void onCheckInButtonClicked() {
        Booking selectedBooking = getSelectedBooking();
        // Instead of getting the selected guest, you need to get the booking number that was selected - 
        if(!selectedBooking.wasCheckedIn()) {
            BookingService bookingService = new BookingService();
            selectedBooking.setIsCheckedIn(true);
            bookingService.updateBooking(selectedBooking);
            registryTable.refresh();
        }

    }

    /**
     * Checks out the selected guest.
     */
    public void onCheckOutButtonClicked() {
        Booking selectedBooking = getSelectedBooking();
        BookingService bookingService = new BookingService();
        selectedBooking.setIsCheckedIn(true);
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
     * @return The currently selected guest in the registry table view.
     */
    private Booking getSelectedBooking() {
        return registryTable.getSelectionModel().getSelectedItem();
    }
}
