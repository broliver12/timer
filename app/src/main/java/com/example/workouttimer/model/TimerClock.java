package com.example.workouttimer.model;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class TimerClock {
    private Timer timer;
    private Section currentSection;
    private long mostRecentStartTime;
    private int clockState;
    private long currentElapsedTime;
    private long totalElapsedTime;
    private long mostRecentUpdate;
    private Observable timeObservable;

    //state
    //0 - stopped (reset)
    //1 - running
    //2 - paused

    public TimerClock() {
        this.unloadTimer();
    }

    private void unloadTimer(){
        this.timer = null;
        this.currentSection = null;
        this.clockState = 0;

        this.mostRecentStartTime = 0;
        this.mostRecentUpdate = 0;
        this.currentElapsedTime = 0;
        this.totalElapsedTime = 0;
    }

    public void loadTimer(Timer timer) {
        if(this.timer != timer){
            unloadTimer();
            this.timer = timer;
            this.currentSection = timer.getSections().get(0);
        }
    }

    public Observable<Long> getTimeObservable(){
        return timeObservable.interval(0, 10, TimeUnit.MILLISECONDS)
                .filter(x -> clockState == 1)
                .doOnNext(x -> this.currentElapsedTime += System.currentTimeMillis() - this.mostRecentStartTime)
                .map(x -> getAndUpdateTotalElapsedTime());
    }

    private boolean stateChange(int desiredState) {
        if (desiredState != this.clockState) {
            //if you want to pause, must be playing
            //can always play
            //can always stop
            if ((desiredState == 2 && this.clockState == 1) || desiredState == 1 || desiredState == 0) {
                this.clockState = desiredState;
                return true;
            }
        }
        //if states are the same do nothing
        return false;
    }

    public void start() {
        if (stateChange(1)) {
            this.mostRecentStartTime = System.currentTimeMillis();
            this.mostRecentUpdate = mostRecentStartTime;
            this.currentSection = findCurrentSection();

        }
    }

    public void pause() {
        if (stateChange(2)) {
            this.currentElapsedTime = System.currentTimeMillis() - this.mostRecentStartTime;
        }
    }

    public void stop() {
        if (stateChange(0)) {
            this.mostRecentStartTime = 0;
            this.clockState = 0;
            this.currentElapsedTime = 0;
            this.totalElapsedTime = 0;
        }
    }

    private Section findCurrentSection() {
        for (Section s : this.timer.getSections()) {
            if (s.getEndTimeStamp() > totalElapsedTime) {
                return s;
            }
        }
        return this.timer.getSections().get(0);
    }

    private long getAndUpdateTotalElapsedTime() {
        long trueTime = totalElapsedTime + (System.currentTimeMillis() - mostRecentUpdate);
        mostRecentUpdate = System.currentTimeMillis();
        totalElapsedTime = trueTime;
        return trueTime;
    }
}
