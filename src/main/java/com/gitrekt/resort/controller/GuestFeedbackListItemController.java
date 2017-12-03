package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.GuestFeedback;
import com.gitrekt.resort.model.services.GuestFeedbackService;
import com.gitrekt.resort.view.GuestFeedbackListItem;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * Handles the functionality of the list items displayed on the list of guest feedback.
 */
public class GuestFeedbackListItemController implements Initializable {

    @FXML
    private Label guestFeedbackLabel;

    @FXML
    private Label guestEmailLabel;

    @FXML
    private Label feedbackDateLabel;

    private GuestFeedback data;

    private GuestFeedbackListItem view;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Intentionally blank.
    }

    /**
     * Marks the feedback as resolved in the database.
     *
     * @param event The button click event triggering the method.
     */
    @FXML
    void onMarkAsResolvedButtonClicked(ActionEvent event) {
        GuestFeedbackService guestFeedbackService = new GuestFeedbackService();
        data.setIsResolved(true);
        guestFeedbackService.updateGuestFeedback(data);
        view.onMarkedResolved();
    }

    /**
     * @param data The GuestFeedback object to display on the screen.
     */
    public void setData(GuestFeedback data) {
        this.data = data;

        guestFeedbackLabel.setText(data.getFeedback());
        guestEmailLabel.setText(data.getGuestEmail());

        // Handle displaying the creation date, which is actually little tricky
        String dateFormat = "MM/dd/yyyy 'at' h:mm a";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        String formattedDate = dateFormatter.format(data.getCreationDate());
        feedbackDateLabel.setText(formattedDate);
    }

    /**
     * This is a quick and dirty way to communicate between the parent and child controller objects
     * here. This should really, really be improved to use a better message passing mechanism at a
     * later time.
     *
     * @param view The view object representing this list item.
     */
    public void setViewReference(GuestFeedbackListItem view) {
        this.view = view;
    }

}
