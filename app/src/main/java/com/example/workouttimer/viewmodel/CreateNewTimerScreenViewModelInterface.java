package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Timer;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public interface CreateNewTimerScreenViewModelInterface {

    void onAddSectionPressed();

    void onSavePressed(String title);
    void onAddPressed(int duration, String label, String type);
    void onCancelPressed();
    void onDiscardPressed();
    Observable<Integer> getStateObservable();
    Observable<Timer> getTimerPublishSubject();

}
