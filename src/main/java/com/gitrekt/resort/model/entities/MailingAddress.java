package com.gitrekt.resort.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mailing_addresses")
public class MailingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (name = "address_line1")
    private String addressLine1;
    
    @Column (name = "address_line2")
    private String addressLine2;
    
    @Column (name = "postal_code")
    private String postalCode;
    
    @Column (name = "state")
    @Enumerated(EnumType.STRING)
    private USState state;
    
     @Column (name = "country")
    private String country;

    public MailingAddress(String addressLine1, String addressLine2,
            String postalCode,USState state, String country) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
        this.state = state;
        this.country = country;
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
    
    public USState getState() {
        return state;
    }
    
}
