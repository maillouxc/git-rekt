package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.BillItem;
import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.services.BillService;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
     * Initializes the FXML controller class.
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
     *
     * Once this method is called, the proper data is displayed on the screen.
     *
     * @param booking The booking to display the bill for.
     */
    public void initializeData(Booking booking) {
        this.booking = booking;
        billItems.addAll(booking.getBill().getCharges());
        initializeInfoLabels();
    }

    /**
     * Returns to the previous screen, and passes a reference to the relevant booking data.
     *
     * I don't like that this class needs to know about it's parent screen, but this is the
     * simplest and fastest way to implement it.
     */
    @FXML
    private void onBackButtonClicked() {
        Object temp = ScreenManager.getInstance().switchToScreen("/fxml/BookingDetailsScreen.fxml");
        BookingDetailsScreenController parentController = (BookingDetailsScreenController) temp;
        parentController.intializeBookingData(this.booking);
    }

    /**
     * Prints the guest bill on a printer.
     *
     * The user is first prompted with a print options dialog.
     *
     * @throws IOException
     * @throws PrinterException
     */
    @FXML
    private void onPrintBillButtonClicked() throws IOException, PrinterException {
        BillService billService = new BillService();
        billService.printBillForBooking(booking);
    }

    /**
     * Initializes the information displayed in the various labels on this screen, such as the
     * customer name label, the total price label, etc.
     */
    private void initializeInfoLabels() {
        bookingNumberText.setText(String.valueOf(booking.getId()));
        String lastName = booking.getGuest().getFirstName();
        String firstName = booking.getGuest().getLastName();
        customerNameText.setText(lastName + ", " + firstName);
        billTotalText.setText(String.format("$%.2f", booking.getBill().getTotal()));
    }

}
