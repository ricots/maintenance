package com.acer.e_maintenance.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.koneksi.config;
import com.acer.e_maintenance.oop.Item;

public class adapter_detail_software extends RecyclerView.Adapter<adapter_detail_software.DataObjectHolder> {
    private ArrayList<Item> mDataset;
    Context context;

    public adapter_detail_software(ArrayList<Item> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public adapter_detail_software.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_software, parent, false);
        adapter_detail_software.DataObjectHolder dataObjectHolder = new adapter_detail_software.DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(adapter_detail_software.DataObjectHolder holder, int position) {

        holder.id_detail.setText(mDataset.get(position).getId_komputer());

        holder.id_idsoft.setText(mDataset.get(position).getId_software());
        holder.nama_software.setText(mDataset.get(position).getNama_software());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        public TextView nama_software, id_detail, id_idsoft;
        Item item;
        String asisten;

        public DataObjectHolder(View itemView) {
            super(itemView);

            id_detail = (TextView) itemView.findViewById(R.id.id_Detail);
            id_idsoft = (TextView) itemView.findViewById(R.id.id_idsof);
            nama_software = (TextView) itemView.findViewById(R.id.nama_software);
            //Toast.makeText(context, "kode asisten " + asisten, Toast.LENGTH_LONG).show();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = context.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    asisten = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("aksi");
                    builder.setMessage(id_idsoft.getText().toString());
                    builder.setPositiveButton("HAPUS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //RequestQueue requestQueue = Volley.newRequestQueue(context);
                            String idnya = id_idsoft.getText().toString();
                            String asis = asisten;
                            delete(idnya,asis);
                        }
                    });
                    builder.setNegativeButton("BATAl", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

    }

    public void delete(final String idnya, final String asis) {
        //final String id = idnya;
        //Toast.makeText(context,config.DELETE_SOFT + idnya + "&id_asisten" + asis,Toast.LENGTH_LONG).show();
        final ProgressDialog loading = ProgressDialog.show(context, "DELETE DATA...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, config.DELETE_SOFT + idnya +
                "&id_asisten=" + asis,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                        System.out.println("hapus " + s);
                        notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put(config.KEY_ID_asis, asis);
                params.put(config.KEY_id_SOft,idnya);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}