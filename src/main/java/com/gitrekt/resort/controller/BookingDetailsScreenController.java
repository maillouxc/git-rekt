package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.BookingDetailEntry;
import com.gitrekt.resort.model.GuestBill;
import com.gitrekt.resort.model.RoomCategory;
import com.gitrekt.resort.view.BookingDetailListItem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This class does all the functionality of BookingDetailsScreen.fxml
 *
 */
public class BookingDetailsScreenController implements Initializable {
    
    @FXML
    private Button backButton;
    
    @FXML
    private ListView guestBookingList;
    
    @FXML
    private Label guestName;
    
    private ObservableList<BookingDetailEntry> guestBookings;
    
    public BookingDetailsScreenController(){
    guestBookings = FXCollections.observableArrayList();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        guestBookingList.setItems(guestBookings);
         guestBookingList.setCellFactory(param -> new BookingDetailListItem() {
            {
                prefWidthProperty().bind( guestBookingList.widthProperty());
            }
        });
         /*Test data for the ListView
      
         //TODO fix the screen size
         */
        BookingDetailEntry[] dummyBookingData = {new BookingDetailEntry("02/13/1999","03/14/2000","432","G325133",
                new RoomCategory("","This room features a 40-inch flat-screen TV with high-definition channels.",
                        new Image("/images/temporary_hotel_room_image_placeholder.jpg"),""),new GuestBill())};
        getGuestBookings(dummyBookingData);
    }    
    
    /**
     * Gets all the booking from the guest and makes a list of them.
     * 
     * @param bookings The BookingDetailEntry to add.
     */
    public void getGuestBookings(BookingDetailEntry[] bookings) {
        int numberOfCurrentBookings = bookings.length;
        for(int i = 0; i < numberOfCurrentBookings; i++){
            guestBookings.add(bookings[i]);
        }
        
            
        
    }

   /**
     * Closes the browse-rooms screen and returns the application to the guest
     * home screen.
     * 
     * @throws IOException
     */
    @FXML
    protected void onBackButtonClicked() throws IOException {
        Stage mainStage = (Stage) backButton.getScene().getWindow();
        Parent guestHomeScreenRoot = FXMLLoader.load(
                getClass().getResource("/fxml/GuestHomeScreen.fxml")
        );
        
        Scene guestHomeScreen = new Scene(guestHomeScreenRoot);
        
        mainStage.setScene(guestHomeScreen);
        mainStage.centerOnScreen();
    }
    
    
}
