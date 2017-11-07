package com.gitrekt.resort.view;

import com.gitrekt.resort.controller.GuestFeedbackListItemController;
import com.gitrekt.resort.model.entities.GuestFeedback;
import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class GuestFeedbackListItem extends ListCell<GuestFeedback> {

    private final GuestFeedbackListItemController controller;
    
    private final FXMLLoader fxmlLoader;
    
    private final Node view;
    
    public GuestFeedbackListItem() {
        super();
        
        try {
            fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/GuestFeedbackListItem.fxml")
            );
            view = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.setViewReference(this);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to load FXML file.", e);
        }
    }
    
    @Override
    protected void updateItem(GuestFeedback feedback, boolean empty) {
        super.updateItem(feedback, empty);
        
        if(empty) {
            setGraphic(null);
        } else {
            controller.setData(feedback);
            setGraphic(view);
        }
    }
    
    public void onMarkedResolved() {
        setGraphic(null);
    }
    
}
