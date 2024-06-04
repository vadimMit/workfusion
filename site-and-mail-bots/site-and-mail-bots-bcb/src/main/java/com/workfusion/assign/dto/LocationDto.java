package com.workfusion.assign.dto;

import com.google.gson.annotations.SerializedName;

public class LocationDto {
    @SerializedName("locationName")
    private String locationName;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;

    @Override
    public String toString() {
        return "LocationDto{" +
                "locationName='" + locationName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", linkToDirection='" + linkToDirection + '\'' +
                ", id=" + id +
                '}';
    }

    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;
    @SerializedName("linkToDirection")
    private String linkToDirection;
    @SerializedName("id")
    private Integer id;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCity() {
        return city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLinkToDirection() {
        return linkToDirection;
    }

    public void setLinkToDirection(String linkToDirection) {
        this.linkToDirection = linkToDirection;
    }

    public LocationDto(String locationName, String city, String state, String address, String phone, String linkToDirection, Integer id) {
        this.locationName = locationName;
        this.city = city;
        this.state = state;
        this.address = address;
        this.phone = phone;
        this.linkToDirection = linkToDirection;
        this.id = id;
    }

}
