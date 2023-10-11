package com.example.flight;

import java.util.Date;

public class BookingData {
    private Integer ticket_id;
    private String flightId;
    private String userId;
    private String flight_name,leave;
    private String destination;
    private String price;
    private String bookingdate;
    Date date;
    public BookingData(String flightId, String userId, String leave, String destination, String price, String bookingdate) {
        this.flightId = flightId;
        this.userId = userId;
        this.date = date;
        this.leave = leave;
        this.destination = destination;
        this.price = price;
        this.bookingdate = bookingdate;
    }


    public Integer getTicket_id(){return ticket_id;}
    public String getFlightId() {
        return flightId;
    }

    public String getUsername() {
        return userId;
    }
    public String getFlight_name() {
        return flight_name;
    }

    public void setFlight_name(String flight_name) {
        this.flight_name = flight_name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLeave() {
        return leave;
    }

    public String getDestination() {
        return destination;
    }

    public String getPrice() {
        return price;
    }

    public String getBookingdate(){return bookingdate;}

}
