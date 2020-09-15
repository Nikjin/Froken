package com.example.confesso.Model;

public class SharePost {

    private String sharedpostid;
    private String publisherpostid;
    private String publisher;
    private String date;
    private String time;
    private String title;
    private String publishername;
    private String category;
    private String postpublisherid;
    private Boolean isanonymous;

    public SharePost(String sharedpostid, String publisherpostid, String publisher, String date, String time, String title, String publishername, String category,String postpublisherid,Boolean isanonymous) {
        this.sharedpostid = sharedpostid;
        this.publisherpostid = publisherpostid;
        this.publisher = publisher;
        this.date = date;
        this.time = time;
        this.title = title;
        this.publishername = publishername;
        this.category = category;
        this.postpublisherid = postpublisherid;
        this.isanonymous = isanonymous;


    }

    public SharePost() {
    }

    public Boolean getIsanonymous() {
        return isanonymous;
    }

    public void setIsanonymous(Boolean isanonymous) {
        this.isanonymous = isanonymous;
    }

    public String getPostpublisherid() {
        return postpublisherid;
    }

    public void setPostpublisherid(String postpublisherid) {
        this.postpublisherid = postpublisherid;
    }

    public String getSharedpostid() {
        return sharedpostid;
    }

    public void setSharedpostid(String sharedpostid) {
        this.sharedpostid = sharedpostid;
    }

    public String getPublisherpostid() {
        return publisherpostid;
    }

    public void setPublisherpostid(String publisherpostid) {
        this.publisherpostid = publisherpostid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishername() {
        return publishername;
    }

    public void setPublishername(String publishername) {
        this.publishername = publishername;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
