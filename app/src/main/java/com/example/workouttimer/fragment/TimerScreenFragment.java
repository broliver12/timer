package com.example.workouttimer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workouttimer.R;
import com.example.workouttimer.activity.MainActivity;
import com.example.workouttimer.model.Timer;
import com.example.workouttimer.viewmodel.TimerScreenViewModelInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerScreenFragment extends Fragment implements TimerScreenFragmentInterface {

    @BindView(R.id.clock_display_text_view)
    TextView clockDisplayTextView;
    @BindView(R.id.duration_value)
    TextView durationValueTextView;
    @BindView(R.id.rest_between_rounds_check_box)
    CheckBox restCheckBox;
    @BindView(R.id.repetitions_number_picker)
    NumberPicker repetitionsNumberPicker;
    @BindView(R.id.quantity_number_picker)
    NumberPicker quantityNumberPicker;

    private Timer timer;
    private TimerScreenViewModelInterface viewModel;

    private final int MAX_REPETITIONS = 99;
    private final int MIN_REPETITIONS = 1;
    private final int MAX_REST = 600;
    private final int MIN_REST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.viewModel = ((MainActivity) getActivity()).getViewModel();
        View v = inflater.inflate(R.layout.timer_screen_layout, container, false);
        ButterKnife.bind(this, v);
        repetitionsNumberPicker.setMaxValue(MAX_REPETITIONS);
        repetitionsNumberPicker.setMinValue(MIN_REPETITIONS);
        quantityNumberPicker.setMaxValue(MAX_REST);
        quantityNumberPicker.setMinValue(MIN_REST);
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timer = viewModel.loadSelectedTimer();
        toolbarSetup(timer.getTitle());
        durationValueTextView.setText(viewModel.getTotalDuration());
        repetitionsNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if(oldVal != newVal){
                viewModel.onRepetitionsChanged(newVal);
                durationValueTextView.setText(viewModel.getTotalDuration());
            }
        });
        quantityNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if(oldVal != newVal){
                viewModel.onQuantityChanged(newVal);
                durationValueTextView.setText(viewModel.getTotalDuration());
            }
        });

    }

    @Override
    public void navigateToHomeScreenFragment() {
        NavHostFragment.findNavController(TimerScreenFragment.this)
                .navigate(R.id.action_timer_to_home);
    }


    private void toolbarSetup(String title){

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
            ((TextView) v.findViewById(R.id.toolbar_title_text_view)).setText(title);
        }
    }
}
