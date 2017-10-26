package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.GuestFeedback;
import com.gitrekt.resort.view.GuestFeedbackListItem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Handles the functionality of FeesbackReportScreen.fxml
 */
public class FeedbackReportScreenController implements Initializable {
    
    @FXML
    private Button backButton;
    
    @FXML
    private ListView<GuestFeedback> guestFeedbackListView;
    
    private ObservableList<GuestFeedback> guestFeedbackList;
    
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
                
        testCodePleaseDeleteLater();
        
        // TODO: initialize arraylist from db
    }
    
    public void onBackButtonClicked() throws IOException {
        Stage mainStage = (Stage) backButton.getScene().getWindow();
        Parent staffHomeScreenRoot = FXMLLoader.load(
            getClass().getResource("/fxml/ReportsHomeScreen.fxml")
        );
        Scene staffHomeScreen = new Scene(staffHomeScreenRoot);
        mainStage.centerOnScreen();
        mainStage.setScene(staffHomeScreen);
    }
    
    private void testCodePleaseDeleteLater() {
        guestFeedbackList.add(new GuestFeedback());
        guestFeedbackList.add(new GuestFeedback());
        guestFeedbackList.add(new GuestFeedback());
        guestFeedbackList.add(new GuestFeedback());
    }
    
}
