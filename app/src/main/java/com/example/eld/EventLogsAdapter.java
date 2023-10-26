package com.example.eld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eld.network.dto.login.response.Record;
import com.example.eld.utils.CommonUtils;

import java.util.List;

public class EventLogsAdapter extends RecyclerView.Adapter<EventLogsAdapter.LogsHolder> {

    Context context;
    List<Record> list;

    public EventLogsAdapter(Context context, List<Record> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EventLogsAdapter.LogsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_layout, parent, false);
        return new LogsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventLogsAdapter.LogsHolder holder, int position) {
        Record recordModel = list.get(position);
        if (recordModel.getTimeRecord() != null) {
            holder.time.setText(CommonUtils.convertDateTime(recordModel.getTimeRecord(), "HH:mm:ss", "HH:mm"));
        }

        holder.status.setText(recordModel.getAttendenceType());
        holder.location.setText(!recordModel.getLocation().equals("null") ? recordModel.getLocation() : "Location Not Available");
        holder.odo.setText(recordModel.getOdometer());
        holder.eng.setText(recordModel.getEngineHours());
        holder.origin.setText(recordModel.getOrigin());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class LogsHolder extends RecyclerView.ViewHolder {
        TextView time, status, location, odo, eng, origin;

        public LogsHolder(@NonNull View itemView) {
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
