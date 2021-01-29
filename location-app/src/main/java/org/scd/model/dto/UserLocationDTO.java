package org.scd.model.dto;

import java.util.Date;

public class UserLocationDTO {
    private String latitude;
    private String longitude;
    private String email;
    private Date date;

    public UserLocationDTO() {

    }

    public UserLocationDTO(String latitude, String longitude, String email) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.date = new Date();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String lattitude) {
        this.latitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
