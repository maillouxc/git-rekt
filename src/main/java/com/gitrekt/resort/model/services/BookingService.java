package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.BillItem;
import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.entities.RoomCategory;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Handles business logic related to bookings.
 */
public class BookingService {

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Constructs a basic instance of BookingService.
     * 
     * Initializes an entityManager for the service, which is used to manage
     * Hibernate entities for database persistence. This entityManager is later
     * closed by the finalize method and so does not need to (nor can it be)
     * explicitly closed.
     */
    public BookingService() {
        this.entityManager = HibernateUtil.getEntityManager();
    }

    /**
     * Takes care of closing the Hibernate entityManager for the class.
     * 
     * @throws Throwable 
     */
    @Override
    public void finalize() throws Throwable {
        super.finalize();
        this.entityManager.close();
    }

    /**
     * Stores a new booking in the database.
     * 
     * Currently handles no other business logic besides this simple store 
     * operation. This could change in the future before v1.0 release, before
     * the service interface is locked down. 
     * 
     * Also sends a confirmation email to the user with the booking number for 
     * future reference.
     * 
     * @param booking The booking object to persist to the data store. 
     */
    public void createBooking(Booking booking) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(booking);
            sendBookingConfirmationEmail(booking);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Log rollback or notify user somewhere, possibly in e.
            throw e;
        }
    }

    /**
     * @param id The unique id of the booking to return.
     * 
     * @return A booking with the provided id.
     */
    public Booking getBookingById(Long id) {
        Booking booking = entityManager.getReference(Booking.class, id);
        return booking;
    }
    
    /**
     * Updates the booking instance stored in the database with the values from
     * the passed booking instance.
     * 
     * This method should be used only when no existing service method exists
     * that better suits the specific task, due to the benefits gained by finer
     * transaction control and exception handling.
     * 
     * @param booking The booking to update.
     */
    public void updateBooking(Booking booking){
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(booking);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Log rollback or notify user somewhere, possibly in e.
            throw e;
        }
    }
    
    /**
     * @param startDate The beginning of the date range to search.
     * 
     * @param endDate The end of the date range to search.
     * 
     * @return A list of all bookings in the system where the check-in date 
     * falls between the provided date range. Does not return bookings which 
     * have been canceled.
     */
    public List<Booking> getBookingsBetweenDates(Date startDate, Date endDate) {        
        String queryString = 
            "FROM Booking WHERE (checkInDate BETWEEN :startDate AND :endDate)"
                + "OR (checkOutDate BETWEEN :startDate AND :endDate)"; 
        Query q = entityManager.createQuery(queryString);
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        
        List<Booking> results = q.getResultList();
        return results;
    }
    
    /**
     * Cancels the provided booking, which includes applying the business 
     * requirement to assess a cancellation fee on the bill.
     * 
     * If provided a booking where the isCanceled field is already set to true,
     * this method simply returns without doing any other work. This behavior 
     * can be used to implement custom cancellation logic if desired, and 
     * avoids the overhead of having to consult the database to resolve the 
     * discrepancy or doing weird stuff to control the field more strictly.
     * 
     * @param booking The booking to cancel.
     */
    public void cancelBooking(Booking booking) {
        if(booking.isCanceled()) {
            return;
            // TODO: Handle/Log/Consider some kind of error message
        }
        
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(booking);
            booking.setCanceled(true);
            double cancellationFee = calcCancellationFee(booking);
            BillItem refund = new BillItem(
                "Refund", booking.getBill().getTotal() * -1, 1
            );
            booking.getBill().getCharges().add(refund);
            BillItem cancellationCharge = new BillItem(
                "Cancellation fee", cancellationFee, 1
            );
            booking.getBill().getCharges().add(cancellationCharge);
            sendBookingCancellationEmail(booking);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Handle exception behavior
        }
        
    }
    
    /**
     * Calculates and returns the cancellation fee that should be assessed when
     * a user cancels a booking.
     * 
     * This method uses the new Java 8 Time API, which a little confusing at
     * first, but way more useful (and easier to use) than the nightmarish way 
     * things used to be done.
     * 
     * It does NOT account for time zones, since in the real world, this code
     * would be running server side, not client side, and then time zones would
     * not matter.
     * 
     * Sadly, the new Java Time and Date API is almost laughably annoying in 
     * that there is NO built in easy way to transform an existing Java Date
     * object into a new time api date object, excepting the toInstant method,
     * which brings with it it's own cluttered bag of complications. Why?!?
     * 
     * @param booking The booking to calculate cancellation fees for.
     * 
     * @return The cancellation fee to cancel the provided booking.
     */
    public static double calcCancellationFee(Booking booking) {  
        // Define some useful dates for our calculations
        LocalDate today = LocalDate.now();
        Instant temp = booking.getCheckInDate().toInstant();
        ZonedDateTime zdt = temp.atZone(ZoneId.systemDefault());
        LocalDate checkInDate = zdt.toLocalDate();
        temp = booking.getCreatedDate().toInstant();
        zdt = temp.atZone(ZoneId.systemDefault());
        LocalDate bookingCreationDate = LocalDate.from(zdt);
        long daysTillCheckIn = today.until(checkInDate, ChronoUnit.DAYS);
        
        // Determine the fee percentage based on specifications
        double feePercentage;
        if(bookingCreationDate.plusDays(2).isAfter(today)) {
            feePercentage = 0.00;
        } else if(daysTillCheckIn > 30) {
            feePercentage = 20.00;
        } else if(daysTillCheckIn > 7) {
            feePercentage = 30.00;
        } else {
            feePercentage = 60.00;
        }
        
        // Calculate the fee from the bill and the fee percentage
        double fee = 0.00;
        
        for(BillItem item : booking.getBill().getCharges()) {
            double totalItemPrice = item.getTotalPrice();
            // Check for cases where another discount is applied, etc.
            if(item.getTotalPrice() > 0) {
                fee += (totalItemPrice * feePercentage);
            }
        }
        
        return fee;
    }
    
    /**
     * Sends an email to the user confirming their booking, along with a booking
     * number that is used to reference the booking by the customer.
     * 
     * This method probably belongs in it's own class, but for now, this is
     * good enough for our needs.
     * 
     * At some point, it would be nice to use a parameterized template system
     * like ThymeLeaf or something similar, rather than the clunky StringBuilder
     * approach that is currently used, as well as HTTP email rather than
     * plaintext, but these enhancements are reserved for a future where I don't
     * have 3 major projects running concurrently.
     * 
     * Also, finally note that the exception handling here is basically 
     * nonexistent, due to the extremely limited time we have to develop this
     * prototype. This is something that would need to be addressed in future
     * iterations.
     * 
     * @param booking The booking which the email is being sent for.
     */
    private void sendBookingConfirmationEmail(Booking booking) {
        EmailService emailService = new EmailService();
        String subjectLine = "Here's your booking info";
        String toAddress = booking.getGuest().getEmailAddress();
        StringBuilder builder = new StringBuilder();
        
        builder.append("Thanks for placing a booking at Git-Rekt Resort.\n\n");
        builder.append("Your booking number is ");
        builder.append(booking.getId().toString());
        builder.append(" \n\n");
        builder.append("Please save this number, as you will need it to "
                + "reference the booking in the future, view your bill, "
                + "check-in, or cancel the booking.");
        builder.append("\n\nThank you for being dumb enough to book with us.");
        String emailText = builder.toString();
        
        try {
            emailService.sendEmail(toAddress, subjectLine, emailText);
        } catch (MessagingException e) {
            System.err.println("Error sending confirmation email");
            // TODO: Handle exception better.
        }
    }
    
    /**
     * Sends an email confirming the cancellation of the guest's booking.
     * 
     * See the documentation on the sendBookingConfirmationEmail method for some
     * comments that apply here as well regarding the design of this method,
     * since the two methods are basically identical in design.
     * 
     * @param booking The booking to send the email regarding.
     */
    private void sendBookingCancellationEmail(Booking booking) {
        EmailService emailService = new EmailService();
        String subjectLine = "Your booking has been cancelled. Loser.";
        String toAddress = booking.getGuest().getEmailAddress();
        StringBuilder builder = new StringBuilder();
        
        builder.append("Hey loser, we cancelled your booking, booking number ");
        builder.append(booking.getId().toString());
        builder.append(" like you asked.");
        builder.append("\n\n");
        builder.append("It's okay, we didn't want people like you in our resort"
                + " anyway.");
        builder.append(" \n\n");
        builder.append("Your bill total is $");
        builder.append(booking.getBill().getTotal());
        builder.append(", which includes any applicable cancellation fees.");
        builder.append("\n\nPay us, or we'll be forced to take it ourselves.");
        String emailText = builder.toString();
        
        try {
            emailService.sendEmail(toAddress, subjectLine, emailText);
        } catch (MessagingException e) {
            System.err.println("Error sending confirmation email");
            // TODO: Handle exception better.
        }
    }
    
    public List<RoomCategory> getRoomTypesAvailable(
        Date checkIn, Date checkout
    ) {
        // TODO
        String queryString = "";
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }
    
}
