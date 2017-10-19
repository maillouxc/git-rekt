package com.gitrekt.resort.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class for the guest home screen.
 */
public class GuestHomeScreenController implements Initializable {

    @FXML
    private Button browseRoomsButton;
    
    @FXML
    private Button leaveFeedbackButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Allows this button to activate when user hits enter
         leaveFeedbackButton.setOnKeyPressed(new EventHandler<KeyEvent>(){
           @Override
           public void handle(KeyEvent event) {
               switch(event.getCode()){
                   case ENTER:
                        try{
               onLeaveFeedbackButtonClicked();
           }
       catch(IOException i){
           
       }
               }
               
           }
       });
         //Allows this button to activate when user hits enter
         browseRoomsButton.setOnKeyPressed(new EventHandler<KeyEvent>(){
           @Override
           public void handle(KeyEvent event) {
               switch(event.getCode()){
                   case ENTER:
                        try{
               onBrowseRoomsButtonClicked();
           }
       catch(IOException i){
           
       }
               }
               
           }
       });
    }
    
    /**
     * Displays the browse rooms screen.
     * 
     * @throws IOException 
     */
    public void onBrowseRoomsButtonClicked() throws IOException {
        Stage mainStage = (Stage) browseRoomsButton.getScene().getWindow();
        Parent browseRoomsScreenRoot = FXMLLoader.load(
                getClass().getResource("/fxml/BrowseRoomsScreen.fxml")
        );
        Scene browseRoomsScreen = new Scene(browseRoomsScreenRoot);
        mainStage.setScene(browseRoomsScreen);
        mainStage.centerOnScreen();
    }
    
    /**
     * Displays the leave feedback screen.
     * 
     * @throws IOException 
     */
     public void onLeaveFeedbackButtonClicked() throws IOException {
        Stage mainStage = (Stage) leaveFeedbackButton.getScene().getWindow();
        Parent leaveFeedbackScreenRoot = FXMLLoader.load(
                getClass().getResource("/fxml/LeaveFeedbackScreen.fxml")
        );
        Scene leaveFeedbackScreen = new Scene(leaveFeedbackScreenRoot);
        mainStage.setScene(leaveFeedbackScreen);
        mainStage.centerOnScreen();
    }
     
}
