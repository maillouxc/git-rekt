package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Booking;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
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
    
    public List<Booking> getBookingBetweenDates(Date startDate, Date endDate) 
            throws EntityNotFoundException{
        List<Booking> allBookings;
        List<Booking> bookingResults = new ArrayList<Booking>(200);
         String query = "FROM Booking WHERE guest is not null";
        Query q = entityManager.createQuery(query);
        allBookings = q.getResultList();
        for(int i = 0; i < allBookings.size();i++){
            boolean isDatesEqualToEnds = allBookings.get(i).getCheckInDate()
                    .equals(startDate) && allBookings.get(i).getCheckOutDate()
                            .equals(endDate) ;
            boolean isDatesInRange = allBookings.get(i).getCheckInDate()
                    .after(startDate) && allBookings.get(i).getCheckOutDate()
                            .before(endDate);
            if( (isDatesEqualToEnds || isDatesInRange) == true ){
                bookingResults.add(allBookings.get(i));
            }
        }
        return bookingResults;
    }
}
