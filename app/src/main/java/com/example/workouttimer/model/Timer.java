package com.example.workouttimer.model;

import com.example.workouttimer.model.Section;

import java.util.ArrayList;

public class Timer {

    private String title;
    private int duration;
    private int totalDuration;
    private int repetitions;
    private int restDuration;
    private boolean hasRest;
    private ArrayList<Section> sections;

    public Timer(String title){
        this.title = title;
        this.duration = 0;
        this.repetitions = 0;
        this.restDuration = 0;
        this.hasRest = false;
        this.sections = new ArrayList<>();
    }

    //for later when loading from db
    public Timer(String title, ArrayList<Section> sections){
        this.title = title;
        this.duration = 0;
        this.repetitions = 0;
        this.restDuration = 0;
        this.hasRest = false;
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

    public void setHasRest(boolean hasRest){
        this.hasRest = hasRest;
    }

    public boolean getHasRest(){
        return this.hasRest;
    }

    public void setRepetitions(int repetitions){
        this.repetitions = repetitions;
    }

    public int getRepetitions(){
        return this.repetitions;
    }

    public void setRestDuration(int restDuration){
        this.restDuration = restDuration;
    }

    public int getRestDuration(){
        return this.restDuration;
    }

    public long getTotalDuration() {
        return hasRest ? this.repetitions * (this.duration + this.restDuration) - this.restDuration : this.repetitions * (this.duration);
    }
}
