package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.RoomSearchResult;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * This class controls the list items in the browse rooms screen room search
 * results list. Due to the weird way that lists are updated in JavaFX,
 * this class actually loads the FXML and then assigns itself as the controller
 * for the screen; things are typically done the other way around in JavaFX.
 */
public class BrowseRoomsListItemController implements Initializable {
    
    @FXML
    private Node root;
    
    @FXML
    private Label roomCategoryLabel;
    
    @FXML
    private Label bedsInfoLabel;
    
    @FXML
    private Label roomPriceLabel;
    
    @FXML
    private ImageView roomThumbnailView;
    
    @FXML
    private Label roomDescriptionLabel;
    
    public BrowseRoomsListItemController() {
        // TODO
    }
    
    /**
     * Handles any needed initialization logic for the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setData(RoomSearchResult roomData) {
        roomCategoryLabel.setText(roomData.getRoomCategory().getName());
        roomThumbnailView.setImage(roomData.getRoomCategory().getImage());
        bedsInfoLabel.setText(roomData.getRoomCategory().getBedsInfo());
    
        // TODO: We should handle currency more flexibly at some point.
        String priceString = "$" + roomData.getRoomPrice() + " / night";
                    
        roomPriceLabel.setText(priceString);
        roomDescriptionLabel.setText(
            roomData.getRoomCategory().getDescription()
        );
    }
    
    public Node getView() {
        return root;
    }
    
    @FXML
    protected void onAddToBookingButtonClicked() {
        // TODO implement program logic
    }
    
    @FXML
    protected void onMoreInfoButtonClicked() {
        // TODO
    }
    
    @FXML
    protected void onRoomThumbnailClicked() {
        // TODO
    }
    
}
