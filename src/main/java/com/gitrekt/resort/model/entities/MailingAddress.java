package com.gitrekt.resort.model.entities;

import com.gitrekt.resort.model.UsState;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MailingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressLine1;

    private String addressLine2;

    private String postalCode;

    @Enumerated(EnumType.STRING)
    private UsState state;

    private String city;

    private String country;

    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT EXISTS ONLY BECAUSE IT IS REQUIRED BY HIBERNATE.
     */
    MailingAddress() {
        // REQUIRED BY HIBERNATE
    }

    public MailingAddress(String addressLine1, String addressLine2, String city, String postalCode,
            UsState state, String country) {
        
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
        this.state = state;
        this.country = country;
        this.city = city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public UsState getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

}
