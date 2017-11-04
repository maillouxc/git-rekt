package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Guest;
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

    public List<Guest> getCurrentlyCheckedInGuests() {
        String query = "FROM Guest WHERE isCheckedIn = :param";
        Query q = entityManager.createQuery(query);
        q.setParameter("param", true);
        return q.getResultList();
    }

    public Guest getGuestById(Long id) throws EntityNotFoundException {
        Guest guest = entityManager.getReference(Guest.class,id);
        return  guest;
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
        // TODO
    }
}
