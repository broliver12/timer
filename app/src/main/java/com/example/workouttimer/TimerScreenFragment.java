package com.example.workouttimer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerScreenFragment extends Fragment implements TimerScreenFragmentInterface {

    @BindView(R.id.button_second)
    Button secondButton;
    @BindView(R.id.clock_display_text_view)
    TextView clockDisplayTextView;
    @BindView(R.id.timer_screen_toolbar)
    Toolbar toolbar;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.timer_screen_layout, container, false);
        ButterKnife.bind(this, v);


        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back_white));
        toolbar.setTitle("Timer app thing??");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
            }
        });

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(TimerScreenFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_launcher_background));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                NavHostFragment.findNavController(TimerScreenFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void navigateToHomeScreenFragment() {
        NavHostFragment.findNavController(TimerScreenFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }
}
