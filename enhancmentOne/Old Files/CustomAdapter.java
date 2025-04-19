package com.example.cs_360_jasonrestucci_option1_projecttwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
    private Context context;
    private List<MyModel> invList;

    public CustomAdapter(Context context, List<MyModel> invList) {
        this.context = context;
        this.invList = invList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.single_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textInvItem.setText(invList.get(position).getItem());
        holder.textInvItemQty.setText(String.valueOf(invList.get(position).getQty()));
    }

    @Override
    public int getItemCount() {
        return invList.size();
    }
}
