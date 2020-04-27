package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Timer;

public interface TimerScreenViewModelInterface {

    int getStatus();
    Timer loadSelectedTimer();
    void onRepetitionsChanged(int newVal);
    void onQuantityChanged(int newVal);
    String getTotalDuration();
}
