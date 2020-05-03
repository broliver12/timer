package com.example.workouttimer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttimer.R;
import com.example.workouttimer.activity.MainActivity;
import com.example.workouttimer.adapter.TimerRecyclerViewAdapter;
import com.example.workouttimer.model.Timer;
import com.example.workouttimer.viewmodel.HomeScreenViewModelInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class HomeScreenFragment extends Fragment implements HomeScreenFragmentInterface {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private RecyclerView timerRecyclerView;
    private HomeScreenViewModelInterface viewModel;
    private Observable<Timer> newTimerObservable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.viewModel = ((MainActivity) getActivity()).getHomeScreenViewModel();
        View v = inflater.inflate(R.layout.home_screen_layout, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();

        timerRecyclerView = view.findViewById(R.id.timerRecyclerview);
        timerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TimerRecyclerViewAdapter adapter = new TimerRecyclerViewAdapter(getContext(), viewModel.getTimerList(), this);
        timerRecyclerView.setAdapter(adapter);
        newTimerObservable = viewModel.getTimerAdapterObservable();

        newTimerObservable.doOnNext(x -> {
            adapter.addItem(x);
        });

        fab.setOnClickListener(v -> navigateToCreateNewTimerFragment());
    }

    @Override
    public boolean onDeleteClicked(String title) {
        return viewModel.removeTimerFromList(title);
    }

    @Override
    public boolean onSelectClicked(String title) {
        viewModel.selectTimerFromList(title);
        navigateToSelectedTimerFragment();
        return true;
    }

    @Override
    public void navigateToCreateNewTimerFragment() {

        NavController ctr = NavHostFragment.findNavController(HomeScreenFragment.this);

        if (ctr.getCurrentDestination().getId() == R.id.HomeScreenFragment) {
            NavHostFragment.findNavController(HomeScreenFragment.this)
                    .navigate(R.id.action_home_to_create);
        }
    }

    @Override
    public void navigateToSelectedTimerFragment() {

        NavController ctr = NavHostFragment.findNavController(HomeScreenFragment.this);

        if (ctr.getCurrentDestination().getId() == R.id.HomeScreenFragment) {
            NavHostFragment.findNavController(HomeScreenFragment.this)
                    .navigate(R.id.action_home_to_timer);
        }
    }

    private void setupToolbar() {

        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
    }
}
