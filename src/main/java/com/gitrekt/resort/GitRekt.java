
package com.gitrekt.resort;

import com.gitrekt.resort.controller.ScreenManager;
import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Bill;
import com.gitrekt.resort.model.entities.Guest;
import com.gitrekt.resort.model.entities.GuestFeedback;
import com.gitrekt.resort.model.entities.MailingAddress;
import com.gitrekt.resort.model.entities.UsState;
import com.gitrekt.resort.model.services.BillService;
import com.gitrekt.resort.model.services.EmailService;
import com.gitrekt.resort.model.services.GuestService;
import java.awt.print.PrinterException;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

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
        
        testHibernate();
        
        //testEmailService();
        
        // Uncomment this line test printing a customer bill
        //testBillService();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void testBillService() throws IOException, PrinterException {
        BillService billService = new BillService();
        billService.printBill(new Bill());
    }
    
    // TODO: Remove
    private void testHibernate() {
        // This method just stores an entity in the database really quick to
        // demonstrate Hibernate and verify that it is working and properly
        // configured. This should be removed later.
        
        GuestFeedback feedback = new GuestFeedback("This place sucks.", "mailloux.cl@gmail.com");
        
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(feedback);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            // If shit hits the fan, rewind it to prevent database problems.
            entityManager.getTransaction().rollback();
        }
    }
    
    // TODO: Remove
    private void testEmailService() {
        EmailService emailService = new EmailService();
        String messageText = "This is a test email.";
        try {
            emailService.sendTestEmail(
                    "mailloux.cl@gmail.com",
                    "Testing email service",
                    messageText
            );
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    
}