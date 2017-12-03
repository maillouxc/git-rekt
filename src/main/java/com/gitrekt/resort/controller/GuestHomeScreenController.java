package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.services.BookingService;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextInputDialog;
import javax.persistence.EntityNotFoundException;

/**
 * FXML Controller class for the guest home screen.
 */
public class GuestHomeScreenController implements Initializable {

    /**
     * Initializes the FXML controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Intentionally blank.
    }

    @FXML
    private void onBrowseRoomsButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/BrowseRoomsScreen.fxml");
    }

    @FXML
    private void onLeaveFeedbackButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/LeaveFeedbackScreen.fxml");
    }

    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/HomeScreen.fxml");
    }

    @FXML
    private void onViewBookingButtonClicked() throws IOException {
        // Show a dialog that allows the user to enter their booking number.
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Find booking");
        dialog.setHeaderText("Enter booking number");
        dialog.setContentText("Please enter your booking number: ");

        // Get the result from the dialog.
        Optional<String> result = dialog.showAndWait();

        // If the result isn't empty, look up the booking number in the DB and show the details
        if(result.isPresent()) {
            Long bookingId = Long.valueOf(result.get());
            BookingService bookingService = new BookingService();
            try {
                Booking booking = bookingService.getBookingById(bookingId);
                // Force loading of booking from DB (because of lazy loading)
                System.out.println(booking.toString());
                Object temp = ScreenManager.getInstance().switchToScreen(
                    "/fxml/BookingDetailsScreen.fxml"
                );
                BookingDetailsScreenController controller = (BookingDetailsScreenController) temp;
                controller.intializeBookingData(booking);
            } catch (EntityNotFoundException e) {
                // If the booking isn't found, just let the dialog close.
            }
        }
    }

}
