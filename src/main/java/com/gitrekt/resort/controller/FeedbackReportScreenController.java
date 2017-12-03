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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Handles the functionality of FeedbackReportScreen.fxml
 */
public class FeedbackReportScreenController 
        implements Initializable {
    
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
            param -> new GuestFeedbackListItem(this)
        );
        
        guestFeedbackListView.setPlaceholder(
            new Label("No unresolved feedback")
        );
        
        this.guestFeedbackService = new GuestFeedbackService();
        loadFeedback();
    }
    
    /**
     * @param item The data item to hide from the screen.
     */
    public void hideItem(GuestFeedback item) {
        guestFeedbackList.remove(item);
    }
    
    @FXML
    private void onBackButtonClicked() throws IOException {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/ReportsHomeScreen.fxml"
        );
    }
    
    @FXML
    private void onRefreshButtonClicked() {
        loadFeedback();
    }
    
    private void loadFeedback() {
        // Clear any existing items from the list
        guestFeedbackList.clear();
        
        // Retrieve new items
        guestFeedbackList.addAll(
            guestFeedbackService.getUnresolvedGuestFeedback()
        );
    }
    
}
