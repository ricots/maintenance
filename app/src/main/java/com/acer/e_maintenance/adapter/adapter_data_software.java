package com.acer.e_maintenance.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.koneksi.config;
import com.acer.e_maintenance.oop.Item;
import com.acer.e_maintenance.oop.Item_data_soft;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapter_data_software extends RecyclerView.Adapter<adapter_data_software.DataObjectHolder> {
    private ArrayList<Item_data_soft> mDataset;
    Context context;
    private static int currentPosition = 0;
    String id_komputer;

    public adapter_data_software(ArrayList<Item_data_soft> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public adapter_data_software.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_soft, parent, false);
        adapter_data_software.DataObjectHolder dataObjectHolder = new adapter_data_software.DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final adapter_data_software.DataObjectHolder holder, final int position) {
        holder.idsoft.setText(mDataset.get(position).getId_software());
        holder.software_maint.setText(mDataset.get(position).getNama_software());
        holder.id_detail.setText(mDataset.get(position).getId_komputer());
        holder.kon_soft.setText(mDataset.get(position).getKondisi_soft());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        public TextView software_maint, id_detail, idsoft,kon_soft;
        Button btnsubmit;
        Item item;
        String asisten;
        LinearLayout linearLayout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            id_detail = (TextView) itemView.findViewById(R.id.data_Detail);
            idsoft = (TextView) itemView.findViewById(R.id.data_id_soft);
            software_maint = (TextView) itemView.findViewById(R.id.data_soft);
            kon_soft =(TextView) itemView.findViewById(R.id.kon_soft);
        }
    }
}