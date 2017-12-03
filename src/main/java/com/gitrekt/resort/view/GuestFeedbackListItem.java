package com.gitrekt.resort.view;

import com.gitrekt.resort.controller.FeedbackReportScreenController;
import com.gitrekt.resort.controller.GuestFeedbackListItemController;
import com.gitrekt.resort.model.entities.GuestFeedback;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class GuestFeedbackListItem extends ListCell<GuestFeedback> {

    private final GuestFeedbackListItemController controller;

    private final FXMLLoader fxmlLoader;

    private final Node view;

    private FeedbackReportScreenController parentController;

    private GuestFeedback data;

    public GuestFeedbackListItem(FeedbackReportScreenController parentController) {
        super();

        // This is a really quick and dirty way to do this.
        // It's really tightly coupled but I don't have time right now to
        // come up with a better design. It gets the job done.
        this.parentController = parentController;

        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GuestFeedbackListItem.fxml"));
            view = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.setViewReference(this);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load FXML file.", e);
        }
    }

    /**
     * Updates the contents of the list item that is displayed.
     *
     * @param feedback The feedback data to display in the list item.
     * @param empty Whether or not the list item is empty.
     */
    @Override
    protected void updateItem(GuestFeedback feedback, boolean empty) {
        super.updateItem(feedback, empty);

        this.data = feedback;

        if(empty) {
            setGraphic(null);
        } else {
            controller.setData(feedback);
            setGraphic(view);
        }
    }

    public void onMarkedResolved() {
        parentController.hideItem(this.data);
    }

}
