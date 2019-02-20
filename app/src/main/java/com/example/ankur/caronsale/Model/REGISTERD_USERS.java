package com.example.ankur.caronsale.Model;

public class REGISTERD_USERS {

    //        String CREATE_LOGIN_TABLE = "CREATE TABLE REGISTERD_USERS (ID INTEGER PRIMARY KEY NOT NULL, UNIQUE_ID TEXT NOT NULL, NAME TEXT NOT NULL," +
    //                "EMAIL TEXT NOT NULL, PHONE_NUMBER TEXT NOT NULL, STREET_ADDRESS TEXT, POSTAL_CODE TEXT, CREATED_AT TEXT, UPDATED_AT TEXT)";

    private long Id;
    private String uniqueId;
    private String userName;
    private String userEmail;
    private String phoneNumber;
    private String street_address;
    private String postalCode;
    private String created_at;

    public REGISTERD_USERS() {
    }

    public REGISTERD_USERS(long id, String uniqueId, String userName, String userEmail, String phoneNumber, String street_address, String postalCode, String created_at) {
        Id = id;
        this.uniqueId = uniqueId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.phoneNumber = phoneNumber;
        this.street_address = street_address;
        this.postalCode = postalCode;
        this.created_at = created_at;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
