package com.example.flight;

import java.util.Date;

public class BookingDetails {
    private Integer bookingId;
    private Integer userId;
    private Integer ticketId;
    private Integer flightId;
    private String flightName;
    private String leaveAirport;
    private String destinationAirport;
    private Date date;
    private String arrivalTime;
    private String departureTime;
    private Double price;
    private Date bookingDatetime;

    private static Integer loggedInUserId;

    public static Integer getLoggedInUserId() {
        return loggedInUserId;
    }

    public static void setLoggedInUserId(Integer userId) {
        loggedInUserId = userId;
    }

    public BookingDetails(
            Integer bookingId,
            Integer userId,
            Integer ticketId,
            Integer flightId,
            String flightName,
            String leaveAirport,
            String destinationAirport,
            Date date,
            String arrivalTime,
            String departureTime,
            Double price,
            Date bookingDatetime
    ) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.ticketId = ticketId;
        this.flightId = flightId;
        this.flightName = flightName;
        this.leaveAirport = leaveAirport;
        this.destinationAirport = destinationAirport;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.price = price;
        this.bookingDatetime = bookingDatetime;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getLeaveAirport() {
        return leaveAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public Date getDate() {
        return date;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public Double getPrice() {
        return price;
    }

    public Date getBookingDatetime() {
        return bookingDatetime;
    }
}
