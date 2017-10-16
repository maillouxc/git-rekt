package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.RoomCategory;
import com.gitrekt.resort.model.RoomSearchResult;
import com.gitrekt.resort.view.AvailableRoomListItem;
import java.io.IOException;
import java.math.BigDecimal;
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
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class for the browse rooms screen.
 */
public class BrowseRoomsScreenController implements Initializable {

    @FXML
    private Button backButton;
    
    @FXML
    private ListView<RoomSearchResult> roomsListView;
    
    @FXML
    private Button findAvailableRoomsButton;
    
    private ObservableList<RoomSearchResult> roomSearchResults;
    
    public BrowseRoomsScreenController() {
        roomSearchResults = FXCollections.observableArrayList();
    }
    
    /**
     * Initializes the controller class.
     * 
     * Don't touch. Magic.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roomsListView.setItems(roomSearchResults);
        roomsListView.setCellFactory(param -> new AvailableRoomListItem() {
            {
                prefWidthProperty().bind(roomsListView.widthProperty());
            }
        });

    }
    
    public void addResult(RoomSearchResult result) {
        if(!roomSearchResults.contains(result)) {
            roomSearchResults.add(result);
            sortResultsByPrice();
        }
    }
    
    public void removeResult(RoomSearchResult result) {
        roomSearchResults.remove(result);
    }
    
    private void sortResultsByPrice() {
        roomSearchResults.sort(
            (RoomSearchResult r1, RoomSearchResult r2) -> 
                r1.getRoomPrice().compareTo(r2.getRoomPrice())
        );
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
    }
    
    @FXML
    protected void onFindAvailableRoomsButtonClicked() {
        System.out.println("here");
        
        Image i = new Image("/images/temporary_hotel_room_image_placeholder.jpg");
        RoomSearchResult r4 = new RoomSearchResult("512", new BigDecimal("2419"), 
            new RoomCategory(
                "Basic Family Suite", 
                "This room features a 40-inch flat-screen TV with high-definition channels. An iPod docking station and coffee-making facilities are also provided.\n" + "\n" + "Room Facilities: Safe, Air conditioning, Iron, Heating, Carpeted, Interconnecting room(s) available, Hairdryer, Free toiletries, Toilet, Bathroom, Bathtub or shower, Telephone, Radio, Cable channels, Flat-screen TV, Refrigerator, Alarm clock",
                i,
                "2 King, 2 Twin"
            )
        );
        
        this.addResult(r4);
    }
    
}
