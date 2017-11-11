package com.acer.e_maintenance.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.oop.Item_tindak_hard;
import com.acer.e_maintenance.oop.Item_tindak_soft;

import java.util.ArrayList;

public class adapter_list_tindak_soft extends RecyclerView.Adapter<adapter_list_tindak_soft.DataObjectHolder>{

    private ArrayList<Item_tindak_soft> mDataset;
    private Context context;

    public adapter_list_tindak_soft(ArrayList<Item_tindak_soft> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tindak_soft, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.nama_soft_tindak.setText(mDataset.get(position).getNama_soft_tindak());
        holder.ket_tindak_soft.setText(mDataset.get(position).getKeterangan_tindak_soft());
        holder.idkom_soft.setText(mDataset.get(position).getId_kom_soft());
        holder.txt_tindak_lanjut.setText(mDataset.get(position).getKet_soft_maintenance());
        }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView nama_soft_tindak, ket_tindak_soft,idkom_soft;
        EditText txt_tindak_lanjut;
        ImageView image;

        public DataObjectHolder(View itemView) {
            super(itemView);

            nama_soft_tindak = (TextView) itemView.findViewById(R.id.nama_soft_tindak);
            idkom_soft = (TextView) itemView.findViewById(R.id.idkom_soft);
            ket_tindak_soft = (TextView) itemView.findViewById(R.id.ket_tindak_soft);
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




































