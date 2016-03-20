package com.database;

/**
 * Created by SONY on 2016/3/19.
 */
public class UserRatio {
    private String location;
    private String density;

    public UserRatio(String location, String density) {
        this.location = location;
        this.density = density;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getDensity() {
        return density;
    }
}
