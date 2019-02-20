package com.example.ankur.caronsale.Model;

public class Favorite {

    private long id;
    private long carID;
    private String ownerId;
    private int IsFavorite;

    public int getIsFavorite() {
        return IsFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        IsFavorite = isFavorite;
    }

    public Favorite() {
    }

    public Favorite(long id, long carID, String ownerId) {
        this.id = id;
        this.carID = carID;
        this.ownerId = ownerId;
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
}
