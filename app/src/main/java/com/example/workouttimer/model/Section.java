package com.example.workouttimer.model;

public class Section {

    private String type;
    private String title = "";
    private int duration;
    private long endTimeStamp;
    private int id;

    public Section(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public int getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getEndTimeStamp(){ return endTimeStamp;}

    public void setEndTimeStamp(long timeStamp){this.endTimeStamp = timeStamp;}
}
