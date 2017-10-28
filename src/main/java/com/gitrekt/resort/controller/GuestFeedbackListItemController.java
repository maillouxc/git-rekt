package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.GuestFeedback;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Handles the functionality of FeedbackReportItemScreen.fxml
 */
public class GuestFeedbackListItemController implements Initializable {

    @FXML
    private Button markAsResolvedButton;

    @FXML
    private Label guestFeedbackLabel;

    @FXML
    private Label guestEmailLabel;

    @FXML
    private Label feedbackDateLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }
    
    @FXML
    void onMarkAsResolvedButtonClicked(ActionEvent event) {
        // TODO
    }
    
    public void setData(GuestFeedback data) {
        guestFeedbackLabel.setText("Not yet implemented");
        guestEmailLabel.setText("Not yet implemented");
        feedbackDateLabel.setText("Not yet implemented");
    }
    
}
