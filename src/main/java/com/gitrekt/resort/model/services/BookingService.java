package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.BillItem;
import com.gitrekt.resort.model.entities.Booking;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
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
            EmailService emailService = new EmailService();
            
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
     * @param booking The booking to calculate cancellation fees for.
     * 
     * @return The cancellation fee to cancel the provided booking.
     */
    public static double calcCancellationFee(Booking booking) {  
        // Define some useful dates for our calculations
        LocalDateTime today = LocalDateTime.now();
        Instant temp = booking.getCheckInDate().toInstant();
        LocalDateTime checkInDate = LocalDateTime.from(temp);
        temp = booking.getCreatedDate().toInstant();
        LocalDateTime bookingCreationDate = LocalDateTime.from(temp);
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
}
