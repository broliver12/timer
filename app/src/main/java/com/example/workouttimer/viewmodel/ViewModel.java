package com.example.workouttimer.viewmodel;

import com.example.workouttimer.model.Section;
import com.example.workouttimer.model.Timer;
import com.example.workouttimer.model.TimerClock;

import java.sql.Time;
import java.time.Clock;
import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;

public class ViewModel implements HomeScreenViewModelInterface, CreateNewTimerViewModelInterface {

    private ArrayList<Timer> timerList;

    private TimerScreenViewModel timerScreenViewModel;

    public ViewModel() {
        timerScreenViewModel = new TimerScreenViewModel();

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

    public TimerScreenViewModel getTimerScreenViewModel() {
        return timerScreenViewModel;
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

}
