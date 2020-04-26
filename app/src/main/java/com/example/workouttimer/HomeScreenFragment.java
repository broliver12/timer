package com.example.workouttimer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreenFragment extends Fragment implements HomeScreenFragmentInterface{

    private RecyclerView timerRecyclerView;
    private HomeScreenViewmodel viewmodel;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.home_screen_layout, container, false);
        viewmodel = new HomeScreenViewmodel();
        ButterKnife.bind(this, v);
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();

        timerRecyclerView = view.findViewById(R.id.timerRecyclerview);
        timerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        timerRecyclerView.setAdapter(new TimerRecyclerViewAdapter(getContext(), viewmodel.getTimerList(), this));

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
