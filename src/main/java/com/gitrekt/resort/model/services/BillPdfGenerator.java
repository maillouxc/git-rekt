
package com.gitrekt.resort.model.services;

import com.gitrekt.resort.model.entities.Bill;
import com.gitrekt.resort.model.entities.BillItem;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class BillPdfGenerator {
    
    private static final String LINE_ITEM_FORMAT = "%-40s %11s %13s %13s";
    
    private final Bill bill;
    
    private final String lineItemHeader = getLineItemHeader();
    
    private final List<String> lineItems = new ArrayList<>();
    
    private final DecimalFormat currencyFormat = new DecimalFormat("$#.00");
    
    private final PDDocument document;
    
    private static final PDFont NORMAL_FONT = PDType1Font.COURIER;
    
    private static final int NORMAL_FONT_SIZE = 10;
    
    private static final String HEADER_LINE_1 = "Git Rekt Resort";
    private static final String HEADER_LINE_2 = "Customer Bill";
    
    public BillPdfGenerator(Bill bill) {
        this.bill = bill;
        generateLineItems();
        document = new PDDocument();
    }
    
    public PDDocument getBillAsPdf() throws IOException {
        // Add blank page to document
        PDPage page = new PDPage();
        document.addPage(page);
        
        // Create a stream to write to the page
        PDPageContentStream contentStream = 
                new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.newLineAtOffset(60, 700);
        
        // Add header text
        contentStream.setFont(NORMAL_FONT, NORMAL_FONT_SIZE);
        contentStream.setLeading(10);
        contentStream.showText(HEADER_LINE_1);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText(HEADER_LINE_2);
        contentStream.newLine();
        
        // Add the column line item header line
        contentStream.setFont(PDType1Font.COURIER_BOLD, NORMAL_FONT_SIZE);
        contentStream.newLine();
        contentStream.showText(lineItemHeader);
        
        // Add bill line items   
        contentStream.setFont(NORMAL_FONT, NORMAL_FONT_SIZE);     
        for(String lineItem : lineItems) {
            contentStream.newLine();
            contentStream.showText(lineItem);
        }
        
        // Display the bill total at the end
        contentStream.newLine();
        String billTotalLine = getBillTotalLine();
        contentStream.setFont(PDType1Font.COURIER_BOLD, NORMAL_FONT_SIZE);
        contentStream.newLine();
        contentStream.showText(billTotalLine);

        contentStream.endText();
        contentStream.close();
        
        return document;
    }
    
    private String getLineItemHeader() {
        return String.format(
            LINE_ITEM_FORMAT, "Item", "Quantity", "Price", "Total"
        );
    }
    
    private void generateLineItems() {
        for(BillItem billItem : bill.getCharges()) {
            String lineItem = convertBillItemToLineItem(billItem);
            lineItems.add(lineItem);
        }
    }
    
    private String getBillTotalLine() {
        double billTotal = bill.getTotal();
        return String.format(
            "%80s", ("Total: " + currencyFormat.format(billTotal))
        );
    }
    
    private String convertBillItemToLineItem(BillItem item) {
        String lineItem;
        double totalPrice = item.getPrice() * item.getQuantity();       
        lineItem = String.format(
            LINE_ITEM_FORMAT, 
            item.getName(),
            item.getQuantity(),
            currencyFormat.format(item.getPrice()),
            currencyFormat.format(totalPrice)
        );
        return lineItem;
    }
}
