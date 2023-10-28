package com.example.eld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eld.utils.CommonUtils;

import java.util.ArrayList;

public class Certifyadpter extends RecyclerView.Adapter<Certifyadpter.ViewHolder> {
    private Context context;
    private static ArrayList<PendingLog> modelArrayList;

    public Certifyadpter(Context context, ArrayList<PendingLog> modelArrayList) {
        this.context = context;
        Certifyadpter.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PendingLog recordModel = modelArrayList.get(position);
        holder.checkBox.setText("Checkbox " + position);
        holder.tvDate.setText(CommonUtils.convertDateFormat(recordModel.getDate()));
        holder.checkBox.setChecked(recordModel.isSelected());
        holder.checkBox.setTag(R.integer.btnPlusView, holder);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(v -> {
            Integer pos = (Integer) holder.checkBox.getTag();
            if (modelArrayList.get(pos).isSelected()) {
                modelArrayList.get(pos).setSelected(false);
            } else {
                modelArrayList.get(pos).setSelected(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        private TextView tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_pending_log);
            tvDate = itemView.findViewById(R.id.date_pending_log);
        }
    }
}
