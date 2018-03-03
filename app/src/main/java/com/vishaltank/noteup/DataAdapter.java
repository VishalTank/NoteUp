package com.vishaltank.noteup;

import android.annotation.SuppressLint;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vishal on 03-03-2018.
 */

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private String note_string;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView note,time,noti_time,title;
        ImageView alarm_on,pin;

        ViewHolder(View view) {
            super(view);
            note = view.findViewById(R.id.note);
            time = view.findViewById(R.id.creation_time);
            alarm_on = view.findViewById(R.id.alarm_on);
            //pin = (ImageView) view.findViewById(R.id.alarm_on);

            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }



    private List<DataModel> names;

    DataAdapter(List<DataModel> names) {
        this.names = names;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        DataModel dataModel = names.get(position);
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd',' yyyy  hh:mm a");

        viewHolder.note.setText(dataModel.getName());

        String CreationTime = formatter.format(new Date(dataModel.getTime()));
        viewHolder.time.setText(CreationTime);

        String ReminderTime = formatter.format(new Date(dataModel.getReminderTime()));

        if(dataModel.getReminderTime() > System.currentTimeMillis())
            viewHolder.alarm_on.setVisibility(View.VISIBLE);
    }


    @Override
    public int getItemCount() {
        return names.size();
    }

    void removeItem(int position) {

        names.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, names.size());
    }
}