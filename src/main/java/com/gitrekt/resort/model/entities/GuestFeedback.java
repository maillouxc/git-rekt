package com.gitrekt.resort.model.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "guest_feedback")
public class GuestFeedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "feedback")
    private String feedback;
    
    @Column(name = "guest_email")
    private String guestEmail;
    
    @Column(name = "is_resolved")
    private Boolean isResolved;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;
    
    public GuestFeedback(String feedback, String guestEmailAddress) {
        this.feedback = feedback;
        this.guestEmail = guestEmailAddress;
        this.isResolved = false;
    }
    
    public String getFeedback() {
        return feedback;
    }
    
    public void setResolved(boolean resolved) {
        this.isResolved = true;
    }
    
}
