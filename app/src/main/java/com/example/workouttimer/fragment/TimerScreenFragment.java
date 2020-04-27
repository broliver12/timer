package com.example.workouttimer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workouttimer.R;
import com.example.workouttimer.activity.MainActivity;
import com.example.workouttimer.viewmodel.TimerScreenViewModelInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerScreenFragment extends Fragment implements TimerScreenFragmentInterface {

    @BindView(R.id.clock_display_text_view)
    TextView clockDisplayTextView;

    private TimerScreenViewModelInterface viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.viewModel = ((MainActivity) getActivity()).getViewModel();
        View v = inflater.inflate(R.layout.timer_screen_layout, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarSetup();


    }

    @Override
    public void navigateToHomeScreenFragment() {
        NavHostFragment.findNavController(TimerScreenFragment.this)
                .navigate(R.id.action_timer_to_home);
    }


    private void toolbarSetup(){

        final Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.timer_screen_toolbar, null);

        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);

            actionBar.setCustomView(v);
            toolbar.setNavigationIcon(null);

            v.findViewById(R.id.back).setOnClickListener(v_ -> getActivity().onBackPressed());
        }
    }
}
