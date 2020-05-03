package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Timer;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;

public interface HomeScreenViewModelInterface {

    ArrayList<Timer> getTimerList();
    boolean selectTimerFromList(String title);
    boolean removeTimerFromList(String title);
    Observable<Timer> getTimerAdapterObservable();

}
