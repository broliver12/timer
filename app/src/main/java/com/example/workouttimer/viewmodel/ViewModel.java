package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Timer;

import java.util.ArrayList;

public class ViewModel implements HomeScreenViewModelInterface, TimerScreenViewModelInterface, CreateNewTimerViewModelInterface {

    private ArrayList<Timer> timerList;
    private Timer currentlySelectedTimer;
    private int status;

    public ViewModel(){

        timerList = new ArrayList<>();

        timerList.add(new Timer("hello"));
        timerList.add(new Timer("helloeee"));
        timerList.add(new Timer("hellorfff"));
        timerList.add(new Timer("helldsdsdso"));

        status = 14;

    }

    public ArrayList<Timer> getTimerList(){
        return this.timerList;
    }

    public boolean addTimerToList(Timer timer){
        for (Timer t : timerList){
            if(t.getTitle().equals(timer.getTitle())){
                return false;
            }
        }

        timerList.add(timer);
        return true;
    }

    public boolean selectTimerFromList(String title){
        for (Timer t : timerList){
            if(t.getTitle().equals(title)){
                timerList.remove(t);
                return true;
            }
        }
        return false;
    }

    public boolean removeTimerFromList(String title){
        for (Timer t : timerList){
            if(t.getTitle().equals(title)){
                currentlySelectedTimer = t;
                return true;
            }
        }
        return false;
    }

    public int getStatus(){
        return status;
    }
}
