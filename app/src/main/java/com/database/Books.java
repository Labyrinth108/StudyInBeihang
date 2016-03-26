package com.database;

/**
 * Created by SONY on 2016/3/26.
 */
public class Books {
    private String person;
    private String code;
    private String name;
    private String author;

    public Books(String person,String code, String name, String author) {
        this.person=person;
        this.code = code;
        this.name = name;
        this.author = author;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
