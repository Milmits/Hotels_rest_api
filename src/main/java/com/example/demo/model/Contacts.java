package com.example.demo.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Contacts {
    private String phone;
    private String email;

    // Геттеры и сеттеры
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

