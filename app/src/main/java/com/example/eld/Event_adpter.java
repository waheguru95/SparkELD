package com.example.eld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eld.custumclass.TimestampConverter;

import java.util.ArrayList;

public class Event_adpter extends RecyclerView.Adapter<Event_adpter.evenholder> {

    Context context;
    ArrayList<Eventmodel> list;

    public Event_adpter(Context context, ArrayList<Eventmodel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Event_adpter.evenholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_layout, parent, false);
        return new evenholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Event_adpter.evenholder holder, int position) {
        Eventmodel eventmodel = list.get(position);
        if (eventmodel.getTime() != null) {
            holder.time.setText(TimestampConverter.convertTimestamp(eventmodel.getTime()));
        }

        holder.status.setText(eventmodel.getStatus());
        holder.location.setText(eventmodel.getLocation());
        holder.odo.setText(eventmodel.getOdometer());
        holder.eng.setText(eventmodel.getEh());
        holder.origin.setText(eventmodel.getOrign());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class evenholder extends RecyclerView.ViewHolder {
        TextView time, status, location, odo, eng, origin;

        public evenholder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.timeadpter);
            status = itemView.findViewById(R.id.statusadpter);
            location = itemView.findViewById(R.id.locatioadpter);
            odo = itemView.findViewById(R.id.odoadpter);
            eng = itemView.findViewById(R.id.engadpter);
            origin = itemView.findViewById(R.id.originadpter);
        }
    }

}
