package com.tank.vishal.noteup;

/**
 * Created by Vishal on 02-02-2018.
 */

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView tv_names,time,noti_time;
        ImageView alarm_on;

        ViewHolder(View view) {
            super(view);
            tv_names = (TextView)view.findViewById(R.id.tv_names);
            time = (TextView)view.findViewById(R.id.creation_time);
            noti_time = (TextView) view.findViewById(R.id.noti_time);
            alarm_on = (ImageView) view.findViewById(R.id.alarm_on);

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

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        DataModel dataModel = names.get(position);

        if(dataModel.getTitle() != null)
            viewHolder.tv_names.setText(dataModel.getTitle());
        else
            viewHolder.tv_names.setText(dataModel.getName());

        viewHolder.time.setText(dataModel.getDate());


        /*
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //showdialog();
            }
        });*/


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm");
        String dateString = formatter.format(new Date(dataModel.getReminderTime()));

        if(dataModel.getReminderTime() > System.currentTimeMillis()) {
            viewHolder.noti_time.setText(dateString);
            viewHolder.alarm_on.setVisibility(View.VISIBLE);
        }
        else
            viewHolder.noti_time.setText("Not Set!" + dataModel.getReminderID());

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
