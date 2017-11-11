package com.acer.e_maintenance.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acer.e_maintenance.oop.Item_komputer;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.activity.detail_software;
import com.acer.e_maintenance.oop.Item;

public class adapter_software extends RecyclerView.Adapter<adapter_software.DataObjectHolder>{
    private ArrayList<Item_komputer> mDataset;
    private Context context;

    public adapter_software(ArrayList<Item_komputer> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public adapter_software.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_software, parent, false);
        adapter_software.DataObjectHolder dataObjectHolder = new adapter_software.DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(adapter_software.DataObjectHolder holder, int position) {

        holder.title.setText(mDataset.get(position).getId_komputer());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView title, body;

        public DataObjectHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.kode_cpunya);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goDetail = new Intent(context, detail_software.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",title.getText().toString());
                    goDetail.putExtras(bundle);
                    v.getContext().startActivity(goDetail);
                }
            });
        }
    }
}