package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.services.BookingService;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class for the guest home screen.
 */
public class GuestHomeScreenController implements Initializable {

    @FXML
    private Button viewBookingButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Find booking");
        dialog.setHeaderText("Enter booking number");
        dialog.setContentText("Please enter your booking number: ");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            Long bookingId = Long.valueOf(result.get());
            BookingService bookingService = new BookingService();
            Booking booking = bookingService.getBookingById(bookingId);
            Object temp = ScreenManager.getInstance().switchToScreen(
                "/fxml/BookingDetailsScreen.fxml"
            );
            BookingDetailsScreenController controller = (BookingDetailsScreenController) temp;
            controller.intializeBookingData(booking);
        }
    }
}
