package com.example.workouttimer;

import android.view.View;

import java.util.ArrayList;

public class ViewModel implements HomeScreenViewModelInterface, CreateNewTimerViewModelInterface{

    private ArrayList<Timer> timerList;
    private Timer currentlySelectedTimer;

    public ViewModel(){

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
}
