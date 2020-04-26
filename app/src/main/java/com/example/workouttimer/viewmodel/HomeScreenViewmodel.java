package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Timer;

import java.util.ArrayList;

public class HomeScreenViewmodel {

    private ArrayList<Timer> timerList;

    public HomeScreenViewmodel(){

        timerList = new ArrayList<>();

        timerList.add(new Timer("hello"));
        timerList.add(new Timer("helloeee"));
        timerList.add(new Timer("hellorfff"));
        timerList.add(new Timer("helldsdsdso"));


    }


    public ArrayList<Timer> getTimerList(){
        return this.timerList;
    }


}
