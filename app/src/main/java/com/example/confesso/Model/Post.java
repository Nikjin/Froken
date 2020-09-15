package com.example.confesso.Model;

public class Post {

    private String postid;
    private String postimage;
    private String description;
    private String publisher;
    private String date;
    private String time;
    private String title;
    private String publishername;


    private String category;


    public Post(String postid, String postimage, String description, String publisher, String date, String time, String title, String category,String publishername) {
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.publisher = publisher;
        this.date = date;
        this.time = time;
        this.title = title;
        this.category = category;
        this.publishername = publishername;

    }

    public Post() {
    }

    public String getPublishername() {
        return publishername;
    }

    public void setPublishername(String publishername) {
        this.publishername = publishername;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
