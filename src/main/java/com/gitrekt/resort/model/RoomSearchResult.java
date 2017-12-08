            package com.gitrekt.resort.model;

import com.gitrekt.resort.model.entities.RoomCategory;

public class RoomSearchResult {
    
    private final RoomCategory roomCategory;
    
    private final Double roomPrice;
    
    private int numberAvailable;
    
    public RoomSearchResult(double roomPrice, RoomCategory roomCategory,
            int numAvailable) {
        this.roomCategory = roomCategory;
        this.roomPrice = roomPrice;
        this.numberAvailable = numAvailable;
    }
    
    public RoomCategory getRoomCategory() {
        return roomCategory;
    }
    
    public Double getRoomPrice() {
        return roomPrice;
    }
    
    public int getNumAvailable() {
        return numberAvailable;
    }
    
    public void setNumAvailable(int numAvailable) {
        this.numberAvailable = numAvailable;
    }
    
}
