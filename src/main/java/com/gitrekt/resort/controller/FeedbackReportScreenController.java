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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Handles the functionality of FeedbackReportScreen.fxml
 */
public class FeedbackReportScreenController 
        implements Initializable, Refreshable {
    
    @FXML
    private Button backButton;
    
    @FXML
    private ListView<GuestFeedback> guestFeedbackListView;
    
    private ObservableList<GuestFeedback> guestFeedbackList;
    
    private GuestFeedbackService guestFeedbackService;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        guestFeedbackList = FXCollections.observableArrayList();
        guestFeedbackListView.setItems(guestFeedbackList);
        guestFeedbackListView.setCellFactory( 
            param -> new GuestFeedbackListItem()
        );
        
        this.guestFeedbackService = new GuestFeedbackService();
        
        // Load the unresolved guest feedback from the database
        guestFeedbackList.addAll(
            guestFeedbackService.getUnresolvedGuestFeedback()
        );
    }
    
    public void onBackButtonClicked() throws IOException {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/ReportsHomeScreen.fxml"
        );
    }
    
    /**
     * Polls the database for feedback items again. 
     * 
     * Not the best solution, since we shouldn't necessarily have to hit the
     * database every single time we resolve a feedback item, but for now, we
     * should be good enough by using this.
     */
    @Override
    public void refresh() {
        guestFeedbackList.clear();
        guestFeedbackList.addAll(
            guestFeedbackService.getUnresolvedGuestFeedback()
        );
    }
}
