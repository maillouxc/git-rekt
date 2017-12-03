package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Guest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class GuestService {

    public List<Guest> getCurrentlyCheckedInGuests() {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        String query = "FROM Guest WHERE isCheckedIn = :param";
        Query q = entityManager.createQuery(query);
        q.setParameter("param", true);
        List<Guest> results = q.getResultList();
        entityManager.close();
        return results;
    }

    public Guest getGuestById(Long id) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        Guest guest = entityManager.getReference(Guest.class, id);
        entityManager.close();
        return guest;
    }

    public Guest getGuestByEmailAddress(String emailAddress) throws NoResultException {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        String query = "FROM Guest WHERE emailAddress = :emailAddress";
        Query q = entityManager.createQuery(query);
        q.setParameter("emailAddress", emailAddress);
        Guest result = (Guest) q.getSingleResult();
        entityManager.close();
        return result;
    }

    public void createNewGuest(Guest guest) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(guest);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public void updateGuest(Guest guest) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(guest);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
            // TODO: Log rollback or notify user somewhere, possibly in e.
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * @return The lists of guests that are on the registry for the given day. This includes both
     * guests who have bookings covering today, and guests who are supposed to be, but are not yet
     * checked out.
     */
    public List<Guest> getDailyGuestRegistry(){
        EntityManager entityManager = HibernateUtil.getEntityManager();
        LocalDate today = LocalDate.now();
        Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        // I'm pretty sure we could use left join here but I'm not exactly brushed up on SQL right
        // now so this works.
        String queryString = "SELECT guest FROM Booking AS b WHERE b.guest.isCheckedIn = true"
                + " or trunc(sysdate) BETWEEN b.checkInDate and b.checkOutDate";
        Query query = entityManager.createQuery(queryString);
        List<Guest> guests = query.getResultList();
        entityManager.close();
        return guests;
    }
}
