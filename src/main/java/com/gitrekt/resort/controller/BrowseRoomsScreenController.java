package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.RoomCategory;
import com.gitrekt.resort.model.RoomSearchResult;
import com.gitrekt.resort.view.BrowseRoomsListItem;
import com.gitrekt.resort.view.DeletableListItem;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

// TODO: Clean up this mess of a class; major refactoring needed.

/**
 * FXML Controller class for the browse rooms screen.
 */
public class BrowseRoomsScreenController implements Initializable,
        DeletableListItemDeletionListener {

    @FXML
    private Button backButton;
    
    @FXML
    private ListView<RoomSearchResult> roomsListView;
    
    @FXML
    private ListView<RoomSearchResult> currentlySelectedRoomsListView;
    
    @FXML
    private Button findAvailableRoomsButton;
    
    @FXML
    private Button nextButton;
    
    private ObservableList<RoomSearchResult> roomSearchResults;
    
    private ObservableList<RoomSearchResult> currentlySelectedRooms;
    
    public BrowseRoomsScreenController() {
        roomSearchResults = FXCollections.observableArrayList();
        currentlySelectedRooms = FXCollections.observableArrayList();
    }
    
    /**
     * Initializes the controller class.
     * 
     * Don't touch. Magic.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roomsListView.setItems(roomSearchResults);
        roomsListView.setCellFactory(param -> new BrowseRoomsListItem() {
            {
                prefWidthProperty().bind(roomsListView.widthProperty());
            }
        });
        currentlySelectedRoomsListView.setCellFactory(
            param -> new DeletableListItem(this)
        );
        currentlySelectedRoomsListView.setItems(currentlySelectedRooms);

        uiDemonstrationCode1RemoveLater();
    }
    
    private void uiDemonstrationCode1RemoveLater() {
        Random random = new Random();
        int randInt = random.nextInt(999);
        BigDecimal bd = new BigDecimal(randInt);
        
        Image i = new Image("/images/temporary_hotel_room_image_placeholder.jpg");
        RoomSearchResult r4 = new RoomSearchResult("512", bd, 
            new RoomCategory(
                "Basic Family Suite", 
                "This room features a 40-inch flat-screen TV with high-definition channels. An iPod docking station and coffee-making facilities are also provided.\n" + "\n" + "Room Facilities: Safe, Air conditioning, Iron, Heating, Carpeted, Interconnecting room(s) available, Hairdryer, Free toiletries, Toilet, Bathroom, Bathtub or shower, Telephone, Radio, Cable channels, Flat-screen TV, Refrigerator, Alarm clock",
                i,
                "2 King, 2 Twin"
            )
        );
        currentlySelectedRooms.add(r4);
        currentlySelectedRooms.add(r4);
        currentlySelectedRooms.add(r4);

    }
    
    /**
     * Adds this RoomSearchResult to the list of search results, if it is not
     * already in the list.
     * 
     * @param result The RoomSearchResult to add.
     */
    public void addResult(RoomSearchResult result) {
        if(!roomSearchResults.contains(result)) {
            roomSearchResults.add(result);
            sortResultsByPrice();
        }
    }
    
    /**
     * Removes this roomSearchResult item from the list of results.
     * 
     * @param result The RoomSearchResult to remove.
     */
    public void removeResult(RoomSearchResult result) {
        roomSearchResults.remove(result);
    }
    
    private void sortResultsByPrice() {
        roomSearchResults.sort(
            (RoomSearchResult r1, RoomSearchResult r2) -> 
                r1.getRoomPrice().compareTo(r2.getRoomPrice())
        );
    }
    
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/GuestHomeScreen.fxml"
        );
    }
    
    // TODO: Implement real program logic, Remove test code.
    public void onFindAvailableRoomsButtonClicked() {
        Random random = new Random();
        int randInt = random.nextInt(999);
        BigDecimal bd = new BigDecimal(randInt);
        
        Image i = new Image("/images/temporary_hotel_room_image_placeholder.jpg");
        RoomSearchResult r4 = new RoomSearchResult("512", bd, 
            new RoomCategory(
                "Basic Family Suite", 
                "This room features a 40-inch flat-screen TV with high-definition channels. An iPod docking station and coffee-making facilities are also provided.\n" + "\n" + "Room Facilities: Safe, Air conditioning, Iron, Heating, Carpeted, Interconnecting room(s) available, Hairdryer, Free toiletries, Toilet, Bathroom, Bathtub or shower, Telephone, Radio, Cable channels, Flat-screen TV, Refrigerator, Alarm clock",
                i,
                "2 King, 2 Twin"
            )
        );
        this.addResult(r4);
    }
    
    public void onNextButtonClicked() {
        // TODO replace with the packages screen first - this is temporary
        
        ScreenManager.getInstance().switchToScreen(
            "/fxml/PlaceBookingScreen.fxml"
        );
    }
    
    /**
     * Handles item deletion events for deletable list items.
     */
    @Override
    public void onItemDeleted(DeletableListItem item) {
        // TODO implement
    }
    
}
