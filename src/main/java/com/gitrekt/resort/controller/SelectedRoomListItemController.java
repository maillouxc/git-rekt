package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.RoomSearchResult;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML controller class for selected room list items.
 */
public class SelectedRoomListItemController implements Initializable {

    @FXML
    private Label listItemText;

    private BrowseRoomsScreenController parentController;

    private RoomSearchResult roomData;

    /**
     * Called by JavaFX.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Intentionally blank.
    }

    /**
     * Sets a reference to the FXML controller class for the screen that contains the list that
     * this list item is in.
     *
     * @param parentController The controller class for the screen that controls this list.
     */
    public void setParentController(BrowseRoomsScreenController parentController) {
        this.parentController = parentController;
    }

    /**
     * Sets the room data for this list item to store.
     *
     * This data also determines the label's text.
     *
     * @param roomData The RoomSearchResult data to store in the item.
     */
    public void setData(RoomSearchResult roomData) {
        this.roomData = roomData;
        this.listItemText.setText(roomData.getRoomCategory().getName());
    }

    /**
     * Notifies the parent controller that this item should be removed from the parent list.
     *
     * Also, increments the count for the number of rooms of this category available.
     */
    @FXML
    private void onDeleteButtonClicked() {
        roomData.setNumAvailable(roomData.getNumAvailable() + 1);
        parentController.onRoomUnselected(roomData);
    }

}
