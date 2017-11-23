
package com.gitrekt.resort.model.services;

import com.gitrekt.resort.model.UsState;
import com.gitrekt.resort.model.entities.BillItem;
import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.entities.Guest;
import com.gitrekt.resort.model.entities.MailingAddress;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Responsible for generating a PDF representation of the guest bill.
 *
 * Currently very crude and incomplete, although the most basic functionalit is in place and
 * usable. This class still needs a lot of work.
 */
public class BillPdfGenerator {

    private static final String LINE_ITEM_FORMAT = "%-45s %9s %10s %13s";

    private final Booking booking;

    private final String lineItemHeader = getLineItemHeader();

    private final List<String> lineItems = new ArrayList<>();

    private final DecimalFormat currencyFormat = new DecimalFormat("$#.00");

    private final PDDocument document;

    private static final PDFont NORMAL_FONT = PDType1Font.COURIER;
    private static final PDFont BOLD = PDType1Font.COURIER_BOLD;

    private static final int NORMAL_FONT_SIZE = 10;

    public BillPdfGenerator(Booking booking) {
        this.booking = booking;
        generateLineItems();
        document = new PDDocument();
    }

    public PDDocument getBillAsPdf() throws IOException {
        // Add blank page to document
        PDPage firstPage = new PDPage();
        document.addPage(firstPage);
        PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);
        generateBillHeader(firstPage, contentStream);
        generateBillInfoText(firstPage, contentStream);
        contentStream.beginText();
        contentStream.newLineAtOffset(60f, 640f);

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

    private void generateBillHeader(PDPage firstPage, PDPageContentStream contentStream)
            throws IOException {
        // Add header text
        PDFont currentFont;
        int currentFontSize;
        String headerLine1 = "Git Rekt Resort";
        String headerLine2 = "Customer Bill";
        contentStream.setLeading(10);
        currentFont = BOLD;
        currentFontSize = 14;
        contentStream.setFont(currentFont, currentFontSize);
        contentStream.beginText();
        float offsetX = getCenteredTextXPos(firstPage, headerLine1, currentFont, currentFontSize);
        contentStream.newLineAtOffset(offsetX, 750f);
        contentStream.showText(headerLine1);
        currentFont = PDType1Font.COURIER_BOLD;
        currentFontSize = 12;
        contentStream.setFont(currentFont, currentFontSize);
        float offsetX2 = getCenteredTextXPos(firstPage, headerLine2, currentFont, currentFontSize);
        contentStream.newLineAtOffset(-offsetX + offsetX2, -5f);
        contentStream.newLine();
        contentStream.showText(headerLine2);
        contentStream.endText();
    }

    private void generateBillInfoText(PDPage firstPage, PDPageContentStream contentStream)
            throws IOException {
        Guest guest = booking.getGuest();
        String guestName = guest.getLastName() + ", " + guest.getFirstName();
        MailingAddress address = guest.getMailingAddress();
        String country = address.getCountry();
        String city = address.getCity();
        UsState state = address.getState();
        String postalCode = address.getPostalCode();
        String addressLine1 = address.getAddressLine1();
        String addressLine2 = address.getAddressLine2();

        contentStream.beginText();

        // Print customer name line
        contentStream.newLineAtOffset(80f, 710f);
        contentStream.setLeading(10);
        contentStream.setFont(BOLD, NORMAL_FONT_SIZE);
        contentStream.showText("Customer name: ");
        contentStream.setFont(NORMAL_FONT, NORMAL_FONT_SIZE);
        contentStream.showText(guestName);

        // Print customer mailing address lines
        contentStream.newLine();
        contentStream.setFont(BOLD, NORMAL_FONT_SIZE);
        contentStream.showText("Billing Address: ");
        contentStream.setFont(NORMAL_FONT, NORMAL_FONT_SIZE);
        contentStream.newLine();
        contentStream.showText(addressLine1);
        if(!addressLine2.isEmpty()) {
            contentStream.showText(",");
            contentStream.showText(addressLine2);
        }
        contentStream.newLine();
        contentStream.showText(city);
        if(state != null) {
            contentStream.showText(", " + state.getAbbreviation());
        }
        contentStream.showText(" " + postalCode);
        contentStream.showText(" - ");
        contentStream.showText(country);

        // Print booking number line
        contentStream.newLine();
        contentStream.setFont(BOLD, NORMAL_FONT_SIZE);
        contentStream.showText("Booking Number: ");
        contentStream.setFont(NORMAL_FONT, NORMAL_FONT_SIZE);
        contentStream.showText(booking.getId().toString());

        // Print bill printed info line
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMMM d, u '-' H:m:s zz");
        String formattedDate = formatter.format(ZonedDateTime.now(ZoneId.systemDefault()));
        contentStream.newLine();
        contentStream.setFont(BOLD, NORMAL_FONT_SIZE);
        contentStream.showText("Bill Printed On: ");
        contentStream.setFont(NORMAL_FONT, NORMAL_FONT_SIZE);
        contentStream.showText(formattedDate);

        contentStream.endText();

    }

    private float getCenteredTextXPos(PDPage page, String text, PDFont font, int fontSize)
            throws IOException {
        float textWidth = getStringWidth(text, font, fontSize);
        PDRectangle pageSize = page.getMediaBox();
        float pageCenterX = pageSize.getWidth() / 2F;
        float textX = pageCenterX - textWidth/2F;
        return textX;
    }

    private String getLineItemHeader() {
        return String.format(
            LINE_ITEM_FORMAT, "Item", "Quantity", "Price", "Total"
        );
    }

    private void generateLineItems() {
        for(BillItem billItem : booking.getBill().getCharges()) {
            String lineItem = convertBillItemToLineItem(billItem);
            lineItems.add(lineItem);
        }
    }

    private String getBillTotalLine() {
        double billTotal = booking.getBill().getTotal();
        return String.format(
            "%80s", ("Total: " + currencyFormat.format(billTotal))
        );
    }

    /**
     * Gathers the fields from the guest bill items and converts them into their
     * formatted string representation to be displayed as a single line-item
     * on the printed guest bill.
     *
     * @param item The bill item to format.
     * @return The formatted bill item.
     */
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

    private float getStringWidth(String text, PDFont font, int fontSize) throws IOException {
        return font.getStringWidth(text) * fontSize / 1000F;
    }

}
