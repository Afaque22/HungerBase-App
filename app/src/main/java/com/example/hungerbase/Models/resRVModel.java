package com.example.hungerbase.Models;

public class resRVModel {
    public String getresName() {
        return resName;
    }

    public void setresName(String resName) {
        this.resName = resName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public resRVModel(String resName, String address) {
        this.resName = resName;
        this.address = address;
    }

    String resName, address;

    public resRVModel() {
    }
}
