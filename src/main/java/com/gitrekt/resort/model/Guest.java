package com.gitrekt.resort.model;

/**
 * TODO: Document
 */
public class Guest {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String mailingAddress;
    private boolean isCheckedIn;

    public Guest(Long id, String firstName, String lastName, String lasrName,
            String emailAddress, String phoneNumber,
            String mailingAddress, boolean isCheckedIn) {
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.mailingAddress = mailingAddress;
        this.isCheckedIn = isCheckedIn;
    }

    public Long getId() {
        return id;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getEmailAddress(){
        return emailAddress;
    }
    
    public String getMailingAddress(){
        return mailingAddress;
    }
    
    public String phoneNumber(){
        return phoneNumber;
    }
    
    public boolean isCheckedIn(){
        return isCheckedIn;
    }
}
