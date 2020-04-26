package com.example.workouttimer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
