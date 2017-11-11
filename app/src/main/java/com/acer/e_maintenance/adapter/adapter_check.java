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
import com.acer.e_maintenance.oop.Item_komputer;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapter_check extends RecyclerView.Adapter<adapter_check.DataObjectHolder> {
    private ArrayList<Item> mDataset;
    Context context;
    private static int currentPosition = 0;
    String id_komputer;

    public adapter_check(ArrayList<Item> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public adapter_check.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_check, parent, false);
        adapter_check.DataObjectHolder dataObjectHolder = new adapter_check.DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final adapter_check.DataObjectHolder holder, final int position) {
        holder.idsoft.setText(mDataset.get(position).getId_software());
        holder.software_maint.setText(mDataset.get(position).getNama_software());
        holder.id_detail.setText(mDataset.get(position).getId_komputer());

        holder.check_ada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.check_ada.isChecked()){
                    holder.check_tidak.setEnabled(false);
                }else{
                    holder.check_tidak.setEnabled(true);
                    holder.check_ada.setEnabled(true);
                }
            }
        });

        holder.check_tidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.check_tidak.isChecked()){
                    holder.check_ada.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_soft);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkomsoft);
                    txt_id.setText(holder.id_detail.getText().toString());
                    final EditText komp_soft = (EditText) dialog.findViewById(R.id.komp_soft);
                    komp_soft.setText(holder.software_maint.getText().toString());
                    final EditText komp_soft_tindak = (EditText) dialog.findViewById(R.id.komp_soft_tindak);

                    Button btn_tidak_lanjutsoft = (Button) dialog.findViewById(R.id.btn_tidak_lanjutsoft);
                    btn_tidak_lanjutsoft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_soft.getText().toString();
                            final String komponen_tindak = komp_soft_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_SOFT,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_soft_tindak.setText("");
                                                dialog.dismiss();
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                //Dismissing the progress dialog
                                                loading.dismiss();
                                                Log.d("erore", volleyError.toString());
                                                Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put(config.KEY_NAMA_SOFT, komponen);
                                        params.put(config.KEY_KETERANGAN_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_SOFT, id_komputer);

                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                requestQueue.add(postRequest);
                            }
                        }
                    });

                    dialog.show();
                }else {
                    holder.check_tidak.setEnabled(true);
                    holder.check_ada.setEnabled(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        public TextView software_maint, id_detail, idsoft;
        CheckBox check_ada, check_tidak;
        Button btnsubmit;
        Item item;
        String asisten;
        LinearLayout linearLayout;
        String kon_soft = null;

        public DataObjectHolder(View itemView) {
            super(itemView);
            check_ada = (CheckBox) itemView.findViewById(R.id.check_ada);
            check_tidak = (CheckBox) itemView.findViewById(R.id.check_tidak);
            id_detail = (TextView) itemView.findViewById(R.id.id_Detail);
            idsoft = (TextView) itemView.findViewById(R.id.idsof_maint);
            software_maint = (TextView) itemView.findViewById(R.id.software_maint);
            btnsubmit = (Button) itemView.findViewById(R.id.btnsubmit);
            btnsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = context.getSharedPreferences("idkomnya",0);
                    SharedPreferences.Editor spe = sp.edit();
                    final String id = sp.getString("idkomnya","");
                    sp = context.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    asisten = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");
                    if (check_ada.isChecked()){
                        kon_soft = check_ada.getText().toString();
                    }else if (check_tidak.isChecked()){
                        kon_soft = check_tidak.getText().toString();
                    }

                    /*if ((check_ada.isChecked() == false) || (check_tidak.isChecked() == false)){
                        Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                    } else {*/
                        final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                        StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_SOFT,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        loading.dismiss();
                                        Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                        System.out.println("tambah " + s);
                                        notifyDataSetChanged();
                                        check_ada.setChecked(false);
                                        check_tidak.setChecked(false);

                                        check_tidak.setEnabled(false);
                                        check_ada.setEnabled(false);
                                        btnsubmit.setEnabled(false);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        //Dismissing the progress dialog
                                        loading.dismiss();
                                        Log.d("erore", volleyError.toString());
                                        Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put(config.KEY_ID_asis, asisten);
                                params.put(config.KEY_ID_KOM, id);
                                params.put(config.KEY_id_SOft, idsoft.getText().toString());
                                params.put(config.KEY_KONDISI_SOFT, kon_soft);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(postRequest);
                    //}
                }
            });
        }
    }
}