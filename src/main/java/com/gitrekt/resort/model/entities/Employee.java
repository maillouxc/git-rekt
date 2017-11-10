package com.gitrekt.resort.model.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String hashedPassword;

    private boolean isManager = false;

    private String firstName;

    private String lastName;
    
    private Date lastPasswordChangeDate;

    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT IS INTENDED FOR USE BY HIBERNATE ONLY.
     */
    protected Employee() {
        // REQUIRED BY HIBERNATE
    }

    public Employee(String plaintextPassword, boolean isManager,
            String firstName, String lastName) {
        encryptPassword(plaintextPassword);
        this.isManager = isManager;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return employeeId;
    }

    public boolean isManager() {
        return isManager;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }
    
    public void setHashedPassword(String hashedPassword){
        this.hashedPassword = hashedPassword;
        this.lastPasswordChangeDate = new Date();
    }
    
    public Date getLastPasswordChangeDate() {
        return lastPasswordChangeDate;
    }
    
    private void encryptPassword(String plaintextPassword) {
        // TODO
    }

}
