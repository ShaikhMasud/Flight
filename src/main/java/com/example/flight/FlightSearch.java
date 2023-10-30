package com.example.flight;

import java.sql.Time;
import java.util.Date;

public class FlightSearch {
    public int getticket_id;
        Integer flight_id;
        String flight_name, leave, destination;
        Date date;
        String arrival_time, departure_time;
        double price;

        public FlightSearch(Integer flight_id,String flight_name, String leave, String destination, Date date, String arrival_time, String departure_time, double price) {
//            this.ticket_id = ticket_id;
            this.flight_id = flight_id;
            this.flight_name = flight_name;
            this.leave = leave;
            this.destination = destination;
            this.date = date;
            this.arrival_time = arrival_time;
            this.departure_time = departure_time;
            this.price = price;
        }

//        public Integer getTicket_id(){return ticket_id;}
//
//        public void setTicket_id(Integer ticket_id){this.ticket_id = ticket_id;}
//
        public Integer getFlight_id() {
            return flight_id;
        }

        public void setFlight_id(Integer flight_id) {
            this.flight_id = flight_id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getFlight_name() {
            return flight_name;
        }

        public void setFlight_name(String flight_name) {
            this.flight_name = flight_name;
        }

        public String getLeave() {
            return leave;
        }

        public void setLeave(String leave) {
            this.leave = leave;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getArrival_time() {
            return arrival_time;
        }

        public void setArrival_time(String arrival_time) {
            this.arrival_time = arrival_time;
        }

        public String getDeparture_time() {
            return departure_time;
        }

        public void setDeparture_time(String departure_time) {
            this.departure_time = departure_time;
        }

    public int getticket_id() {return getticket_id;
    }
}