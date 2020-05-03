package com.example.workouttimer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttimer.R;
import com.example.workouttimer.activity.CustomOnDoneActionListener;
import com.example.workouttimer.activity.MainActivity;
import com.example.workouttimer.adapter.SectionRecyclerViewAdapter;
import com.example.workouttimer.model.Section;
import com.example.workouttimer.viewmodel.CreateNewTimerScreenViewModelInterface;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class CreateNewTimerScreenFragment extends Fragment {

    @BindView(R.id.add_section_button)
    Button addSectionButton;
    @BindView(R.id.title_edit_text)
    EditText titleEditText;

    @BindView(R.id.section_modifiers_linear_layout)
    LinearLayout sectionModifiersLinearLayout;
    @BindView(R.id.choose_duration_number_picker)
    NumberPicker chooseDurationNumberPicker;
    @BindView(R.id.section_label_edit_text)
    EditText sectionLabelEditText;
    @BindView(R.id.choose_section_type_number_picker)
    NumberPicker chooseSectionTypeNumberPicker;
    @BindView(R.id.confirm_add_section_button)
    Button confirmAddSectionButton;
    @BindView(R.id.cancel_add_section_button)
    Button cancelAddSectionButton;
    @BindView(R.id.create_timer_section_recyclerview)
    RecyclerView recyclerView;

    Button submitButton;
    Button discardButton;


    private CreateNewTimerScreenViewModelInterface viewModel;
    private static String[] typeOptionsArray = {"Work", "Rest"};
    private Observable<Integer> stateObs;

    private SectionRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewModel = ((MainActivity) getActivity()).getCreateNewTimerScreenViewModel();
        View v = inflater.inflate(R.layout.create_new_timer_screen_layout, container, false);
        ButterKnife.bind(this, v);

        chooseDurationNumberPicker.setMaxValue(600);
        chooseDurationNumberPicker.setMinValue(1);
        chooseSectionTypeNumberPicker.setMaxValue(1);
        chooseSectionTypeNumberPicker.setMinValue(0);
        chooseSectionTypeNumberPicker.setDisplayedValues(typeOptionsArray);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbarSetup();

        updateView(0);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SectionRecyclerViewAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        stateObs = viewModel.getStateObservable();

        stateObs.doOnNext(x -> updateView(x))
                .subscribe();

        addSectionButton.setOnClickListener(v -> {
            viewModel.onAddSectionPressed();
            this.resetAddSectionLayout();
            removeFocusAndHideKeyboard();
        });

        cancelAddSectionButton.setOnClickListener(v -> {
            viewModel.onCancelPressed();
            removeFocusAndHideKeyboard();
        });

        confirmAddSectionButton.setOnClickListener(v -> {
            viewModel.onAddPressed(chooseDurationNumberPicker.getValue(), sectionLabelEditText.getText().toString(), typeOptionsArray[chooseSectionTypeNumberPicker.getValue()]);
            adapter.addItem(chooseDurationNumberPicker.getValue(), sectionLabelEditText.getText().toString(), typeOptionsArray[chooseSectionTypeNumberPicker.getValue()]);
            removeFocusAndHideKeyboard();
        });

        titleEditText.setOnEditorActionListener(new CustomOnDoneActionListener());
        sectionLabelEditText.setOnEditorActionListener(new CustomOnDoneActionListener());

    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).forceShowSoftKeyboard();
    }

    private void resetAddSectionLayout() {
        sectionLabelEditText.setText("");
        chooseDurationNumberPicker.setValue(1);
        chooseSectionTypeNumberPicker.setValue(0);
    }

    private void removeFocusAndHideKeyboard(){
        sectionModifiersLinearLayout.requestFocus();
        ((MainActivity) getActivity()).hideSoftKeyboard();
    }

    private void toolbarSetup(){

        final Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.create_timer_screen_toolbar, null);


        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);

            actionBar.setCustomView(v);
            toolbar.setNavigationIcon(null);

            submitButton = v.findViewById(R.id.confirm_add_timer_button);
            submitButton.setOnClickListener(v_ -> {
                viewModel.onSavePressed(titleEditText.getText().toString());
                removeFocusAndHideKeyboard();
                getActivity().onBackPressed();
            });

            discardButton = v.findViewById(R.id.discard_timer_button);
            discardButton.setOnClickListener(v_ -> {
                viewModel.onDiscardPressed();
                removeFocusAndHideKeyboard();
                getActivity().onBackPressed();
            });
        }
    }


    private void updateView(int state){
        switch (state) {
            case 0:
                sectionModifiersLinearLayout.setVisibility(View.GONE);
                submitButton.setVisibility(View.INVISIBLE);
                addSectionButton.setVisibility(View.VISIBLE);
                break;

            case 1:
                sectionModifiersLinearLayout.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.INVISIBLE);
                addSectionButton.setVisibility(View.GONE);
                break;

            case 2:
                sectionModifiersLinearLayout.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
                addSectionButton.setVisibility(View.VISIBLE);
                break;

            default:
        }
    }
}
