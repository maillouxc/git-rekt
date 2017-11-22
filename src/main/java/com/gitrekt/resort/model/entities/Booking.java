package com.gitrekt.resort.model.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Guest guest;

    @Temporal(TemporalType.DATE)
    private Date checkInDate;

    @Temporal(TemporalType.DATE)
    private Date checkOutDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Bill bill;

    private String specialInstructions;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Package> packages;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Room> bookedRooms;

    private boolean isCanceled = false;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date createdDate;

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

    public boolean isCanceled() {
        return isCanceled;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCanceled(boolean canceled) {
        this.isCanceled = canceled;
    }

    public Guest getGuest() {
        return guest;
    }

    /**
     * @return The booking id, which is currently being used as the confirmation
     * number until a scheme for generating confirmation numbers in the
     * database can be properly devised.
     */
    public Long getId() {
        return this.id;
    }

}
