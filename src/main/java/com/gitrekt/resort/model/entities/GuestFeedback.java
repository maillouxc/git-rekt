package com.gitrekt.resort.model.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "guest_feedback") // Override the default name for the table
public class GuestFeedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String feedback;
    
    private String guestEmail;
    
    private Boolean isResolved;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    /**
     * DO NOT CALL THIS CONSTRUCTOR - IT IS INTEDED FOR USE ONLY BY HIBERNATE.
     */
    GuestFeedback() {
        // REQUIRED BY HIBERNATE
    }
    
    public GuestFeedback(String feedback, String guestEmailAddress) {
        this.feedback = feedback;
        this.guestEmail = guestEmailAddress;
        this.isResolved = false;
    }
    
    public String getFeedback() {
        return feedback;
    }
    
    public Long getId() {
        return id;
    }
    
    public Date getCreationDate() {
        return createdDate;
    }
    
    public String getGuestEmail() {
        return guestEmail;
    }
    
    public Boolean isResolved(){
        return isResolved;
    }
    
    public void setIsResolved(boolean resolved) {
        this.isResolved = true;
    }
    
}
