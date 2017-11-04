package com.gitrekt.resort.model.entities;

public class MailingAddress {
    
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    //TODO Add state and country fields
    
    public MailingAddress(String addressLine1,String addressLine2,
            String postalCode){
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
    }
    
    public String getAddressLine1(){
        return addressLine1;
    }
    
    public String getAddressLine2(){
        return addressLine2;
    }
    
    public String getPostalCode(){
        return postalCode;
    }
    
    //TODO Add getters for state and country
}
