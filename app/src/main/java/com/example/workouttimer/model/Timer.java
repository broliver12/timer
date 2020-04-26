package com.example.workouttimer.model;

import com.example.workouttimer.model.Section;

import java.util.ArrayList;

public class Timer {

    private String title;
    private int duration;
    private ArrayList<Section> sections;

    public Timer(String title){
        this.title = title;
        this.duration = 0;
        this.sections = new ArrayList<>();
    }

    //for later when loading from db
    public Timer(String title, ArrayList<Section> sections){
        this.title = title;
        this.duration = 0;
        this.sections = sections;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }
}
