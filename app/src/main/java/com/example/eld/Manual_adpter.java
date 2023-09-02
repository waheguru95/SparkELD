package com.example.eld;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eld.activity.Showfullimage_screen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Manual_adpter extends RecyclerView.Adapter<Manual_adpter.manualholder> {
    Context context;
    ArrayList<Modelclass> list;

    public Manual_adpter(Context context, ArrayList<Modelclass> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public Manual_adpter.manualholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manual_layout,parent,false);
        return new manualholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Manual_adpter.manualholder holder, int position) {
        Modelclass modelclass=list.get(position);
        holder.heading.setText(modelclass.getHeading());
        holder.discriprion.setText(modelclass.getDiscription());
        Glide.with(context).load(modelclass.getImage()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Showfullimage_screen.class);
                intent.putExtra("url",modelclass.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class manualholder extends RecyclerView.ViewHolder {
        TextView heading,discriprion;
        ImageView image;
        public manualholder(@NonNull View itemView) {
            super(itemView);
            heading=itemView.findViewById(R.id.heading);
            discriprion=itemView.findViewById(R.id.discription);
            image=itemView.findViewById(R.id.manualimage);
        }
    }
}
