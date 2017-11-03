package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Bill;
import com.gitrekt.resort.model.services.BillService;
import java.awt.print.PrinterException;
import java.io.IOException;
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
    
    // TODO: Fix rawtype
    @FXML
    private TableView billTable;
    
    // TODO: Fix rawtype
    @FXML
    private TableColumn itemNameColumn;
    
    // TODO: Fix rawtype
    @FXML
    private TableColumn qtyColumn;
    
    // TODO: Fix rawtype
    @FXML
    private TableColumn priceColumn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/BookingDetailsScreen.fxml"
        );
    }
    
    public void onPrintBillButtonClicked() 
            throws IOException, PrinterException {
        // TODO: Replace with an actual bill, not an empty one.
        BillService billService = new BillService();
        billService.printBill(new Bill());
    }
    
}
