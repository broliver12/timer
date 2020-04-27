package com.example.workouttimer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.workouttimer.R;
import com.example.workouttimer.activity.MainActivity;
import com.example.workouttimer.model.Section;
import com.example.workouttimer.model.Timer;
import com.example.workouttimer.viewmodel.CreateNewTimerViewModelInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateNewTimerFragment extends Fragment {

    @BindView(R.id.add_section_button)
    Button addSectionButton;
    @BindView(R.id.title_edit_text)
    EditText titleEditText;

    private CreateNewTimerViewModelInterface viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewModel = ((MainActivity) getActivity()).getViewModel();
        View v = inflater.inflate(R.layout.create_new_timer_screen_layout, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarSetup();

        addSectionButton.setOnClickListener(v -> {
            Timer t = new Timer(titleEditText.getText().toString());
            t.setDuration(400);
            ArrayList<Section> secs = new ArrayList<>();
            secs.add(new Section("Rest"));
            secs.add(new Section("Work"));
            secs.add(new Section("Rest"));

            t.setSections(secs);
            viewModel.addTimerToList(t);
        });
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
