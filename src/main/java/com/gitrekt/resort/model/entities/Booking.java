package com.gitrekt.resort.model.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Guest guest;
    
    @Temporal(TemporalType.DATE)
    private Date checkInDate;
    
    @Temporal(TemporalType.DATE)
    private Date checkOutDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Bill bill;

    private String specialInstructions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Package> packages;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> bookedRooms;

    /**
     * DO NOT CALL THIS CONSTRUCTOR. IT IS INTENDED FOR USE BY HIBERNATE ONLY.
     */
    protected Booking() {
        // REQUIRED BY HIBERNATE
    }

    public Booking(Guest guest, Date checkInDate, Date checkOutDate, Bill bill,
            String specialInstructions, List<Package> packages, 
            List<Room> bookedRooms) {

        this.guest = guest;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bill = bill;
        this.specialInstructions = specialInstructions;
        this.packages = packages;
        this.bookedRooms = bookedRooms;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Bill getBill() {
        return bill;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public List<Room> getBookedRooms() {
        return bookedRooms;
    }

}
