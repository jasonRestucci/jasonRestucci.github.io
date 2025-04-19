package com.example.cs_360_jasonrestucci_option1_projecttwo;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder{
    public TextView textInvItem, textInvItemQty;
    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        textInvItem = itemView.findViewById(R.id.textInvItem);
        textInvItemQty = itemView.findViewById(R.id.textInvItemQuty);
    }
}
