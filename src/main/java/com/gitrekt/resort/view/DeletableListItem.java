package com.gitrekt.resort.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * TODO
 */
public class DeletableListItem extends ListCell<String> {

    private HBox root = new HBox();
    private Label label = new Label("");
    private Pane pane = new Pane();
    private Button button = new Button();
    
    ///private DeletableListItemDeletionListener listener;
    
    public DeletableListItem() {
        super();
        
        root.getChildren().addAll(button, pane, label);
        HBox.setHgrow(pane, Priority.ALWAYS);
        button.setOnAction(event -> onXClicked());
    }
    
    @Override
    protected void updateItem(String text, boolean empty) {
        super.updateItem(text, empty);
        
        if(empty || text == null) {
            setItem(null);
            setGraphic(null);
        } else {
            label.setText(text);
            setGraphic(root);
        }
    }
    
    private void onXClicked() {
        //listener.onItemDeleted();
        
        // Remove the item from the list
        getListView().getItems().remove((getItem()));
    }
    
}
