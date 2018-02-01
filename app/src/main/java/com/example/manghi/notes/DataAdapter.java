package com.example.manghi.notes;

/**
 * Created by Vishal on 12-12-2017.
 */

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView tv_names,time,noti_time,reminder_id;
        ImageView set_reminder;

        ViewHolder(View view) {
            super(view);
            tv_names = (TextView)view.findViewById(R.id.tv_names);
            time = (TextView)view.findViewById(R.id.time);
            noti_time = (TextView) view.findViewById(R.id.noti_time);
            reminder_id = (TextView) view.findViewById(R.id.reminder_id);
            set_reminder = (ImageView) view.findViewById(R.id.set_reminder);

            //linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);

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

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        DataModel dataModel = names.get(position);

        if(dataModel.getTitle() != null)
            viewHolder.tv_names.setText(dataModel.getTitle());
        else
            viewHolder.tv_names.setText(dataModel.getName());

        viewHolder.time.setText(dataModel.getDate());

        /*if(dataModel.getBookmark() == 0)
            viewHolder.book.setVisibility(View.VISIBLE);
        */

        /*
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //showdialog();
            }
        });*/


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm");
        String dateString = formatter.format(new Date(dataModel.getNotiTime()));

        if(dataModel.getNotiTime() > System.currentTimeMillis()) {
            viewHolder.noti_time.setText(dateString);
        }

        String remID = String.valueOf(dataModel.getReminderID());
        viewHolder.reminder_id.setText(remID);

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
