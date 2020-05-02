package com.example.workouttimer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttimer.R;
import com.example.workouttimer.fragment.HomeScreenFragmentInterface;
import com.example.workouttimer.model.Section;
import com.example.workouttimer.model.Timer;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SectionRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<Section> sectionList;

    public SectionRecyclerViewAdapter(Context context, ArrayList<Section> items) {
        this.context = context;
        this.sectionList = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View row = layoutInflater.inflate(R.layout.section_recyclerview_tile, parent, false);

        SectionTile tile = new SectionTile(row);
        return tile;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SectionTile) holder).labelTextView.setText(sectionList.get(position).getLabel());
        ((SectionTile) holder).durationTextView.setText(sectionList.get(position).getDuration() + " s");
        ((SectionTile) holder).typeTextView.setText(sectionList.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    public class SectionTile extends RecyclerView.ViewHolder {
        TextView labelTextView;
        TextView durationTextView;
        TextView typeTextView;

        public SectionTile(View itemView){
            super(itemView);

            labelTextView = itemView.findViewById(R.id.section_tile_label_text_view);
            durationTextView = itemView.findViewById(R.id.section_tile_duration_text_view);
            typeTextView = itemView.findViewById(R.id.section_tile_type_text_view);
        }
    }

    public void addItem(int dur, String label, String type){
        this.sectionList.add(new Section(dur, label, type));
        notifyDataSetChanged();
    }
}
