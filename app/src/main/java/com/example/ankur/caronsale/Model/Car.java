package com.example.ankur.caronsale.Model;

import android.os.Parcelable;

import java.io.Serializable;

public class Car implements Serializable {
    private long carId;
    private String modelName;
    private String kmsDriven;
    private String price;
    private String mfgYear;
    private String ownerId;
    private String carPhoto;
    private String ownerType;
    private String carCapacity;
    private int IsLiked;

    public int getIsLiked() {
        return IsLiked;
    }

    public void setIsLiked(int isLiked) {
        IsLiked = isLiked;
    }

    public String getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(String carCapacity) {
        this.carCapacity = carCapacity;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCarPhoto() {
        return carPhoto;
    }

    public void setCarPhoto(String carPhoto) {
        this.carPhoto = carPhoto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private  String description;

    public Car(){

    }

    public Car(long carId, String modelName, String kmsDriven, String price, String mfgYear, String ownerType, String description, String carCapacity) {
        this.carId = carId;
        this.modelName = modelName;
        this.kmsDriven = kmsDriven;
        this.price = price;
        this.mfgYear = mfgYear;
        this.ownerType = ownerType;
        this.description = description;
        this.carCapacity = carCapacity;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getKmsDriven() {
        return kmsDriven;
    }

    public void setKmsDriven(String kmsDriven) {
        this.kmsDriven = kmsDriven;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMfgYear() {
        return mfgYear;
    }

    public void setMfgYear(String mfgYear) {
        this.mfgYear = mfgYear;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }
}
