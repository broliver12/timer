package com.example.workouttimer.fragment;

public interface HomeScreenFragmentInterface {

    void navigateToSelectedTimerFragment();
    void navigateToCreateNewTimerFragment();
    boolean onDeleteClicked(String title);
    boolean onSelectClicked(String title);

}
