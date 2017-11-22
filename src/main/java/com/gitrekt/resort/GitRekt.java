
package com.gitrekt.resort;

import com.gitrekt.resort.controller.ScreenManager;
import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Bill;
import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.entities.Guest;
import com.gitrekt.resort.model.entities.Package;
import com.gitrekt.resort.model.entities.Room;
import com.gitrekt.resort.model.services.BookingService;
import com.gitrekt.resort.model.services.GuestService;
import com.gitrekt.resort.model.services.PackageService;
import com.gitrekt.resort.model.services.RoomService;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The main application class shell.
 */
public class GitRekt extends Application {

    @Override
    public void start(Stage mainStage) throws IOException, PrinterException {
        ScreenManager screenManager = ScreenManager.getInstance();
        screenManager.initialize(mainStage);

        Parent root =
            FXMLLoader.load(getClass().getResource("/fxml/HomeScreen.fxml"));
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);



        Image appLogo = new Image("images/Logo.png");
        mainStage.getIcons().add(appLogo);
        mainStage.setTitle("Git-Rektsort Booking Software");

        mainStage.show();

        GuestService guestService = new GuestService();
        guestService.getCurrentlyCheckedInGuests();

        DatabaseTestDataLoader.initializeTestData();

        // TODO: Remove
        testRandomStuffRealQuick();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

        // Fix bug where application keeps running after closing
        HibernateUtil.close();
    }

    // TODO: Remove ASAP
    private void testRandomStuffRealQuick() {
        BookingService bookingService = new BookingService();
        PackageService packageService = new PackageService();
        GuestService guestService = new GuestService();
        RoomService roomService = new RoomService();
        List<Room> rooms = roomService.getAllRoomsInCategory("Basic");
        List<Package> packages = packageService.getAllPackages();
        Guest g = guestService.getGuestById(1L);
        Calendar c = new GregorianCalendar(TimeZone.getDefault());
        c.add(Calendar.DATE, 1);
        Booking b = new Booking(g, new Date(), c.getTime(), new Bill(), "", packages, rooms);
        bookingService.createBooking(b);
    }
}