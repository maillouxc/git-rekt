package com.gitrekt.resort.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML controller class for the customer bill view screen.
 */
public class CustomerBillScreenController implements Initializable {

    @FXML
    private Button backButton;
    
    @FXML
    private Label billTotalText;
    
    @FXML
    private Label customerNameText;
    
    @FXML
    private Label billingPeriodText;
    
    @FXML
    private Label bookingNumberText;
    
    @FXML
    private TableView billTable;
    
    @FXML
    private TableColumn itemNameColumn;
    
    @FXML
    private TableColumn qtyColumn;
    
    @FXML
    private TableColumn priceColumn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    protected void onBackButtonClicked() {
        // TODO
    }
    
    @FXML
    protected void onPrintBillButtonClicked() {
        // TODO
    }
    
}
