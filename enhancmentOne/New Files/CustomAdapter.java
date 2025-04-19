package com.example.cs_360_jasonrestucci_option1_projecttwo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Author: Jason Restucci
 * Date Last Modified: 3/21/25
 * Description: inflates item models to recycler view
 */

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
        View view = LayoutInflater.from(context).inflate(R.layout.single_items, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        holder.textInvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String index = holder.textInvItemIndex.getText().toString();
                Intent intentIndex = new Intent(context.getApplicationContext(), EditItemMenu.class);
                intentIndex.putExtra("index", index);
                context.startActivity(intentIndex);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textInvItem.setText(invList.get(position).getItem());
        holder.textInvItemQty.setText(String.valueOf(invList.get(position).getQty()));
        holder.textInvItemIndex.setText(String.valueOf(invList.get(position).getIndex()));
    }

    @Override
    public int getItemCount() {
        return invList.size();
    }
}
