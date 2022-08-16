package com.example.hospital_ms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Orders> list;
    ProgressBar progressid;
    public MyAdapter(Context context, ArrayList<Orders> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userorders,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Orders order= list.get(position);
        holder.amount.setText(order.getAmount());
        holder.datetime.setText(order.getDatetime());
        holder.status.setText(order.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView amount,datetime,status;
       // ProgressBar progressid;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            amount=itemView.findViewById(R.id.tViewAmount);
            datetime=itemView.findViewById(R.id.tViewDT);
            status=itemView.findViewById(R.id.tViewStatus);
            progressid=itemView.findViewById(R.id.progressId);
        }
    }
}
