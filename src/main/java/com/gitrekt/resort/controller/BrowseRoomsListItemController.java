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
 * TODO: Document.
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
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/BrowseRoomsListItem.fxml")
        );
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setData(RoomSearchResult roomData) {
        // TODO: Remove
        System.out.println("here");
        
        //roomCategoryLabel.setText(roomData.getRoomCategory().getName());
        roomThumbnailView.setImage(roomData.getRoomCategory().getImage());
    
        // TODO: We should handle currency more flexibly at some point.
        String priceString = "$" + roomData.getRoomPrice() + " / night";
                    
        roomPriceLabel.setText(priceString);
        roomDescriptionLabel.setText(
            roomData.getRoomCategory().getDescription()
        );
        bedsInfoLabel.setText(roomData.getRoomCategory().getBedsInfo());
    }
    
    public Node getView() {
        return root;
    }
    
    @FXML
    protected void onAddToBookingButtonClicked() {
        // TODO
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
