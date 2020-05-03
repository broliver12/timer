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

    public void onSavePressed(String title) {
        if (this.inProgressTimer != null && this.inProgressTimer.getSections().size() > 0) {
            this.inProgressTimer.setTitle(title);
            int totalDurSeconds = 0;
            for (int i=0; i<this.inProgressTimer.getSections().size(); i++){
                this.inProgressTimer.getSections().get(i).setId(i);
                totalDurSeconds += this.inProgressTimer.getSections().get(i).getDuration();
                this.inProgressTimer.getSections().get(i).setEndTimeStamp(totalDurSeconds * 1000);
            }
            this.inProgressTimer.setDuration(totalDurSeconds);
            //set the rest of the timer values here...
            completedTimerPublishSubject.onNext(this.inProgressTimer);
            this.inProgressTimer = null;
        }
    }

    public void onDiscardPressed() {

        this.inProgressTimer = null;
    }

    public Observable<Timer> getTimerPublishSubject() {
        return completedTimerPublishSubject.map(x -> x);
    }

    public Observable<Integer> getStateObservable(){
        return stateObservable.map(x -> x);
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
