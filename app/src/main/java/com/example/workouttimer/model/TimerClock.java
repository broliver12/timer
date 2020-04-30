package com.example.workouttimer.model;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class TimerClock {
    private Timer timer;
    private Section currentSection;

    //state
    //0 - unstarted
    //1 - running
    //2 - paused
    //3 - stopped
    //4 - finished
    private int clockState;
    private PublishSubject<Integer> stateObservable = PublishSubject.create();

    private long mostRecentStartTime;
    private long currentElapsedTime;
    private long totalElapsedTime;
    private long mostRecentUpdate;

    public TimerClock() {
        this.unloadTimer();
    }

    private void unloadTimer() {
        this.timer = null;
        this.currentSection = null;
        resetClockValues();
    }

    public void loadTimer(Timer timer) {
        if (this.timer != timer) {
            unloadTimer();
            this.timer = timer;
            this.currentSection = timer.getSections().get(0);
        }
    }

    public Observable<Long> getTimeObservable() {
        return Observable.interval(0, 10, TimeUnit.MILLISECONDS)
                .filter(x -> this.clockState == 1)
                .doOnNext(x -> this.currentElapsedTime += System.currentTimeMillis() - this.mostRecentStartTime)
                .map(x -> getAndUpdateTotalElapsedTime())
                .doOnNext(x -> {
                            if (x >= this.currentSection.getEndTimeStamp()) {
                                //perform required sound action or whatever, publishsubject onnext??
                                if (x >= this.timer.getTotalDuration() * 1000) {
                                    this.finish();
                                } else {
                                    this.currentSection = this.timer.getSections().get(this.currentSection.getId() + 1);
                                }
                            }
                        }
                );
    }

    public Observable<Integer> getStateObservable() {
        return stateObservable.map(x -> x);
    }

    private boolean stateChange(int desiredState) {

        boolean legalStateChange = false;
        if (desiredState != this.clockState) {

            switch (this.clockState) {
                case 0:
                    legalStateChange = (desiredState == 1);
                    break;
                case 1:
                    legalStateChange = (desiredState != 0);
                    break;
                case 2:
                    legalStateChange = (desiredState == 1 || desiredState == 3);
                    break;
                case 3:
                case 4:
                    legalStateChange = (desiredState == 0);
                    break;
                default:
            }
        }
        if(legalStateChange){
            this.clockState = desiredState;
            this.stateObservable.onNext(this.clockState);
        }
        return legalStateChange;
    }

    private void resetClockValues(){
        if(stateChange(0)) {
            this.mostRecentStartTime = 0;
            this.mostRecentUpdate = 0;
            this.currentElapsedTime = 0;
            this.totalElapsedTime = 0;
        }
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
        if (stateChange(3)) {
            resetClockValues();
        }
    }

    private void finish() {

        if (stateChange(4)) {
//            resetClockValues();
        }
    }

    public void reset() {
        resetClockValues();
    }

    private Section findCurrentSection() {
        for (Section s : this.timer.getSections()) {
            if (s.getEndTimeStamp() > totalElapsedTime) {
                return s;
            }
        }
        return this.timer.getSections().get(0);
    }

    public void updateTotalDuration(boolean hasRest, int restDuration, int repetitions){
        if(this.timer != null){
            this.timer.setHasRest(hasRest);
            this.timer.setRestDuration(restDuration);
            this.timer.setRepetitions(repetitions);
        }
    }

    private long getAndUpdateTotalElapsedTime() {
        long trueTime = totalElapsedTime + (System.currentTimeMillis() - mostRecentUpdate);
        mostRecentUpdate = System.currentTimeMillis();
        totalElapsedTime = trueTime;
        return trueTime;
    }
}
