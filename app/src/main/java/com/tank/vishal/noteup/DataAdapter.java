package com.tank.vishal.noteup;

/**
 * Created by Vishal on 02-02-2018.
 */

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

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private String note_string;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView note,time,noti_time,title;
        ImageView alarm_on,pin;

        ViewHolder(View view) {
            super(view);
            note = (TextView)view.findViewById(R.id.note);
            time = (TextView)view.findViewById(R.id.creation_time);
            noti_time = (TextView) view.findViewById(R.id.noti_time);
            alarm_on = (ImageView) view.findViewById(R.id.alarm_on);
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

        /*if(!Objects.equals(dataModel.getTitle(), ""))
            viewHolder.tv_names.setText(dataModel.getTitle());
        else
            viewHolder.tv_names.setText(Html.fromHtml(dataModel.getName()));*/

        //if(dataModel.getName().length() > 20)
        //    note_string = dataModel.getName().substring(0,20) + "...";
        //else

        viewHolder.note.setText(dataModel.getName());

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd',' yyyy  hh:mm");

        String CreationTime = formatter.format(new Date(dataModel.getTime()));
        viewHolder.time.setText(CreationTime);

        /*if(dataModel.getBookmark() == 1)
            viewHolder.pin.setVisibility(View.VISIBLE);
        else
            viewHolder.pin.setVisibility(View.INVISIBLE);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //showdialog();
            }
        });*/

        String ReminderTime = formatter.format(new Date(dataModel.getReminderTime()));

        if(dataModel.getReminderTime() > System.currentTimeMillis()) {
            viewHolder.noti_time.setVisibility(View.VISIBLE);
            viewHolder.noti_time.setText(ReminderTime);
            viewHolder.alarm_on.setVisibility(View.VISIBLE);
        }
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
