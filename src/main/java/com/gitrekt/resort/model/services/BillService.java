package com.gitrekt.resort.model.services;

import com.gitrekt.resort.model.entities.Booking;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import javax.print.PrintService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 * This class is responsible for handling all business logic related to bills.
 * 
 * At the moment, the only real business logic that belongs here is printing
 * related, though this may change in the future as features are implemented.
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
     * Prompts the user to choose a printer to print from, using a standard
     * dialog box.
     * 
     * The user is also able to selected from other properties such as the
     * number of copies to print, collation, etc., independently of our 
     * software.
     * 
     * @return The PrintService (a.k.a the printer) selected by the user. 
     */
    public static PrintService choosePrinter() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        if(printJob.printDialog()) {
            return printJob.getPrintService();          
        }
        else {
            return null;
        }
    }
    
}
