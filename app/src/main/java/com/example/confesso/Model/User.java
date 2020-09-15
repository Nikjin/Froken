package com.example.confesso.Model;

public class User {
    private String id;
    private String username;
    private String fullname;
    private String imageurl;
    private String coverurl;
    private String phone;
    private String mail;
    private String bio;
    private String gender;
    private String anonymous;
    private String dob;
    private String country;

    public User(String id, String username, String fullname, String imageurl, String coverurl, String phone, String mail, String bio, String gender, String anonymous, String dob, String country) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.imageurl = imageurl;
        this.coverurl = coverurl;
        this.phone = phone;
        this.mail = mail;
        this.bio = bio;
        this.gender = gender;
        this.anonymous = anonymous;
        this.dob = dob;
        this.country = country;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}

