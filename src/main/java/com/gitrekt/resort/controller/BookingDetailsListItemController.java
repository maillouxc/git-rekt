package com.gitrekt.resort.controller;


import com.gitrekt.resort.model.BookingDetailEntry;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * This class controls the functionality of BookingDetialsListItem.fxml
 *
 */
public class BookingDetailsListItemController implements Initializable {
    
    
    @FXML
    private Node root;
    
    @FXML
    private Label bookingNumber;
    
    @FXML
    private Label roomNumber;
    
    @FXML
    private Label bookingStart;
    
    @FXML
    private Label bookingEnd;
    
    @FXML
    private Label roomDescription;
    
    @FXML
    private ImageView roomImage;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button billButton;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void setData(BookingDetailEntry bookingData) {
        roomImage.setImage(bookingData.getRoom().getImage());
  
        roomDescription.setText(
            bookingData.getRoom().getDescription()
        );
        bookingNumber.setText(bookingData.getbookingNumber());
        
        roomNumber.setText(bookingData.getRoomNumber());
        
        bookingStart.setText(bookingData.getStartDate());
        
        bookingEnd.setText(bookingData.getEndDate());
    }
    
    public Node getView() {
        return root;
    }
    
    @FXML
    public void onCancelButtonClicked(){
    //TODO
    }
    
    @FXML
    public void onbillButtionClicked(){
    //TODO
    }
}
