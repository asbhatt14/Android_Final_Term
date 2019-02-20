package com.example.ankur.caronsale.Model;

public class Like {

    private long id;
    private long carID;
    private String ownerId;
    private int IsLike;


    public Like() {
    }

    public Like(long id, long carID, String ownerId, int isLike) {
        this.id = id;
        this.carID = carID;
        this.ownerId = ownerId;
        IsLike = isLike;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCarID() {
        return carID;
    }

    public void setCarID(long carID) {
        this.carID = carID;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getIsLike() {
        return IsLike;
    }

    public void setIsLike(int isLike) {
        IsLike = isLike;
    }
}
