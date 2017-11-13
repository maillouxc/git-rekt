package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.services.BookingService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        controller.initializeData(this.booking);
    }
    
    @FXML
    private void onCancelBookingButtonClicked() {
        // TODO
    }
    
}
