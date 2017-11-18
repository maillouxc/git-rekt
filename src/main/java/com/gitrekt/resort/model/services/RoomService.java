package com.gitrekt.resort.model.services;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Room;
import com.gitrekt.resort.model.entities.RoomCategory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * No, not that kind of room service.
 */
public class RoomService {
    
    private final EntityManager entityManager;
    
    /**
     * Creates an instance of the service class, along with it's associated 
     * Hibernate entityManager.
     */
    public RoomService(){
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
    
    public List<Room> getAllRooms() {
        String queryString = "FROM Room"; 
        Query q = entityManager.createQuery(queryString);        
        return q.getResultList();
    }
    
    public List<Room> getAllRoomsInCategory(String categoryName) {
        String query = "FROM Room WHERE roomCategory.name = :categoryName";
        Query q = entityManager.createQuery(query);
        q.setParameter("categoryName", categoryName);
        return q.getResultList();
    }
    
    public List<RoomCategory> getAllRoomCategories() {
        return entityManager.createQuery("FROM RoomCategory").getResultList();
    }
}
