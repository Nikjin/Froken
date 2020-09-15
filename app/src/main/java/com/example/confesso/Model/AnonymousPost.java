package com.example.confesso.Model;

public class AnonymousPost {

    String category;
    String date;
    String time;
    String postid;
    String title;
    String description;
    String postimage;
    String publisher;
    String publishername;
    int picpos;



    public AnonymousPost(String category, String date, String time, String postid, String title, String description, String postimage, String publisher,String publishername,int picpos) {
        this.category = category;
        this.date = date;
        this.time = time;
        this.postid = postid;
        this.title = title;
        this.description = description;
        this.postimage = postimage;
        this.publisher = publisher;
        this.publishername = publishername;
        this.picpos=picpos;

    }

    public AnonymousPost() {
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

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPicpos() {
        return picpos;
    }

    public void setPicpos(int picpos) {
        this.picpos = picpos;
    }
}
