package com.gitrekt.resort.view;

import com.gitrekt.resort.controller.BrowseRoomsListItemController;
import com.gitrekt.resort.model.RoomSearchResult;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 * TODO
 */
public class AvailableRoomListItem extends ListCell<RoomSearchResult> {
    
    private final BrowseRoomsListItemController controller 
            = new BrowseRoomsListItemController();
    
    private final Node view = controller.getView();
    
    @Override
    protected void updateItem(RoomSearchResult roomData, boolean empty) {
        super.updateItem(roomData, empty);
        
        if(empty) {
            setGraphic(null);
        } else {
            controller.setData(roomData);
            setGraphic(view);
        }
    }
    
}
