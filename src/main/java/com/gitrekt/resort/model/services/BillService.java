package com.gitrekt.resort.model.services;

import com.gitrekt.resort.model.entities.BillItem;
import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.entities.Package;
import com.gitrekt.resort.model.entities.Room;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.print.PrintService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 * This class is responsible for handling all business logic related to bills.
 */
public class BillService {

    /**
     * Prints the bill associated with the provided booking on a printer.
     *
     * @param booking The booking to print the bill for.
     *
     * @throws IOException
     * @throws PrinterException
     */
    public void printBillForBooking(Booking booking)
            throws IOException, PrinterException {

        BillPdfGenerator pdfGenerator = new BillPdfGenerator(booking.getBill());
        try (PDDocument pdf = pdfGenerator.getBillAsPdf()) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintService(choosePrinter());
            job.setPageable(new PDFPageable(pdf));
            job.print();
        }
    }

    /**
     * Generates the bill for the provided booking, and adds it to the booking object.
     *
     * @param booking The booking to generate the bill for.
     */
    public void generateBillForBooking(Booking booking) {
        List<BillItem> billItems = new ArrayList<>();

        // Account for all booked rooms
        RoomService roomService = new RoomService();
        Date checkInDate = booking.getCheckInDate();
        Date checkOutDate = booking.getCheckOutDate();
        LocalDate checkin = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkout = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int numNights = (int) DAYS.between(checkin, checkout);

        for(Room r : booking.getBookedRooms()) {
            double pricePerNight = roomService.getCurrentRoomPrice(r.getRoomCategory());
            String categoryName = r.getRoomCategory().getName();
            String itemName = "Room " + r.getRoomNumber() + " - " + categoryName + " suite";
            BillItem item = new BillItem(itemName, pricePerNight, numNights);
            billItems.add(item);
        }

        // Account for all booked packages
        Map<Package, Integer> packageQtys = new HashMap<>();
        for(Package p : booking.getPackages()) {
            if(packageQtys.containsKey(p)) {
                int qty = packageQtys.get(p);
                qty++;
                packageQtys.replace(p, qty);
            } else {
                packageQtys.put(p, 1);
            }
        }
        for(Map.Entry<Package, Integer> entry : packageQtys.entrySet()) {
            String itemName = "Package - " + entry.getKey().getName();
            double costPerPerson = entry.getKey().getPricePerPerson();
            int qty = entry.getValue();
            BillItem packageBillItem = new BillItem(itemName, costPerPerson, qty);
            billItems.add(packageBillItem);
        }

        // TODO: Price adjust based on resort occupation level.

        for(BillItem item : billItems) {
            booking.getBill().addCharge(item);
        }
    }

    /**
     * Prompts the user to choose a printer to print from, using a standard dialog box.
     *
     * The user is also able to selected from other properties such as the number of copies to
     * print, collation, etc., independently of our software.
     *
     * @return The PrintService (a.k.a the printer) selected by the user.
     */
    private static PrintService choosePrinter() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        if(printJob.printDialog()) {
            return printJob.getPrintService();
        }
        else {
            return null;
        }
    }

}
