package com.example.confesso.Model;

public class Comment {

    private String comment;
    private String publisher;
    private String commentid;
    private String postid;

    private String time;
    private String upvotes;

    public Comment(String comment, String publisher, String commentid, String postid, String time, String upvotes) {
        this.comment = comment;
        this.publisher = publisher;
        this.commentid = commentid;
        this.postid = postid;
        this.time = time;
        this.upvotes = upvotes;
    }

    public Comment() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(String upvotes) {
        this.upvotes = upvotes;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
}
