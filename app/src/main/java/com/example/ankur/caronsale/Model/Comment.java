package com.example.ankur.caronsale.Model;

public class Comment {
    private long id;
    private long carID;
    private String ownerId;
    private String comment_userID;
    private String commentText;

    public Comment() {
    }

    public Comment(long id, long carID, String ownerId, String comment_userID, String commentText) {
        this.id = id;
        this.carID = carID;
        this.ownerId = ownerId;
        this.comment_userID = comment_userID;
        this.commentText = commentText;
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

    public String getComment_userID() {
        return comment_userID;
    }

    public void setComment_userID(String comment_userID) {
        this.comment_userID = comment_userID;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
