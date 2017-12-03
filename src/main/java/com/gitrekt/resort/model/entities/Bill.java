
package com.gitrekt.resort.model.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> charges;

    /**
     * NO ARG CONSTRUCTOR REQUIRED BY HIBERNATE.
     */
    public Bill() {
        this.charges = new ArrayList<>();
    }

    public List<BillItem> getCharges() {
        return charges;
    }

    public void addCharge(BillItem item) {
        charges.add(item);
    }

    public double getTotal() {
        double total = 0.00;
        for(BillItem item : charges) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
