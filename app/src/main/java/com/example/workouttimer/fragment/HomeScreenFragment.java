package com.example.workouttimer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttimer.R;
import com.example.workouttimer.activity.MainActivity;
import com.example.workouttimer.adapter.TimerRecyclerViewAdapter;
import com.example.workouttimer.viewmodel.HomeScreenViewModelInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreenFragment extends Fragment implements HomeScreenFragmentInterface {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private RecyclerView timerRecyclerView;
    private HomeScreenViewModelInterface viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.viewModel = ((MainActivity) getActivity()).getViewModel();
        View v = inflater.inflate(R.layout.home_screen_layout, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();

        timerRecyclerView = view.findViewById(R.id.timerRecyclerview);
        timerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        timerRecyclerView.setAdapter(new TimerRecyclerViewAdapter(getContext(), viewModel.getTimerList(), this));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToCreateNewTimerFragment();
            }
        });
    }

    @Override
    public void navigateToCreateNewTimerFragment() {
        NavHostFragment.findNavController(HomeScreenFragment.this)
                .navigate(R.id.action_home_to_create);
    }

    @Override
    public void navigateToSelectedTimerFragment() {
        NavHostFragment.findNavController(HomeScreenFragment.this)
                .navigate(R.id.action_home_to_timer);
    }

    private void setupToolbar(){

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
    }
}
