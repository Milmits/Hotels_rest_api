package com.example.demo.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class ArrivalTime {
    private String checkIn;
    private String checkOut;

    // Геттеры и сеттеры
    public String getCheckIn() { return checkIn; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }

    public String getCheckOut() { return checkOut; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }
}

