package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Guest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class GuestService {

    private final EntityManager entityManager;

    public GuestService() {
        this.entityManager = HibernateUtil.getEntityManager();
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        this.entityManager.close();
    }

    public List<Guest> getCurrentlyCheckedInGuests() {
        try {
            String query
                    = "FROM Guest WHERE Guest.isCheckedIn = :param";
            Query q = entityManager.createQuery(query);
            q.setParameter("param", true);
            return q.getResultList();
        } catch (EntityNotFoundException e) {
            return null;
        }

    }

    public Guest getGuestById(Long id) {
        Guest guest = entityManager.getReference(Guest.class, id);
        return guest;
    }

    public Guest getGuestByEmailAddress(String emailAddress) {
        try {
            String query
                    = "FROM Guest WHERE Guest.emailAddress = :emailAddress";
            Query q = entityManager.createQuery(query);
            q.setParameter("emailAddress", emailAddress);
            return (Guest) q.getSingleResult();
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public void createNewGuest(Guest guest) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(guest);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void updateGuest(Guest guest) {
        // TODO
    }
}
