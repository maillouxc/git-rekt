package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.RoomSearchResult;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 * TODO: Document.
 */
public class BrowseRoomsListItemController extends ListCell<RoomSearchResult>{
    
    @FXML
    private Label roomCategoryLabel;
    
    @FXML
    private Label roomNumberLabel;
    
    @FXML
    private Label roomPriceLabel;
    
    @FXML
    private ImageView roomCategoryPicture;
    
    private FXMLLoader fxmlLoader;
    
    @Override
    protected void updateItem(RoomSearchResult roomData, boolean empty) {
        super.updateItem(roomData, empty);
        
        if(empty || roomData == null) {
            // TODO: Handle
        } else {
            if(fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(
                    getClass().getResource("/fxml/BrowseRoomsListItem.fxml")
                );
                
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    // TODO: Handle
                }
            }
            
            // TODO: Updata cell data.
            
            roomCategoryLabel.setText(roomData.getRoomCategory().getName());
            roomCategoryPicture.setImage(roomData.getRoomCategory().getImage());
            roomNumberLabel.setText(roomData.getRoomNumber());
            roomPriceLabel.setText(roomData.getRoomPrice());
        }
    }
    
}
