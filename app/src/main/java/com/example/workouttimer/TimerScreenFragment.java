package com.example.workouttimer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerScreenFragment extends Fragment implements TimerScreenFragmentInterface {

    @BindView(R.id.clock_display_text_view)
    TextView clockDisplayTextView;

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
        toolbarSetup();
    }

    @Override
    public void navigateToHomeScreenFragment() {
        NavHostFragment.findNavController(TimerScreenFragment.this)
                .navigate(R.id.action_timer_to_home);
    }

    private void toolbarSetup(){

        // get the ToolBar from Main Activity
        final Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        // get the ActionBar from Main Activity
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        // inflate the customized Action Bar View
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.timer_screen_toolbar, null);

        if (actionBar != null) {
            // enable the customized view and disable title
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);

            actionBar.setCustomView(v);
            // remove Burger Icon
            toolbar.setNavigationIcon(null);

            // add click listener to the back arrow icon
            v.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        }


    }
}
