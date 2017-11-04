package com.gitrekt.resort.model.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "first_name") 
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    @Column (name = "email_address")
    private String emailAddress;
    
    @Column (name = "phone_numbets")
    private String phoneNumber;

    @Column (name = "mailing_address")
    @OneToOne (cascade = CascadeType.ALL)
    private MailingAddress mailingAddress;
    
    @Column (name = "checked_in")
    private boolean isCheckedIn = true;

    protected Guest() {

    }

    public Guest(String firstName, String lastName,
            String emailAddress, String phoneNumber,
            MailingAddress mailingAddress) {
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
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

    public String getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNUmber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void detChexkedIn(boolean checkedIn) {
        this.isCheckedIn = checkedIn;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }
}
