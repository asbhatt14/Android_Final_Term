package com.example.ankur.caronsale.Model;

public class PhotoBook {
    private long id;
    private long carID;
    private String PhotoPath;

    public PhotoBook(long id, long carID, String photoPath) {
        this.id = id;
        this.carID = carID;
        PhotoPath = photoPath;
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

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }
}
