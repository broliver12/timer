package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Section;
import com.example.workouttimer.model.Timer;
import com.example.workouttimer.model.TimerClock;

import java.sql.Time;
import java.time.Clock;
import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;

public class ViewModel implements HomeScreenViewModelInterface, TimerScreenViewModelInterface, CreateNewTimerViewModelInterface {

    private ArrayList<Timer> timerList;
    private Timer currentlySelectedTimer;
    private TimerClock clock;
    private int status;

    private int repetitions;
    private int restDuration;
    private int totalDuration;

    public ViewModel() {

        timerList = new ArrayList<>();

        Timer x = new Timer("test");
        x.setDuration(5);
        ArrayList<Section> l = new ArrayList<>();
        l.add(new Section("rest"));
        l.add(new Section("work"));
        l.add(new Section("rest"));
        x.setSections(l);
        timerList.add(x);
        status = 14;

        this.clock = new TimerClock();

    }

    public ArrayList<Timer> getTimerList() {
        return this.timerList;
    }

    public boolean addTimerToList(Timer timer) {
        for (Timer t : timerList) {
            if (t.getTitle().equals(timer.getTitle())) {
                return false;
            }
        }

        timerList.add(timer);
        return true;
    }

    public boolean removeTimerFromList(String title) {
        for (Timer t : timerList) {
            if (t.getTitle().equals(title)) {
                timerList.remove(t);
                return true;
            }
        }
        return false;
    }

    public boolean selectTimerFromList(String title) {
        for (Timer t : timerList) {
            if (t.getTitle().equals(title)) {
                currentlySelectedTimer = t;
                clock.loadTimer(t);
                return true;
            }
        }
        return false;
    }

    public void loadSelectedTimer() {
        repetitions = 1;
        restDuration = 0;
        totalDuration = calculateTotalDurationInSeconds();
    }

    private int calculateTotalDurationInSeconds() {
        return repetitions * (currentlySelectedTimer.getDuration() + restDuration) - restDuration;
    }

    public void onQuantityChanged(int newVal) {
        restDuration = newVal;
        totalDuration = calculateTotalDurationInSeconds();
    }

    public void onRepetitionsChanged(int newVal) {
        repetitions = newVal;
        totalDuration = calculateTotalDurationInSeconds();
    }

    public String getTotalDurationString() {
        int sec = totalDuration % 60;
        int min = (totalDuration / 60) % 60;
        int hr = (totalDuration / 3600);

        String secStr = sec < 10 ? "0" + sec : Integer.toString(sec);
        String minStr = min < 10 ? "0" + min : Integer.toString(min);
        String hrStr = hr < 10 ? "0" + hr : Integer.toString(hr);

        String str = hrStr + ":" + minStr + ":" + secStr;
        return str;
    }

    public void onPlayButtonPressed() {
        this.clock.start();
    }

    public void onPauseButtonPressed() {
        this.clock.pause();
    }

    public void onStopButtonPressed() {
        this.clock.stop();
    }

    public void onResetButtonPressed() {
        this.clock.reset();
    }

    public Observable<Integer> getStateChangeObservable() {
        return this.clock.getStateObservable();
    }

    public void onTotalDurationChanged(boolean hasRest, int restDuration, int repetitions) {
        this.clock.updateTotalDuration(hasRest, restDuration, repetitions);
    }

    public Observable<String> getClockStringObservable() {
        return clock.getTimeObservable().map(timeInMillis -> getFormattedClockString(timeInMillis));
    }

    public String getFinishedClockString(){
        return this.getFormattedClockString(Long.valueOf(calculateTotalDurationInSeconds()) * 1000);
    }

    private String getFormattedClockString(Long timeInMillis){
        int hundredth = (timeInMillis.intValue() / 10) % 100;
        int sec = (timeInMillis.intValue() / 1000) % 60;
        int min = (timeInMillis.intValue() / 60000) % 60;
        int hr = (timeInMillis.intValue() / 3600000);

        String hunStr = hundredth < 10 ? "0" + hundredth : Integer.toString(hundredth);
        String secStr = sec < 10 ? "0" + sec : Integer.toString(sec);
        String minStr = min < 10 ? "0" + min : Integer.toString(min);
        String hrStr = hr < 10 ? "0" + hr : Integer.toString(hr);
        String str = hrStr + ":" + minStr + ":" + secStr + "." + hunStr;

        if (hr == 0) {
            str = minStr + ":" + secStr + "." + hunStr;
        }

        return str;
    }

    public String getTimerTitle(){
        return this.currentlySelectedTimer.getTitle();
    }
}
