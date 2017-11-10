package com.gitrekt.resort.model.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Room {
    
    @Id
    private String roomNumber;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private RoomCategory roomCategory;
    
    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT EXISTS ONLY BECAUSE IT IS REQUIRED BY
     * HIBERNATE.
     */
    Room() {
        // REQUIRED BY HIBERNATE
    }
    
    public Room(String roomNumber, RoomCategory roomCategory) {
        this.roomNumber = roomNumber;
        this.roomCategory = roomCategory;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public RoomCategory getRoomCategory() {
        return roomCategory;
    }
    
}
