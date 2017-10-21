
package com.gitrekt.resort.view;

import com.gitrekt.resort.controller.BookingDetailsListItemController;
import com.gitrekt.resort.model.BookingDetailEntry;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

/**
 *This class creates the list of screens for BookingDetailsScreenController
 */
public class BookingDetailListItem extends ListCell<BookingDetailEntry> {
    
    private final BookingDetailsListItemController controller; 
           // = new BookingDetailsListItemController();
    
    private final FXMLLoader fxmlLoader;
    
    private final Node view;
    
    public BookingDetailListItem() {
        super();
        
        try {
            fxmlLoader = new FXMLLoader(
                    getClass().getResource("/fxml/BookingDetailsListItem.fxml")
            );
            view = fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex + "FXML file loading failed.");
        }
    }
    
    @Override
    protected void updateItem(BookingDetailEntry bookingData, boolean empty) {
        super.updateItem(bookingData, empty);
        
        if(empty) {
            setGraphic(null);
        } else {
            controller.setData(bookingData);
            setGraphic(view);
        }
    }
    
}
