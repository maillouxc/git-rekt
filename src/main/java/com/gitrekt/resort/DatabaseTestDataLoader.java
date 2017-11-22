package com.gitrekt.resort;

import com.gitrekt.resort.hibernate.HibernateUtil;
import com.gitrekt.resort.model.UsState;
import com.gitrekt.resort.model.entities.Booking;
import com.gitrekt.resort.model.entities.Employee;
import com.gitrekt.resort.model.entities.Guest;
import com.gitrekt.resort.model.entities.GuestFeedback;
import com.gitrekt.resort.model.entities.MailingAddress;
import com.gitrekt.resort.model.entities.Package;
import com.gitrekt.resort.model.entities.Room;
import com.gitrekt.resort.model.entities.RoomCategory;
import com.gitrekt.resort.model.services.GuestFeedbackService;
import com.gitrekt.resort.model.services.PackageService;
import com.gitrekt.resort.model.services.RoomService;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * This class is responsible for preparing the database with test data for the program demo.
 *
 * It's a temporary solution and pretty lame, but it's the fastest way to solve our problem and
 * keep from hindering further progress. If time permits, we will try to migrate to a more
 * permanent solution like a stored SQL script, but there isn't a reason to do that right now.
 */
public class DatabaseTestDataLoader {

    public static void initializeTestData() {
        // Populate database with data on all of the rooms available
        RoomCategory basic = new RoomCategory(
            "Basic",
            "This room is as basic as you are. Includes complimentary bedbugs "
                + "and various mystery stains on the sheets. Used needles also "
                + "included free of charge.",
            "images/rooms/basic.jpg",
            "Beds not provided",
            100.00
        );

        RoomCategory familyBasic = new RoomCategory(
            "Family Basic",
            "With the Family basic room, you can be treated like the dirt you"
                + " are, but now with the whole family!",
            "images/rooms/family_basic.jpg",
            "2 Queen, 2 twin",
            125.99
        );

        RoomCategory luxury = new RoomCategory(
            "Luxury",
            "Because in 2017 being able to go to a resort at all is a luxury. "
                + "You should be thanking us.",
            "images/rooms/luxury.jpg",
            "2 Queen",
            159.99
        );

        RoomCategory luxuryFamily = new RoomCategory(
            "Luxury Family",
            "This room is almost bearable. Too bad you have kids and you won't "
                + "be able to enjoy it.",
            "images/rooms/luxury_family.jpg",
            "2 Queen, 2 twin",
            179.67
        );

        RoomCategory king = new RoomCategory(
            "King",
            "The room that says, \"I'm better than everyone else, and I want"
                + " them to know it.\" Includes access to a large arena where"
                + " basic level guests battle to the death for a small sum of"
                + "money - plus, we give you javelins to throw at the winner."
                + " Helipad access available.",
            "images/rooms/king.jpg",
            "2 King",
            259.99
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

        // Generate package data.
        Package package1 = new Package("Loch-Ness monster viewing", 3.50);
        Package package2 = new Package("Basement tour", 10.00);
        Package package3 = new Package("Drug cartel abduction experience", 650.00);
        Package package4 = new Package("Surfing with sharks", 580.99);

        entityManager.persist(package1);
        entityManager.persist(package2);
        entityManager.persist(package3);
        entityManager.persist(package4);

        // Generate test guest data
        Guest g1 = new Guest("Chris", "Mailloux", "mailloux.cl@gmail.com", new MailingAddress("525 fake way", null, "Fort Myers", "33969", UsState.FLORIDA, "United States"));
        Guest g2 = new Guest("Chrsfgmis", "Mailloux", "mailsfghux.cl@gmail.com", new MailingAddress("525 fake way", null, "Fort Myers", "33969", UsState.FLORIDA, "United States"));
        Guest g3 = new Guest("Chris", "Mailldfghoux", "maillsfghsfghsoux.cl@gmail.com", new MailingAddress("525 fake way", null, "Fort Myers", "33969", UsState.FLORIDA, "United States"));
        Guest g4 = new Guest("Chrawetis", "Mailloux", "maillojytfkdfux.cl@gmail.com", new MailingAddress("525 fake way", null, "Fort Myers", "33969", UsState.FLORIDA, "United States"));

        entityManager.persist(g1);
        entityManager.persist(g2);
        entityManager.persist(g3);
        entityManager.persist(g4);

        // Load test employee data
        Employee e1 = new Employee(1L, "gitrekt", true, "Chris", "Mailloux");
        Employee e2 = new Employee(2L, "bassface", false, "Chris", "Kael");
        Employee e3 = new Employee(Long.valueOf(3),"1234", true, "Juan" , "Gomez");
        Employee e4 = new Employee(Long.valueOf(4),"1234", true, "Juanito" , "Gomez");
        Employee e5 = new Employee(Long.valueOf(5),"1234", false, "Juana" , "Gomez");
        Employee e6 = new Employee(Long.valueOf(6),"1234", false, "Juanita" , "Gomez");
        Employee e7 = new Employee(Long.valueOf(7),"1234", true, "Juanucho" , "Gomez");

        entityManager.persist(e1);
        entityManager.persist(e2);
        entityManager.persist(e3);
        entityManager.persist(e4);
        entityManager.persist(e5);
        entityManager.persist(e6);
        entityManager.persist(e7);

        entityManager.getTransaction().commit();
        entityManager.close();

        // Load test feedback data
        GuestFeedbackService s = new GuestFeedbackService();
        s.createNewGuestFeedback(new GuestFeedback("You suck.", "mailloux.cl@gmail.com"));
        s.createNewGuestFeedback(new GuestFeedback("You suck a lot.", "mailloux.cl@gmail.com"));
        s.createNewGuestFeedback(new GuestFeedback("You're the worst programmer ever and this simple feature took you all night to implement.", "mailloux.cl@gmail.com"));
        s.createNewGuestFeedback(new GuestFeedback("You're bad and you should feel bad.", "mailloux.cl@gmail.com"));

        createTestBookingData();
    }

    private static void createTestBookingData() {
        EntityManager entityManager = HibernateUtil.getEntityManager();

        Guest g1 = new Guest("Chris", "Mailloux", "maillasdgoux.cl@gmail.com", new MailingAddress("525 fake way", null, "Fort Myers", "33969", UsState.FLORIDA, "United States"));
        entityManager.persist(g1);

        RoomService r = new RoomService();
        List<Room> testRooms = r.getAllRoomsInCategory("Basic");
        System.out.println(testRooms.size() + " Basic rooms added to test booking");

        Calendar testCalendar = new GregorianCalendar();
        Date d1 = new Date();
        testCalendar.add(Calendar.DAY_OF_MONTH, 3);
        Date d2 = testCalendar.getTime();

        PackageService packageService = new PackageService();
        List<Package> allPackages = packageService.getAllPackages();
        Booking b1 = new Booking(g1, d1, d2, "you suck", allPackages, testRooms);

        entityManager.getTransaction().begin();
        entityManager.persist(b1);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
