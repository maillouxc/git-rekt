package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Guest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

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
        return null;
    }

    public Guest getGuestById(Long id) {
        Guest guest = entityManager.getReference(Guest.class,id);
        return  guest;
    }

    public Guest getGuestByEmailAddress(String emailAddress) {
        return null;
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
