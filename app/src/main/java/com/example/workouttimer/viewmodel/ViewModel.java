package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Section;
import com.example.workouttimer.model.Timer;
import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class ViewModel implements HomeScreenViewModelInterface{

    private ArrayList<Timer> timerList;

    private TimerScreenViewModel timerScreenViewModel;
    private CreateNewTimerScreenViewModel createNewTimerScreenViewModel;

    private Observable<Timer> newTimerObs;

    public ViewModel() {
        timerScreenViewModel = new TimerScreenViewModel();
        createNewTimerScreenViewModel = new CreateNewTimerScreenViewModel();

        newTimerObs = createNewTimerScreenViewModel.getTimerPublishSubject();

        newTimerObs.doOnNext(x -> timerList.add(x)).map(x -> x).subscribe();

        timerList = new ArrayList<>();

        Timer x = new Timer("test");
        x.setDuration(5);
        ArrayList<Section> l = new ArrayList<>();
        l.add(new Section("rest"));
        l.add(new Section("work"));
        l.add(new Section("rest"));
        x.setSections(l);
        timerList.add(x);

    }

    public Observable<Timer> getTimerAdapterObservable(){
        return createNewTimerScreenViewModel.getTimerPublishSubject();
    }

    public TimerScreenViewModel getTimerScreenViewModel() {
        return timerScreenViewModel;
    }

    public CreateNewTimerScreenViewModel getCreateNewTimerScreenViewModel(){
        return createNewTimerScreenViewModel;
    }

    public boolean removeTimerFromList(String title) {
        for (Timer t : timerList) {
            if (t.getTitle().equals(title)) {
                timerList.remove(t);
                return true;
            }
        }
        return false;
    }

    public boolean selectTimerFromList(String title) {
        for (Timer t : timerList) {
            if (t.getTitle().equals(title)) {
                timerScreenViewModel.loadSelectedTimer(t);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Timer> getTimerList() {
        return this.timerList;
    }


    public boolean addTimerToList(Timer timer) {
        for (Timer t : timerList) {
            if (t.getTitle().equals(timer.getTitle())) {
                return false;
            }
        }

        timerList.add(timer);

        return true;
    }



}
