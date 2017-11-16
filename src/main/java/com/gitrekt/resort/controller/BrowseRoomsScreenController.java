package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.RoomSearchResult;
import com.gitrekt.resort.view.BrowseRoomsListItem;
import com.gitrekt.resort.view.DeletableListItem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class for the browse rooms screen.
 */
public class BrowseRoomsScreenController implements Initializable,
        DeletableListItemDeletionListener {
    
    @FXML
    private ListView<RoomSearchResult> roomsListView;
    
    @FXML
    private ListView<RoomSearchResult> selectedRoomsListView;
    
    private final ObservableList<RoomSearchResult> roomSearchResults;
    
    private final ObservableList<RoomSearchResult> selectedRooms;
    
    public BrowseRoomsScreenController() {
        roomSearchResults = FXCollections.observableArrayList();
        selectedRooms = FXCollections.observableArrayList();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roomsListView.setItems(roomSearchResults);
        roomsListView.setCellFactory(param -> new BrowseRoomsListItem() {
            {
                // Don't touch. Magic.
                prefWidthProperty().bind(roomsListView.widthProperty());
            }
        });
        selectedRoomsListView.setCellFactory(
            param -> new DeletableListItem(this)
        );
        selectedRoomsListView.setItems(selectedRooms);
    }
    
    private void sortResultsByPrice() {
        roomSearchResults.sort(
            (RoomSearchResult r1, RoomSearchResult r2) -> 
                r1.getRoomPrice().compareTo(r2.getRoomPrice())
        );
    }
    
    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/GuestHomeScreen.fxml"
        );
    }
    
    @FXML
    private void onFindAvailableRoomsButtonClicked() {
        // TODO
    }
    
    @FXML
    private void onNextButtonClicked() {
        // TODO replace with the packages screen first - this is temporary
        
        if(selectedRooms.size() > 0) {
            ScreenManager.getInstance().switchToScreen(
                "/fxml/PlaceBookingScreen.fxml"
            );
        }
        
        
    }
    
    // TODO: Determine if really necessary
    /**
     * Handles item deletion events for deletable list items.
     */
    @Override
    public void onItemDeleted(DeletableListItem item) {
        // TODO implement
    }
    
}
