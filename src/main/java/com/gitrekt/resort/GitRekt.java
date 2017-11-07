
package com.gitrekt.resort;

import com.gitrekt.resort.controller.ScreenManager;
import com.gitrekt.resort.model.entities.GuestFeedback;
import com.gitrekt.resort.model.services.GuestFeedbackService;
import com.gitrekt.resort.model.services.GuestService;
import java.awt.print.PrinterException;
import java.io.IOException;
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
        
        // TEST SOME STUFF TEMPORARILY - REMOVE ASAP
        GuestFeedbackService s = new GuestFeedbackService();
        s.createNewGuestFeedback(new GuestFeedback("You suck.", "mailloux.cl@gmail.com"));
        s.createNewGuestFeedback(new GuestFeedback("You suck a lot.", "mailloux.cl@gmail.com"));
        s.createNewGuestFeedback(new GuestFeedback("You're the worst programmer ever and this simple feature took you all night to implement.", "mailloux.cl@gmail.com"));
        s.createNewGuestFeedback(new GuestFeedback("You're bad and you should feel bad.", "mailloux.cl@gmail.com"));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }  
}