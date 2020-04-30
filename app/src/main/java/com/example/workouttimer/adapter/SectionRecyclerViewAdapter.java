package com.example.workouttimer.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttimer.model.Section;

import java.util.ArrayList;

public class SectionRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<Section> sectionList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }
}
