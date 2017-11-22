package com.gitrekt.resort.model.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String emailAddress;

    @OneToOne (cascade = CascadeType.ALL)
    private MailingAddress mailingAddress;

    private boolean isCheckedIn = false;

    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT IS ONLY HERE FOR HIBERNATE.
     */
    protected Guest() {
        // REQUIRED BY HIBERNATE
    }

    public Guest(String firstName, String lastName, String emailAddress,
            MailingAddress mailingAddress) {
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailingAddress = mailingAddress;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailingAddress) {
        this.emailAddress = emailingAddress;
    }

    public MailingAddress getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(MailingAddress mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.isCheckedIn = checkedIn;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public Long getId(){
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        Guest obj2 = (Guest) obj;
        if(obj == null){
            return false;
        }
        else if(this.firstName.equals(obj2.firstName)){
            return false;
        }
        else if(this.lastName.equals(obj2.lastName)){
            return false;
        }
        else if(this.emailAddress.equals(obj2.emailAddress)){
            return false;
        }
        else{
            return true;
        }
    }
}
