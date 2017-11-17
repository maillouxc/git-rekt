package com.gitrekt.resort.view;

import com.gitrekt.resort.controller.DeletableListItemDeletionListener;
import com.gitrekt.resort.model.RoomSearchResult;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * TODO: Document
 * 
 * Everything about this class is a giant mess. It was only really written to 
 * test the UI. It needs to be redesigned from the ground up.
 * 
 * Please, please, do not use this class in the final program.
 * 
 * We need to determine if the class is even needed, and if it is, how to make
 * it more generalized.
 */
public class DeletableListItem extends ListCell<RoomSearchResult> {

    private HBox root = new HBox();
    private Label listItemText = new Label("");
    private Button deleteButton = new Button();
    private ImageView deleteButtonIcon;
    
    //private DeletableListItemController controller;
    
    private DeletableListItemDeletionListener listener;
    
    public DeletableListItem(DeletableListItemDeletionListener listener) {
        super();
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            root = fxmlLoader.load(
                    getClass().getResource("/fxml/DeletableListItem.fxml")
            );
           //fxmlLoader.setController(controller);
        } catch (IOException ex) {
            // TODO
        }
        
        this.listener = listener;
        deleteButton.setOnAction(event -> onXClicked());
    }
    
    @Override
    protected void updateItem(RoomSearchResult roomData, boolean empty) {
        super.updateItem(roomData, empty);
        
        if(empty || roomData == null) {
            setItem(null);
            setGraphic(null);
        } else {
            listItemText.setText(roomData.getRoomCategory().getName());
            setGraphic(root);
        }
    }
    
    private void onXClicked() {
        listener.onItemDeleted(this);
        
        // Remove the item from the list
        getListView().getItems().remove((getItem()));
    }
    
}
