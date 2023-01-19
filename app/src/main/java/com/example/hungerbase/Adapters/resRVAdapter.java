package com.example.hungerbase.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hungerbase.Models.resRVModel;
import com.example.hungerbase.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class resRVAdapter extends FirebaseRecyclerAdapter<resRVModel,resRVAdapter.myViewHolder> {



    public resRVAdapter(@NonNull FirebaseRecyclerOptions<resRVModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.res_rv_row,parent,false);
        return new myViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull resRVModel model) {
        holder.name.setText(model.getresName());
        holder.address.setText(model.getAddress());

    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,address;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtRName);
            address = itemView.findViewById(R.id.txtAddress);


        }
    }
}
