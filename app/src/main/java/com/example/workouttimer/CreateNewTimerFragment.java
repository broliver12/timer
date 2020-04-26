package com.example.workouttimer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateNewTimerFragment extends Fragment {


    @BindView(R.id.add_section_button)
    Button addSectionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_new_timer_screen_layout, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarSetup();
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
