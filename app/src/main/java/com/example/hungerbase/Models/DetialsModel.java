package com.example.hungerbase.Models;

public class DetialsModel {
    String name, phoneNo, province, city, address;

    public DetialsModel(String name, String phoneNo, String province, String city, String address) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.province = province;
        this.city = city;
        this.address = address;
    }

    public DetialsModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
