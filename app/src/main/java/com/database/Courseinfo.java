package com.database;

/**
 * Created by SONY on 2016/3/10.
 */
public class Courseinfo {
    private int week;//周几
    private int startnum;//起始第几节
    private int endnum;//结束第几节
    private String room;

    public Courseinfo(String room, int week, int startnum, int endnum) {
        this.room = room;
        this.week = week;
        this.startnum = startnum;
        this.endnum = endnum;
    }
    public Courseinfo(){

    }
    public int getWeek() {
        return week;
    }

    public String getRoom() {
        return room;
    }

    public int getStartnum() {
        return startnum;
    }

    public int getEndnum() {
        return endnum;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setStartnum(int startnum) {
        this.startnum = startnum;
    }

    public void setEndnum(int endnum) {
        this.endnum = endnum;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
