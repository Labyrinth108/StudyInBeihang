package com.database;

/**
 * Created by SONY on 2016/2/19.
 */
public class Classroom {
    private int id;
    private String location;
    private String room;
    private int idnum;
    private String percent;
    public Classroom(int id,String location, String room, int idnum,String percent) {
        this.id=id;
        this.location = location;
        this.room = room;
        this.idnum = idnum;
        this.percent=percent;
    }

    public Classroom() {

    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getRoom() {
        return room;
    }

    public int getIdnum() {
        return idnum;
    }

    public String getPercent() {
        return percent;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setIdnum(int idnum) { this.idnum = idnum;    }

    public void setId(int id) {  this.id = id;    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
