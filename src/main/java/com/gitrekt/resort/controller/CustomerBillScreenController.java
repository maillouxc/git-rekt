package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.BillItem;
import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.services.BillService;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML controller class for the customer bill view screen.
 */
public class CustomerBillScreenController implements Initializable {
    
    @FXML
    private Label billTotalText;
    
    @FXML
    private Label customerNameText;
    
    @FXML
    private Label bookingNumberText;
    
    @FXML
    private TableView<BillItem> billTable;
    
    @FXML
    private TableColumn<BillItem, String> itemNameColumn;
    
    @FXML
    private TableColumn<BillItem,Integer> qtyColumn;
    
    @FXML
    private TableColumn<BillItem, String> priceColumn;
    
    private Booking booking;
    
    private ObservableList<BillItem> billItems;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        billItems = FXCollections.observableArrayList();
        
        itemNameColumn.setCellValueFactory((param) -> {
            return new SimpleStringProperty(param.getValue().getName());
        });
        
        qtyColumn.setCellValueFactory((param) -> {
            return new SimpleIntegerProperty(param.getValue().getQuantity())
                    .asObject();
        });
        
        priceColumn.setCellValueFactory((param) -> {
            return new SimpleStringProperty(
                String.format("%.2f", param.getValue().getTotalPrice())
            );
        });
        
        billTable.setItems(billItems);
    }
    
    /**
     * Initializes the view bill screen with the data for the bill to display.
     * Once this method is called, the proper data is displayed on the screen.
     * 
     * It's not ideal to have to send the entire booking into this class to 
     * display just the bill, but we need several bits of extra information 
     * that the bill itself just doesn't contain (such as the guest name), and 
     * I don't have the time to implement a more robust solution, so for now, it
     * works.
     * 
     * @param booking The booking to display the bill for.
     */
    public void initializeData(Booking booking) {
        this.booking = booking;
        
        // TODO: Remove test code
        for(int i = 0; i < 30; i++) {
            double price = ThreadLocalRandom.current().nextDouble(0,500);
            int qty = ThreadLocalRandom.current().nextInt(10);
            booking.getBill().getCharges().add(new BillItem("Test bill item", price, qty));
        }
        
        billItems.addAll(booking.getBill().getCharges());
        initializeInfoLabels();
        prepareTableView();
    }
    
    @FXML
    private void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen(
            "/fxml/BookingDetailsScreen.fxml"
        );
    }
    
    /**
     * Prints the guest bill on a physical printer. The user is first prompted
     * with a print options dialog.
     * 
     * @throws IOException
     * @throws PrinterException 
     */
    @FXML
    private void onPrintBillButtonClicked() 
            throws IOException, PrinterException {
        BillService billService = new BillService();
        billService.printBillForBooking(booking);
    }
    
    /**
     * Initializes the information displayed in the various labels displayed on
     * this screen, such as the customer name label, the total price label, etc.
     */
    private void initializeInfoLabels() {
        bookingNumberText.setText(String.valueOf(booking.getId()));
        String lastName = booking.getGuest().getFirstName();
        String firstName = booking.getGuest().getLastName();
        customerNameText.setText(lastName + ", " + firstName);
        billTotalText.setText(
            String.format("$%.2f", booking.getBill().getTotal())
        );
    }
    
    private void prepareTableView() {
        // TODO
    }

}
