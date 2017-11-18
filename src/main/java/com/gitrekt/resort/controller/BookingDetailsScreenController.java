package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.services.BookingService;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 * FXML Controller class for the booking details screen.
 */
public class BookingDetailsScreenController implements Initializable {

    // TODO fix rawtype
    @FXML
    private TableView bookedRoomsTableView;

    // TODO fix rawtype
    @FXML
    private TableView bookedPackagesTableView;

    @FXML
    private Label guestNameLabel;

    @FXML
    private Label checkInDateLabel;

    @FXML
    private Label checkOutDateLabel;

    private Booking booking;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Remove test code
        BookingService bookingService = new BookingService();
        this.booking = bookingService.getBookingById(1L);
    }

    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/GuestHomeScreen.fxml"
        );
    }

    @FXML
    private void onViewBillButtonClicked() {
        Object temp = ScreenManager.getInstance().switchToScreen(
            "/fxml/CustomerBillScreen.fxml"
        );
        CustomerBillScreenController controller;
        controller = (CustomerBillScreenController) temp;

        // Fix LazyInitializationException by forcing initialization here.
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
        confirmationDialog.setContentText("You will be assessed a cancellation fee of "
                + cancellationFeeString
                + " - please confirm that you want to cancel this booking. "
                + "This action cannot be undone.");

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if(result.get() == ButtonType.OK) {
            // User chose OK
            BookingService bookingService = new BookingService();
            bookingService.cancelBooking(this.booking);
            onBackButtonClicked();
        }
    }

}
