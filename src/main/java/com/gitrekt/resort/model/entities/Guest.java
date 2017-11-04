package com.gitrekt.resort.model.entities;

/**
 * TODO: Document
 */
public class Guest {
    
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private MailingAddress mailingAddress;
    private boolean isCheckedIn = true;
    
    protected Guest(){
    
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
    
    public String getLastName(){
        return lastName;
    }
    
    public void setLastName(String lastName){
           this.lastName = lastName;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public String getEmailAddress(){
        return emailAddress;
    }
    
    public void setEmailAddress(String emailingAddress){
        this.emailAddress = emailingAddress;
    }
    
    public MailingAddress getMailingAddress(){
        return mailingAddress;
    }
    
    public void setMailingAddress(MailingAddress mailingAddress){
        this.mailingAddress = mailingAddress;
    }
    
    public String getphoneNumber(){
        return phoneNumber;
    }
    
    public void setphoneNUmber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    
    public boolean isCheckedIn(){
        return isCheckedIn;
    }
}
