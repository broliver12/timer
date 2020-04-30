package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Section;
import com.example.workouttimer.model.Timer;
import com.example.workouttimer.model.TimerClock;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;

public class TimerScreenViewModel implements TimerScreenViewModelInterface {

    private ArrayList<Timer> timerList;
    private Timer currentlySelectedTimer;
    private TimerClock clock;
    private int status;

    private int repetitions;
    private int restDuration;
    private int totalDuration;

    public TimerScreenViewModel () {
        this.timerList = new ArrayList<>();

        Timer x = new Timer("test");
        x.setDuration(5);
        ArrayList<Section> l = new ArrayList<>();
        l.add(new Section("rest"));
        l.add(new Section("work"));
        l.add(new Section("rest"));
        x.setSections(l);
        this.timerList.add(x);
        this.status = 14;

        this.clock = new TimerClock();
    }

    public void loadSelectedTimer(Timer t) {
        this.currentlySelectedTimer = t;
        this.clock.loadTimer(t);
        this.repetitions = 1;
        this.restDuration = 0;
        this.totalDuration = calculateTotalDurationInSeconds();
    }

    private int calculateTotalDurationInSeconds() {
        return repetitions * (this.currentlySelectedTimer.getDuration() + this.restDuration) - this.restDuration;
    }

    public void onQuantityChanged(int newVal) {
        this.restDuration = newVal;
        this.totalDuration = calculateTotalDurationInSeconds();
    }

    public void onRepetitionsChanged(int newVal) {
        this.repetitions = newVal;
        this.totalDuration = this.calculateTotalDurationInSeconds();
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
