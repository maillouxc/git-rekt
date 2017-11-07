
package com.gitrekt.resort;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.entities.Room;
import com.gitrekt.resort.model.entities.RoomCategory;
import com.gitrekt.resort.model.entities.GuestFeedback;
import com.gitrekt.resort.model.services.GuestFeedbackService;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javax.persistence.EntityManager;

/**
 * This class is responsible for preparing the database with test data for the
 * program to operate on.
 * 
 * It's a temporary solution and pretty lame, but it's the fastest way to solve
 * our problem and keep from hindering further progress. If time permits, we
 * will try to migrate to a more permanent solution like a stored SQL script,
 * but there isn't really a reason to do that right now. 
 * 
 * This class is just meant to be a quick and dirty solution to the problem.
 * 
 * It's also not finished. It doesn't create half of the data we need yet.
 */
public class DatabaseTestDataLoader {
    
    public static void initializeTestData() {
        
        // Populate database with data on all of the rooms available
        
        // Uncomment this line later once RoomService is created
        // RoomService roomService = new RoomService()
        
        Image placeholderImg = new Image("/images/temporary_hotel_room_image_placeholder.jpg");
        
        List<Room> rooms = new ArrayList<>();
        
        RoomCategory basic = new RoomCategory(
            "Basic",
            "This room is as basic as you are. Includes complimentary bedbugs and various mystery stains on the sheets.",
            placeholderImg,
            "Beds not provided"               
        );
        
        RoomCategory familyBasic = new RoomCategory(
            "Family Basic",
            "With the Family basic room, you can be treated like dirt, but now with the whole family!",
            placeholderImg,
            "2 Queen, 2 twin"               
        );
        
        RoomCategory luxury = new RoomCategory(
            "Luxury",
            "Because in 2017 being able to go to a resort at all is a luxury. You should be thanking us.",
            placeholderImg,
            "2 Queen"               
        );
        
        RoomCategory luxuryFamily = new RoomCategory(
            "Luxury Family",
            "This room is almost bearable. Too bad you have kids and you won't be able to enjoy it.",
            placeholderImg,
            "2 Queen, 2 twin"               
        );
        
        RoomCategory king = new RoomCategory(
            "King",
            "The room that says, 'I'm better than everyone else, and I want them to know it.'",
            placeholderImg,
            "2 King"               
        );
        
        EntityManager entityManager = HibernateUtil.getEntityManager();
        
        // Put the data in the database
        entityManager.getTransaction().begin();
        
        // 1st floor rooms don't exist
        
        // 2nd floor rooms
        for(int roomNumber = 200; roomNumber < 200+50; roomNumber++) {
            entityManager.persist(new Room(String.valueOf(roomNumber), basic));
        }
        
        // 3rd floor rooms
        for(int roomNumber = 300; roomNumber < 300+50; roomNumber++) {
            Room room = new Room(String.valueOf(roomNumber), basic);
            entityManager.persist(room);
        }
        
        // 4th floor rooms
        for(int roomNumber = 400; roomNumber < 400+40; roomNumber++) {
            Room room = new Room(String.valueOf(roomNumber), familyBasic);
            entityManager.persist(room);
        }
        
        // 5th floor rooms
            for(int roomNumber = 500; roomNumber < 500+30; roomNumber++) {
                Room room = new Room(String.valueOf(roomNumber), luxury);
                entityManager.persist(room);
            }
        
        // 6th floor rooms
        for(int roomNumber = 600; roomNumber < 600+20; roomNumber++) {
            Room room = new Room(String.valueOf(roomNumber), luxuryFamily);
            entityManager.persist(room);
        }
        
        // 7th floor rooms
        for(int roomNumber = 700; roomNumber < 700+10; roomNumber++) {
            Room room = new Room(String.valueOf(roomNumber), king);
            entityManager.persist(room);
        }
        
        entityManager.getTransaction().commit();
        
        // Don't forget to close the entityManager when done with it
        entityManager.close();
        
        // Load test feedback data
        GuestFeedbackService s = new GuestFeedbackService();
        s.createNewGuestFeedback(new GuestFeedback("You suck.", "mailloux.cl@gmail.com"));
        s.createNewGuestFeedback(new GuestFeedback("You suck a lot.", "mailloux.cl@gmail.com"));
        s.createNewGuestFeedback(new GuestFeedback("You're the worst programmer ever and this simple feature took you all night to implement.", "mailloux.cl@gmail.com"));
        s.createNewGuestFeedback(new GuestFeedback("You're bad and you should feel bad.", "mailloux.cl@gmail.com"));
        
        // TODO: Room pricing data
        
        // TODO: Load test employee data
        
        // TODO: Load test guest data
    }
    
}
