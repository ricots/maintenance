package com.acer.e_maintenance.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.activity.hardware_detail;
import com.acer.e_maintenance.oop.Item_komputer;
import com.acer.e_maintenance.oop.Item_tindak_hard;

import java.util.ArrayList;

public class adapter_list_tindak_hard extends RecyclerView.Adapter<adapter_list_tindak_hard.DataObjectHolder>{

    private ArrayList<Item_tindak_hard> mDataset;
    private Context context;

    public adapter_list_tindak_hard(ArrayList<Item_tindak_hard> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tindak_hard, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.nama_kom_tindak.setText(mDataset.get(position).getNama_komponen_tindak());
        holder.ket_tindak_hard.setText(mDataset.get(position).getKet_tindak_lanjut());
        holder.idkom_hard.setText(mDataset.get(position).getId_kom_tindak());
        holder.txt_tindak_lanjut.setText(mDataset.get(position).getKet_maintenance_hard());
        }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView nama_kom_tindak, ket_tindak_hard,idkom_hard;
        EditText txt_tindak_lanjut;
        ImageView image;

        public DataObjectHolder(View itemView) {
            super(itemView);

            nama_kom_tindak = (TextView) itemView.findViewById(R.id.nama_kom_tindak);
            idkom_hard = (TextView) itemView.findViewById(R.id.idkom_hard);
            ket_tindak_hard = (TextView) itemView.findViewById(R.id.ket_tindak_hard);
            txt_tindak_lanjut = (EditText) itemView.findViewById(R.id.txt_tindak_lanjut);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Intent goDetail = new Intent(context, hardware_detail.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",title.getText().toString());
                    goDetail.putExtras(bundle);
                    v.getContext().startActivity(goDetail);*/
                }
            });
        }
    }
}




































