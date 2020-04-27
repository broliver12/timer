package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Timer;

import io.reactivex.rxjava3.core.Observable;

public interface TimerScreenViewModelInterface {

    int getStatus();

    Timer loadSelectedTimer();

    void onRepetitionsChanged(int newVal);

    void onQuantityChanged(int newVal);

    String getTotalDuration();

    void onPlayButtonPressed();

    void onPauseButtonPressed();

    void onStopButtonPressed();

    Observable<String> getClockStringObservable();
}
