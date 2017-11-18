package com.gitrekt.resort.view;

import com.gitrekt.resort.controller.BrowseRoomsScreenController;
import com.gitrekt.resort.controller.SelectedRoomListItemController;
import com.gitrekt.resort.model.RoomSearchResult;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 * A list item meant for the rooms that are currently selected but not yet booked.
 *
 * This class could probably stand to be generalized into a more generic DeletableListItem class,
 * but it's not worth the time right now.
 */
public class SelectedRoomListItem extends ListCell<RoomSearchResult> {

    private SelectedRoomListItemController controller;

    private HBox root = new HBox();

    private BrowseRoomsScreenController parentController;

    public SelectedRoomListItem(BrowseRoomsScreenController parentController) {
        super();

        this.parentController = parentController;
        FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource("/fxml/SelectedRoomListItem.fxml")
        );
        try {
            root = fxmlLoader.load();
            controller = (SelectedRoomListItemController) fxmlLoader.getController();
            controller.setParentController(parentController);
        } catch (IOException e) {
            // TODO
        }
    }

    @Override
    protected void updateItem(RoomSearchResult roomData, boolean empty) {
        super.updateItem(roomData, empty);

        if(empty || roomData == null) {
            setItem(null);
            setGraphic(null);
        } else {
            controller.setData(roomData);
            setGraphic(root);
        }
    }

}
