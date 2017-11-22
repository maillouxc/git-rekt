package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.entities.Guest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class GuestService {

    @PersistenceContext
    private final EntityManager entityManager;

    public GuestService() {
        this.entityManager = HibernateUtil.getEntityManager();
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        this.entityManager.close();
    }
    
    /**
     * Forcefully closes the entityManager for this service.
     */
    public void cleanup() {
        entityManager.close();
    }

    public List<Guest> getCurrentlyCheckedInGuests() {
        String query = "FROM Guest WHERE isCheckedIn = :param";
        Query q = entityManager.createQuery(query);
        q.setParameter("param", true);
        return q.getResultList();
    }

    public Guest getGuestById(Long id) throws EntityNotFoundException {
        Guest guest = entityManager.getReference(Guest.class, id);
        return guest;
    }

    public Guest getGuestByEmailAddress(String emailAddress)
            throws EntityNotFoundException {
        String query = "FROM Guest WHERE emailAddress = :emailAddress";
        Query q = entityManager.createQuery(query);
        q.setParameter("emailAddress", emailAddress);
        return (Guest) q.getSingleResult();
    }

    public void createNewGuest(Guest guest) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(guest);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Log rollback or notify user somewhere, possibly in e.
            throw e;
        }
    }

    public void updateGuest(Guest guest) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(guest);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Log rollback or notify user somewhere, possibly in e.
            throw e;
        }
    }
    
    public List<Guest> getDailyGuestRegistry(){
        LocalDate today = LocalDate.now();
        Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        BookingService b = new BookingService();
        List<Guest> guests = new ArrayList();
        for (Booking booking : b.getBookingsBetweenDates(date, date)){
            guests.add(booking.getGuest());
        }        
        String queryString = "FROM Guest WHERE isCheckedIn = true";
        Query query = entityManager.createQuery(queryString);
        List<Guest> checkedInGuests = query.getResultList();
        for(Guest g : checkedInGuests) {
            if(!guests.contains(g)) {
                guests.add(g);
            }
        }
        return guests;
    }
}
