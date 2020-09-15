package com.example.confesso.Model;

public class AnonymousComment {

    private String comment;
    private String publisher;
    private String commentid;
    private String postid;

    private String time;
    private String upvotes;


    public AnonymousComment(String comment, String publisher, String commentid, String postid, String time, String upvotes) {
        this.comment = comment;
        this.publisher = publisher;
        this.commentid = commentid;
        this.postid = postid;
        this.time = time;
        this.upvotes = upvotes;
    }

    public AnonymousComment() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(String upvotes) {
        this.upvotes = upvotes;
    }
}
