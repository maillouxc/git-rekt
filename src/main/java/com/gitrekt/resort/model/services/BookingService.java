package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Booking;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class BookingService {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookingService() {
        this.entityManager = HibernateUtil.getEntityManager();
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        this.entityManager.close();
    }

    public void createBooking(Booking booking) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(booking);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Log rollback or notify user somewhere, possibly in e.
            throw e;
        }
    }

    public Booking getBookingById(Long id) {
        Booking booking = entityManager.getReference(Booking.class, id);
        return booking;
    }
    
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
}
