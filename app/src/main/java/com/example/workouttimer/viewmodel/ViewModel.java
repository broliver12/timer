package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Section;
import com.example.workouttimer.model.Timer;

import java.sql.Time;
import java.util.ArrayList;

public class ViewModel implements HomeScreenViewModelInterface, TimerScreenViewModelInterface, CreateNewTimerViewModelInterface {

    private ArrayList<Timer> timerList;
    private Timer currentlySelectedTimer;
    private int status;

    private int repetitions;
    private int restDuration;
    private int totalDuration;

    public ViewModel(){

        timerList = new ArrayList<>();

        Timer x = new Timer("test");
        x.setDuration(20);
        x.setSections(new ArrayList<>());
        timerList.add(x);
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

    public boolean removeTimerFromList(String title){
        for (Timer t : timerList){
            if(t.getTitle().equals(title)){
                timerList.remove(t);
                return true;
            }
        }
        return false;
    }

    public boolean selectTimerFromList(String title){
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

    public Timer loadSelectedTimer() {
        repetitions = 1;
        restDuration = 0;
        totalDuration = calculateTotalDurationInSeconds();
        return currentlySelectedTimer;}

    private int calculateTotalDurationInSeconds(){
        return repetitions * (currentlySelectedTimer.getDuration() + restDuration) - restDuration;
    }

    public void onQuantityChanged(int newVal){
        restDuration = newVal;
        totalDuration = calculateTotalDurationInSeconds();
    }

    public void onRepetitionsChanged(int newVal){
        repetitions = newVal;
        totalDuration = calculateTotalDurationInSeconds();
    }

    public String getTotalDuration(){
        int sec = totalDuration % 60;
        int min = (totalDuration / 60) % 60;
        int hr = (totalDuration / 3600);

        String secStr = sec < 10 ? "0" + sec : Integer.toString(sec);
        String minStr = min < 10 ? "0" + min : Integer.toString(min);
        String hrStr = hr < 10 ? "0" + hr : Integer.toString(hr);

        String  str = hrStr + ":" + minStr + ":" + secStr;
        return str;
    }
}
