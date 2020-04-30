package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Section;
import com.example.workouttimer.model.Timer;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class CreateNewTimerScreenViewModel implements CreateNewTimerScreenViewModelInterface {

    private Timer inProgressTimer;
    private PublishSubject<Timer> completedTimerPublishSubject = PublishSubject.create();

    //state
    //0 - not created or no sections
    //1 - creating a section
    //2 - has sections

    private int state;
    private PublishSubject<Integer> stateObservable = PublishSubject.create();

    public CreateNewTimerScreenViewModel() {

    }

    public void onAddSectionPressed() {
        if (this.inProgressTimer == null) {
            this.inProgressTimer = new Timer("");
        }
        //update state to
        stateChange(1);
    }

    public void onAddPressed(int duration, String label, String type) {
        Section s = new Section(duration, label, type);
        inProgressTimer.addSection(s);
        stateChange(2);
    }

    public void onCancelPressed() {
        if (this.inProgressTimer.getSections().size() > 0) {
            stateChange(2);
        } else {
            stateChange(0);
        }
    }

    public void onSavePressed() {
        if (this.inProgressTimer != null && this.inProgressTimer.getSections().size() > 0) {
            completedTimerPublishSubject.onNext(this.inProgressTimer);
            //leave the screen
        }
    }

    public void onDiscardPressed() {
        //leave and don't save
    }

    public Observable<Timer> getTimerPublishSubject() {
        return completedTimerPublishSubject.map(x -> x);
    }

    public Observable<Integer> getStateObservable(){
        return stateObservable.map(x -> x);
    }

    @Override
    public boolean addTimerToList(Timer timer) {
        return false;
    }

    private boolean stateChange(int desiredState) {

        boolean legalStateChange = false;
        if (desiredState != this.state) {

            switch (this.state) {
                case 0:
                    legalStateChange = (desiredState == 1);
                    break;
                case 1:
                    legalStateChange = true;
                    break;
                case 2:
                    legalStateChange = (desiredState == 1);
                    break;
//                case 3:
//                case 4:
//                    legalStateChange = (desiredState == 0);
//                    break;
                default:
            }
        }
        if (legalStateChange) {
            this.state = desiredState;
            this.stateObservable.onNext(this.state);
        }
        return legalStateChange;
    }
}
