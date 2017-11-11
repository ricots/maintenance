package com.acer.e_maintenance.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.acer.e_maintenance.R;
import com.acer.e_maintenance.activity.hardware_detail;
import com.acer.e_maintenance.oop.Item_komputer;

import java.util.ArrayList;

public class Hardware_adapter extends RecyclerView.Adapter<Hardware_adapter.DataObjectHolder>{

    private ArrayList<Item_komputer> mDataset;
    private Context context;

    public Hardware_adapter(ArrayList<Item_komputer> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hardware, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.title.setText(mDataset.get(position).getId_komputer());

        }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView title, body;
        ImageView image;

        public DataObjectHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.kode_cpu);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goDetail = new Intent(context, hardware_detail.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",title.getText().toString());
                    goDetail.putExtras(bundle);
                    v.getContext().startActivity(goDetail);
                }
            });
        }
    }
}




































