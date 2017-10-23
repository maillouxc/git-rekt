package com.gitrekt.resort.model;

import javafx.scene.image.Image;

/**
 * The type of room, containing properties such as the number of beds, 
 * a text description of the room, images of the room, etc. Because all resort
 * rooms in the same category share the same core set of features, there is no
 * need to tie these properties to the room object itself.
 * 
 * This implementation is a temporary thrown together implementation meant to 
 * test some UI components. It should not be used for real programming until it
 * has been better designed and further conceptually explored within our 
 * architecture. It has several flaws, and would be unsuitable for a production
 * application. Chief among these flaws is how images are handled. Images should
 * probably be stored in some kind of room image collection along with a 
 * way to get just the thumbnail for the room category. The images should 
 * probably also be fetched from S3, instead of stored locally in the DB. These
 * are things that need to be considered for a real implementation.
 */
public class RoomCategory {
    
    private String name;
    
    private String description;
    
    private Image roomCategoryImage;
    
    // I really don't think our domain model is complicated enough that we need
    // a dedicated Bed class, and a BedType enum to represent beds in our rooms.
    // For now, we can just store a string. If things change, it won't be hard
    // to added in a concrete type for beds in a room.
    private String bedsInfo;
    
    public RoomCategory(String name, String description, 
        Image roomCategoryImage, String bedsInfo) {
        this.name = name;
        this.description = description;
        this.roomCategoryImage = roomCategoryImage;
        this.bedsInfo = bedsInfo;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    // We may want to modify this so that we use S3 to fetch images, but that
    // is a future problem that we don't need to solve anytime soon. This is 
    // good enough for now.
    public Image getImage() {
        return roomCategoryImage;
    }
    
    public String getBedsInfo() {
        return bedsInfo;
    }
    
}
