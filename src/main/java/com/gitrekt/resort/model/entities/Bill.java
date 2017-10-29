
package com.gitrekt.resort.model.entities;

import java.util.List;

public class Bill {
    
    private List<BillItem> charges;
    
    public Bill() {
        // TODO
    }
    
    public List<BillItem> getCharges() {
        return charges;
    }
}
