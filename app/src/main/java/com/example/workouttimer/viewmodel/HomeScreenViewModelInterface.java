package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Timer;

import java.util.ArrayList;

public interface HomeScreenViewModelInterface {

    boolean selectTimerFromList(String title);
    boolean removeTimerFromList(String title);
    ArrayList<Timer> getTimerList();
}
