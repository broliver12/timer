package com.example.workouttimer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttimer.fragment.HomeScreenFragmentInterface;
import com.example.workouttimer.R;
import com.example.workouttimer.model.Timer;

import java.util.ArrayList;

public class TimerRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Timer> items;
    private HomeScreenFragmentInterface fragmentInterface;

    public TimerRecyclerViewAdapter(Context context, ArrayList<Timer> items, HomeScreenFragmentInterface fragmentInterface) {
        this.context = context;
        this.items = items;
        this.fragmentInterface = fragmentInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View row = layoutInflater.inflate(R.layout.timer_recyclerview_tile, parent, false);

        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((Item) holder).titleTextView.setText(items.get(position).getTitle());
        ((Item) holder).selectButton.setOnClickListener(v -> {
            if (fragmentInterface.onSelectClicked(items.get(position).getTitle())) {
                fragmentInterface.navigateToSelectedTimerFragment();
            }
        });

        ((Item) holder).deleteButton.setOnClickListener(v -> {
            if (fragmentInterface.onDeleteClicked(((Item) holder).titleTextView.getText().toString())) {
                notifyDataSetChanged();
            }
        });
    }

    public class Item extends RecyclerView.ViewHolder {

        TextView titleTextView;
        Button deleteButton;
        Button selectButton;

        public Item(View itemView) {
            super(itemView);
            this.titleTextView = itemView.findViewById(R.id.timerRcvRowTitleTextView);
            this.selectButton = itemView.findViewById(R.id.tile_select_button);
            this.deleteButton = itemView.findViewById(R.id.tile_delete_button);
        }

    }

    public void updateList(ArrayList<Timer> newList) {
        items = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
