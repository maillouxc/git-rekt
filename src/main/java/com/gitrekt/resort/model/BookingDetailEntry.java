
package com.gitrekt.resort.model;

/**
 * This is a class that handles the data from a guest's booking
 * 
*/
public class BookingDetailEntry {
    private String startDate;
    
    private String endDate;
    
    private String  roomNumber;
    
    private String bookingNumber;
    
    private GuestBill bill;
    
    private RoomCategory room;
    
    public BookingDetailEntry(String startDate, String endDate, String roomNumber
            , String bookingNumber, RoomCategory room, GuestBill bill){

    this.bill = bill;
    this.bookingNumber = bookingNumber;
    this.endDate = endDate;
    this.startDate = startDate;
    this.roomNumber = roomNumber;
    this.room = room;
    }
    
    public String getRoomNumber(){
       return roomNumber;
    }
    
    public String getStartDate(){
       return  startDate;
    }
    
    public String getEndDate(){
       return endDate;
    }
    
    public String getbookingNumber(){
       return  bookingNumber;
    }
    
    public GuestBill getBill(){
       return  bill;
    }
    
    public RoomCategory getRoom(){
       return room;
    }
}
