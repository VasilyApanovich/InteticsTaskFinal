package com.dto;

import java.io.Serializable;
import java.util.Date;


public class Client implements Serializable, Cloneable {

    private int clientId = 0;
    private String firstName = "";
    private String lastName = "";
    private Date dateOfBirth;
    private String address = "";
    private String phone = "";
    private String email = "";


    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" + "clientId=" + clientId + ", firstName=" + firstName
                + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", address=" + address
                + ", phone=" + phone + ", email=" + email + "}";
    }
}
