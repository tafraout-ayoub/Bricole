package com.example.bricole;

public class User {
    private String email, username, role, password,city, profileImg, gender, address, postalCode;
    private int number ;

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public User() {
    }

    public User(String email, String username, String role) {
        this.email = email;
        this.role = role;
        this.username = username;
    }
    public User(String username,String email,int number,String city, String profileImg, String role, String gender,String address, String postalCode) {
        this.username = username;
        this.email = email;
        this.number = number;
        this.city = city;
        this.profileImg = profileImg;
        this.role = role;
        this.gender = gender;
        this.address = address;
        this.postalCode = postalCode;
    }


    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String password) {
        this.role = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
