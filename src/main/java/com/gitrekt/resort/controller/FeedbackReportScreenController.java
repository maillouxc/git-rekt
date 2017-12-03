package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.GuestFeedback;
import com.gitrekt.resort.model.services.GuestFeedbackService;
import com.gitrekt.resort.view.GuestFeedbackListItem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Handles the functionality of feedback report screen.
 */
public class FeedbackReportScreenController implements Initializable {

    @FXML
    private ListView<GuestFeedback> guestFeedbackListView;

    private ObservableList<GuestFeedback> guestFeedbackList;

    /**
     * Initializes the FXML controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        guestFeedbackList = FXCollections.observableArrayList();
        guestFeedbackListView.setItems(guestFeedbackList);
        guestFeedbackListView.setCellFactory(param -> new GuestFeedbackListItem(this));
        guestFeedbackListView.setPlaceholder(new Label("No unresolved feedback"));

        loadFeedback();
    }

    /**
     * @param item The data item to hide from the screen.
     */
    public void hideItem(GuestFeedback item) {
        guestFeedbackList.remove(item);
    }

    /**
     * Returns to the reports home screen.
     *
     * @throws IOException
     */
    @FXML
    private void onBackButtonClicked() throws IOException {
        ScreenManager.getInstance().switchToScreen("/fxml/ReportsHomeScreen.fxml");
    }

    /**
     * Loads and displays updated guest feedback from the database.
     */
    @FXML
    private void onRefreshButtonClicked() {
        loadFeedback();
    }

    /**
     * Loads updated guest feedback from the database.
     */
    private void loadFeedback() {
        GuestFeedbackService guestFeedbackService = new GuestFeedbackService();
        guestFeedbackList.clear();
        guestFeedbackList.addAll(guestFeedbackService.getUnresolvedGuestFeedback());
    }

}
