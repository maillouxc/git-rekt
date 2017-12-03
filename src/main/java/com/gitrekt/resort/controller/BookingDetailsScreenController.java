package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.entities.Guest;
import com.gitrekt.resort.model.entities.Package;
import com.gitrekt.resort.model.entities.Room;
import com.gitrekt.resort.model.services.BookingService;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class for the booking details screen.
 */
public class BookingDetailsScreenController implements Initializable {

    @FXML
    private TableView<Room> bookedRoomsTableView;

    @FXML
    private TableColumn<Room, String> roomNumberColumn;

    @FXML
    private TableColumn<Room, String> roomCategoryColumn;

    @FXML
    private TableView<BookedPackagesWrapper> bookedPackagesTableView;

    @FXML
    private TableColumn<BookedPackagesWrapper, String> packageNameColumn;

    @FXML
    private TableColumn<BookedPackagesWrapper, Integer> packageQuantityColumn;

    @FXML
    private Label guestNameLabel;

    @FXML
    private Label checkInDateLabel;

    @FXML
    private Label checkOutDateLabel;

    @FXML
    private Label bookingCanceledLabel;

    private Booking booking;

    private ObservableList<Room> bookedRooms;

    private ObservableList<BookedPackagesWrapper> bookedPackagesList;

    /**
     * Initializes the controller class.
     *
     * Mainly just prepares the tables and configures them.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookedRooms = FXCollections.observableArrayList();
        bookedRoomsTableView.setItems(bookedRooms);
        roomNumberColumn.setCellValueFactory(
            (param) -> {
                return new SimpleStringProperty(
                    String.valueOf(param.getValue().getRoomNumber())
                );
            }
        );
        roomCategoryColumn.setCellValueFactory(
            (param) -> {
                return new SimpleStringProperty(
                    param.getValue().getRoomCategory().getName()
                );
            }
        );

        bookedPackagesList = FXCollections.observableArrayList();
        bookedPackagesTableView.setItems(bookedPackagesList);
        packageNameColumn.setCellValueFactory(
            (param) -> {
                return new SimpleStringProperty(
                    param.getValue().packageName
                );
            }
        );
        packageQuantityColumn.setCellValueFactory(
            (param) -> {
                return new SimpleIntegerProperty(
                    param.getValue().bookedQty
                ).asObject();
            }
        );
    }

    /**
     * Initializes a reference to the booking in question to allow the screen to display information
     * about it without having to hit the database. Once this method is called, the information is
     * displayed on the screen automatically.
     *
     * @param booking The booking to display information about.
     */
    public void intializeBookingData(Booking booking) {
        this.booking = booking;

        Guest guest = booking.getGuest();
        String guestNameLastFirst = guest.getLastName() + ", " + guest.getFirstName();
        this.guestNameLabel.setText(guestNameLastFirst);
        this.checkInDateLabel.setText(booking.getCheckInDate().toString());
        this.checkOutDateLabel.setText(booking.getCheckOutDate().toString());

        bookedRooms.addAll(booking.getBookedRooms());

        for(Package p : booking.getPackages()) {
            boolean alreadyPresent = false;
            for(BookedPackagesWrapper w : bookedPackagesList) {
                if(w.packageName.equals(p.getName())) {
                    alreadyPresent = true;
                    w.bookedQty++;
                }
            }
            if(!alreadyPresent) {
                BookedPackagesWrapper wrapper = new BookedPackagesWrapper(p.getName(), 1);
                bookedPackagesList.add(wrapper);
            }
        }
    }

    /**
     * Returns to the Guest home screen.
     */
    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/GuestHomeScreen.fxml");
    }

    /**
     * Displays the customer bill screen for the booking displayed by this screen.
     */
    @FXML
    private void onViewBillButtonClicked() {
        Object temp = ScreenManager.getInstance().switchToScreen("/fxml/CustomerBillScreen.fxml");
        CustomerBillScreenController controller = (CustomerBillScreenController) temp;
        // Fix LazyInitializationException by forcing initialization here with println
        System.out.println(this.booking.toString());
        controller.initializeData(this.booking);
    }

    /**
     * Displays a confirmation dialog showing the price that will be charged as a cancellation fee.
     */
    @FXML
    private void onCancelBookingButtonClicked() {
        double cancellationFee = BookingService.calcCancellationFee(this.booking);
        String cancellationFeeString = String.format("$%.2f", cancellationFee);
        // Show confirmation dialog
        Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirm");
        confirmationDialog.setHeaderText("Confirm Cancel Booking");

        // Check for the case where there is no fee because of the 24 hr window
        if(cancellationFee < 0.01) {
            confirmationDialog.setContentText("You will not be assessed a cancellation fee."
                    + " - please confirm that you want to cancel this booking."
                    + " This action cannot be undone.");
        } else {
            confirmationDialog.setContentText("You will be assessed a cancellation fee of "
                + cancellationFeeString
                + " - please confirm that you want to cancel this booking. "
                + "This action cannot be undone.");
        }

        // Show the dialog and get the result
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if(result.get() == ButtonType.OK) {
            // User chose OK
            BookingService bookingService = new BookingService();
            bookingService.cancelBooking(this.booking);
            bookingCanceledLabel.setVisible(true);
        }
    }

    /**
     * It might have been better to use a Map here instead of an inner class but in practice it's
     * simple enough - this simple little wrapper helps us associate a quantity with a package.
     */
    private class BookedPackagesWrapper {
        String packageName;
        Integer bookedQty;

        BookedPackagesWrapper(String packageName, Integer bookedQty) {
            this.packageName = packageName;
            this.bookedQty = bookedQty;
        }
    }

}
