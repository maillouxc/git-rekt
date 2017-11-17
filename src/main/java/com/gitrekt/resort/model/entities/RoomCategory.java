package com.gitrekt.resort.model.entities;

import javafx.scene.image.Image;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
@Entity
public class RoomCategory {
    
    @Id
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    private Double basePrice;

    private String bedsInfo;
    
    private String imageFilePath;
    
    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT EXISTS ONLY BECAUSE IT IS REQUIRED BY
     * HIBERNATE.
     */
    RoomCategory() {
        // REQUIRED BY HIBERNATE
    }
    
    public RoomCategory(String name, String description, 
        String imagePath, String bedsInfo, Double basePrice) {
        this.name = name;
        this.description = description;
        this.bedsInfo = bedsInfo;
        this.basePrice = basePrice;
        this.imageFilePath = imagePath;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * The image representing this room category, based on the file path string
     * provided when the category was created.
     */
    public Image getImage() {
        return new Image(this.imageFilePath);
    }
    
    public String getBedsInfo() {
        return bedsInfo;
    }
    
    /**
     * DANGER!
     * 
     * This method only gives you the base price of a room, which is just a part
     * of what goes into the pricing of a room. Other factors like resort
     * capacity, etc. affect this price. This method should only be used to
     * calculate the final price of the room within the appropriate service
     * class.
     * 
     * @return The base price of the room. 
     */
    public Double getBasePrice() {
        return basePrice;
    }
    
}
