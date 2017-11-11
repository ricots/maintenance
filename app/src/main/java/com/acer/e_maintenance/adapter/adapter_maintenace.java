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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.acer.e_maintenance.oop.Item_hardware;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapter_maintenace extends RecyclerView.Adapter<adapter_maintenace.DataObjectHolder> {
    private ArrayList<Item_hardware> mDataset;
    private Context context;
    private static int currentPosition = 0;

    public adapter_maintenace(ArrayList<Item_hardware> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public adapter_maintenace.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_maintenance, parent, false);
        adapter_maintenace.DataObjectHolder dataObjectHolder = new adapter_maintenace.DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final adapter_maintenace.DataObjectHolder holder, final int position) {


        holder.btn.setText(mDataset.get(position).getId_komputer());
        holder.prosesor.setText(mDataset.get(position).getProsesor());
        holder.ram.setText(mDataset.get(position).getMemory());
        holder.hdd.setText(mDataset.get(position).getHardisk());
        holder.monitor.setText(mDataset.get(position).getMonitor());
        holder.casing.setText(mDataset.get(position).getCasing());
        holder.vga.setText(mDataset.get(position).getVga());
        holder.lan.setText(mDataset.get(position).getLan());
        holder.power_suply.setText(mDataset.get(position).getPower_supply());
        holder.sound_card.setText(mDataset.get(position).getSondcard());
        holder.mouse.setText(mDataset.get(position).getMouse());
        holder.keyboard.setText(mDataset.get(position).getKeyboard());
        holder.linearLayout.setVisibility(View.GONE);
        if (currentPosition == position) {
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.linearLayout.startAnimation(slideDown);
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition = position;
                notifyDataSetChanged();
            }
        });

        holder.checkbox_baik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkbox_baik.isChecked()){
                    holder.checkBox_masalah.setEnabled(false);
                }else {
                    holder.checkBox_masalah.setEnabled(true);
                    holder.checkbox_baik.setEnabled(true);
                }
            }
        });

        holder.checkBox_masalah.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox_masalah.isChecked()){
                    holder.checkbox_baik.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_hard);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkom);
                    txt_id.setText(holder.btn.getText().toString());
                    final EditText komp_hard = (EditText) dialog.findViewById(R.id.komp_hard);
                    final EditText komp_hard_tindak = (EditText) dialog.findViewById(R.id.komp_hard_tindak);
                    komp_hard.setText(holder.prosesor.getText().toString());

                    Button btn_tindak = (Button) dialog.findViewById(R.id.btn_tidak_lanjut);
                    btn_tindak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_hard.getText().toString();
                            final String komponen_tindak = komp_hard_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_HARD,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_hard_tindak.setText("");
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
                                        params.put(config.KEY_NAMA_KOMPONEN, komponen);
                                        params.put(config.KEY_KET_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_TINDAK, id_komputer);

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
                    holder.checkBox_masalah.setEnabled(true);
                    holder.checkbox_baik.setEnabled(true);
                }
            }
        });

        holder.checkbox_baik1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkbox_baik1.isChecked()){
                    holder.checkBox_masalah1.setEnabled(false);
                }else {
                    holder.checkBox_masalah1.setEnabled(true);
                    holder.checkbox_baik1.setEnabled(true);
                }
            }
        });

        holder.checkBox_masalah1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox_masalah1.isChecked()){
                    holder.checkbox_baik1.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_hard);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkom);
                    txt_id.setText(holder.btn.getText().toString());
                    final EditText komp_hard = (EditText) dialog.findViewById(R.id.komp_hard);
                    komp_hard.setText(holder.hdd.getText().toString());
                    final EditText komp_hard_tindak = (EditText) dialog.findViewById(R.id.komp_hard_tindak);

                    Button btn_tindak = (Button) dialog.findViewById(R.id.btn_tidak_lanjut);
                    btn_tindak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_hard.getText().toString();
                            final String komponen_tindak = komp_hard_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_HARD,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_hard_tindak.setText("");
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
                                        params.put(config.KEY_NAMA_KOMPONEN, komponen);
                                        params.put(config.KEY_KET_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_TINDAK, id_komputer);

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
                    holder.checkBox_masalah1.setEnabled(true);
                    holder.checkbox_baik1.setEnabled(true);
                }
            }
        });

        holder.checkbox_baik2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkbox_baik2.isChecked()){
                    holder.checkBox_masalah2.setEnabled(false);
                }else {
                    holder.checkBox_masalah2.setEnabled(true);
                    holder.checkbox_baik2.setEnabled(true);
                }
            }
        });

        holder.checkBox_masalah2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox_masalah2.isChecked()){
                    holder.checkbox_baik2.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_hard);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkom);
                    txt_id.setText(holder.btn.getText().toString());
                    final EditText komp_hard = (EditText) dialog.findViewById(R.id.komp_hard);
                    komp_hard.setText(holder.hdd.getText().toString());
                    final EditText komp_hard_tindak = (EditText) dialog.findViewById(R.id.komp_hard_tindak);

                    Button btn_tindak = (Button) dialog.findViewById(R.id.btn_tidak_lanjut);
                    btn_tindak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_hard.getText().toString();
                            final String komponen_tindak = komp_hard_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_HARD,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_hard_tindak.setText("");
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
                                        params.put(config.KEY_NAMA_KOMPONEN, komponen);
                                        params.put(config.KEY_KET_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_TINDAK, id_komputer);

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
                    holder.checkBox_masalah2.setEnabled(true);
                    holder.checkbox_baik2.setEnabled(true);
                }
            }
        });

        holder.checkbox_baik3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkbox_baik3.isChecked()){
                    holder.checkBox_masalah3.setEnabled(false);
                }else {
                    holder.checkBox_masalah3.setEnabled(true);
                    holder.checkbox_baik3.setEnabled(true);
                }
            }
        });

        holder.checkBox_masalah3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox_masalah3.isChecked()){
                    holder.checkbox_baik3.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_hard);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkom);
                    txt_id.setText(holder.btn.getText().toString());
                    final EditText komp_hard = (EditText) dialog.findViewById(R.id.komp_hard);
                    komp_hard.setText(holder.hdd.getText().toString());
                    final EditText komp_hard_tindak = (EditText) dialog.findViewById(R.id.komp_hard_tindak);

                    Button btn_tindak = (Button) dialog.findViewById(R.id.btn_tidak_lanjut);
                    btn_tindak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_hard.getText().toString();
                            final String komponen_tindak = komp_hard_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_HARD,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_hard_tindak.setText("");
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
                                        params.put(config.KEY_NAMA_KOMPONEN, komponen);
                                        params.put(config.KEY_KET_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_TINDAK, id_komputer);

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
                    holder.checkBox_masalah3.setEnabled(true);
                    holder.checkbox_baik3.setEnabled(true);
                }
            }
        });

        holder.checkbox_baik4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkbox_baik4.isChecked()){
                    holder.checkBox_masalah4.setEnabled(false);
                }else {
                    holder.checkBox_masalah4.setEnabled(true);
                    holder.checkbox_baik4.setEnabled(true);
                }
            }
        });

        holder.checkBox_masalah4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox_masalah4.isChecked()){
                    holder.checkbox_baik4.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_hard);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkom);
                    txt_id.setText(holder.btn.getText().toString());
                    final EditText komp_hard = (EditText) dialog.findViewById(R.id.komp_hard);
                    komp_hard.setText(holder.hdd.getText().toString());
                    final EditText komp_hard_tindak = (EditText) dialog.findViewById(R.id.komp_hard_tindak);

                    Button btn_tindak = (Button) dialog.findViewById(R.id.btn_tidak_lanjut);
                    btn_tindak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_hard.getText().toString();
                            final String komponen_tindak = komp_hard_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_HARD,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_hard_tindak.setText("");
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
                                        params.put(config.KEY_NAMA_KOMPONEN, komponen);
                                        params.put(config.KEY_KET_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_TINDAK, id_komputer);

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
                    holder.checkBox_masalah4.setEnabled(true);
                    holder.checkbox_baik4.setEnabled(true);
                }
            }
        });

        holder.checkbox_baik5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkbox_baik5.isChecked()){
                    holder.checkBox_masalah5.setEnabled(false);
                }else {
                    holder.checkBox_masalah5.setEnabled(true);
                    holder.checkbox_baik5.setEnabled(true);
                }
            }
        });

        holder.checkBox_masalah5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox_masalah5.isChecked()){
                    holder.checkbox_baik5.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_hard);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkom);
                    txt_id.setText(holder.btn.getText().toString());
                    final EditText komp_hard = (EditText) dialog.findViewById(R.id.komp_hard);
                    komp_hard.setText(holder.hdd.getText().toString());
                    final EditText komp_hard_tindak = (EditText) dialog.findViewById(R.id.komp_hard_tindak);

                    Button btn_tindak = (Button) dialog.findViewById(R.id.btn_tidak_lanjut);
                    btn_tindak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_hard.getText().toString();
                            final String komponen_tindak = komp_hard_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_HARD,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_hard_tindak.setText("");
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
                                        params.put(config.KEY_NAMA_KOMPONEN, komponen);
                                        params.put(config.KEY_KET_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_TINDAK, id_komputer);

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
                    holder.checkBox_masalah5.setEnabled(true);
                    holder.checkbox_baik5.setEnabled(true);
                }
            }
        });

        holder.checkbox_baik6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkbox_baik6.isChecked()){
                    holder.checkBox_masalah6.setEnabled(false);
                }else {
                    holder.checkBox_masalah6.setEnabled(true);
                    holder.checkbox_baik6.setEnabled(true);
                }
            }
        });

        holder.checkBox_masalah6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox_masalah6.isChecked()){
                    holder.checkbox_baik6.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_hard);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkom);
                    txt_id.setText(holder.btn.getText().toString());
                    final EditText komp_hard = (EditText) dialog.findViewById(R.id.komp_hard);
                    komp_hard.setText(holder.hdd.getText().toString());
                    final EditText komp_hard_tindak = (EditText) dialog.findViewById(R.id.komp_hard_tindak);

                    Button btn_tindak = (Button) dialog.findViewById(R.id.btn_tidak_lanjut);
                    btn_tindak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_hard.getText().toString();
                            final String komponen_tindak = komp_hard_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_HARD,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_hard_tindak.setText("");
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
                                        params.put(config.KEY_NAMA_KOMPONEN, komponen);
                                        params.put(config.KEY_KET_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_TINDAK, id_komputer);

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
                    holder.checkBox_masalah6.setEnabled(true);
                    holder.checkbox_baik6.setEnabled(true);
                }
            }
        });

        holder.checkbox_baik7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkbox_baik7.isChecked()){
                    holder.checkBox_masalah7.setEnabled(false);
                }else {
                    holder.checkBox_masalah7.setEnabled(true);
                    holder.checkbox_baik7.setEnabled(true);
                }
            }
        });

        holder.checkBox_masalah7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox_masalah7.isChecked()){
                    holder.checkbox_baik7.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_hard);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkom);
                    txt_id.setText(holder.btn.getText().toString());
                    final EditText komp_hard = (EditText) dialog.findViewById(R.id.komp_hard);
                    komp_hard.setText(holder.hdd.getText().toString());
                    final EditText komp_hard_tindak = (EditText) dialog.findViewById(R.id.komp_hard_tindak);

                    Button btn_tindak = (Button) dialog.findViewById(R.id.btn_tidak_lanjut);
                    btn_tindak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_hard.getText().toString();
                            final String komponen_tindak = komp_hard_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_HARD,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_hard_tindak.setText("");
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
                                        params.put(config.KEY_NAMA_KOMPONEN, komponen);
                                        params.put(config.KEY_KET_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_TINDAK, id_komputer);

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
                    holder.checkBox_masalah7.setEnabled(true);
                    holder.checkbox_baik7.setEnabled(true);
                }
            }
        });

        holder.checkbox_baik8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkbox_baik8.isChecked()){
                    holder.checkBox_masalah8.setEnabled(false);
                }else {
                    holder.checkBox_masalah8.setEnabled(true);
                    holder.checkbox_baik8.setEnabled(true);
                }
            }
        });

        holder.checkBox_masalah8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox_masalah8.isChecked()){
                    holder.checkbox_baik8.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_hard);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkom);
                    txt_id.setText(holder.btn.getText().toString());
                    final EditText komp_hard = (EditText) dialog.findViewById(R.id.komp_hard);
                    komp_hard.setText(holder.hdd.getText().toString());
                    final EditText komp_hard_tindak = (EditText) dialog.findViewById(R.id.komp_hard_tindak);

                    Button btn_tindak = (Button) dialog.findViewById(R.id.btn_tidak_lanjut);
                    btn_tindak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_hard.getText().toString();
                            final String komponen_tindak = komp_hard_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_HARD,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_hard_tindak.setText("");
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
                                        params.put(config.KEY_NAMA_KOMPONEN, komponen);
                                        params.put(config.KEY_KET_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_TINDAK, id_komputer);

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
                    holder.checkBox_masalah8.setEnabled(true);
                    holder.checkbox_baik8.setEnabled(true);
                }
            }
        });

        holder.checkbox_baik9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkbox_baik9.isChecked()){
                    holder.checkBox_masalah9.setEnabled(false);
                }else {
                    holder.checkBox_masalah9.setEnabled(true);
                    holder.checkbox_baik9.setEnabled(true);
                }
            }
        });

        holder.checkBox_masalah9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox_masalah9.isChecked()){
                    holder.checkbox_baik9.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_hard);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkom);
                    txt_id.setText(holder.btn.getText().toString());
                    final EditText komp_hard = (EditText) dialog.findViewById(R.id.komp_hard);
                    komp_hard.setText(holder.hdd.getText().toString());
                    final EditText komp_hard_tindak = (EditText) dialog.findViewById(R.id.komp_hard_tindak);

                    Button btn_tindak = (Button) dialog.findViewById(R.id.btn_tidak_lanjut);
                    btn_tindak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_hard.getText().toString();
                            final String komponen_tindak = komp_hard_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_HARD,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_hard_tindak.setText("");
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
                                        params.put(config.KEY_NAMA_KOMPONEN, komponen);
                                        params.put(config.KEY_KET_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_TINDAK, id_komputer);

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
                    holder.checkBox_masalah9.setEnabled(true);
                    holder.checkbox_baik9.setEnabled(true);
                }
            }
        });

        holder.checkbox_baik10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkbox_baik10.isChecked()){
                    holder.checkBox_masalah10.setEnabled(false);
                }else {
                    holder.checkBox_masalah10.setEnabled(true);
                    holder.checkbox_baik10.setEnabled(true);
                }
            }
        });

        holder.checkBox_masalah10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox_masalah10.isChecked()){
                    holder.checkbox_baik10.setEnabled(false);
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.pop_main_hard);
                    dialog.setTitle("tindak lanjut");
                    final TextView txt_id = (TextView) dialog.findViewById(R.id.komp_idkom);
                    txt_id.setText(holder.btn.getText().toString());
                    final EditText komp_hard = (EditText) dialog.findViewById(R.id.komp_hard);
                    komp_hard.setText(holder.hdd.getText().toString());
                    final EditText komp_hard_tindak = (EditText) dialog.findViewById(R.id.komp_hard_tindak);

                    Button btn_tindak = (Button) dialog.findViewById(R.id.btn_tidak_lanjut);
                    btn_tindak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String id_komputer = txt_id.getText().toString();
                            final String komponen = komp_hard.getText().toString();
                            final String komponen_tindak = komp_hard_tindak.getText().toString();

                            if (komponen_tindak.equals("")){
                                Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                            } else {

                                final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                                StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_TINDAK_HARD,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                loading.dismiss();
                                                Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                                System.out.println("tambah " + s);
                                                notifyDataSetChanged();
                                                komp_hard_tindak.setText("");
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
                                        params.put(config.KEY_NAMA_KOMPONEN, komponen);
                                        params.put(config.KEY_KET_TINDAK, komponen_tindak);
                                        params.put(config.KEY_ID_KOM_TINDAK, id_komputer);

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
                    holder.checkBox_masalah10.setEnabled(true);
                    holder.checkbox_baik10.setEnabled(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView prosesor,ram,hdd,monitor,casing,vga,lan,power_suply,sound_card,mouse,keyboard,id_kom,id_asis;
        CheckBox checkbox_baik,checkBox_masalah,checkbox_baik1,checkBox_masalah1,checkbox_baik2,checkBox_masalah2,
                checkbox_baik3,checkBox_masalah3,checkbox_baik4,checkBox_masalah4,checkbox_baik5,checkBox_masalah5,
                checkbox_baik6,checkBox_masalah6,checkbox_baik7,checkBox_masalah7,checkbox_baik8,checkBox_masalah8,
                checkbox_baik9,checkBox_masalah9,checkbox_baik10,checkBox_masalah10;
        Item item;
        String asisten;
        SharedPreferences sp;
        Button btn,btn_submit;
        LinearLayout linearLayout;
        String kon_prosesor = null,kon_hdd= null,kon_ram = null,kon_vga = null,kon_lan = null,
                kon_sound = null,kon_keyboard = null,kon_mouse = null,kon_power = null,kon_monitor = null,kon_casing= null;

        public DataObjectHolder(View itemView) {
            super(itemView);
            btn_submit = (Button) itemView.findViewById(R.id.btn_submit);
            btn = (Button) itemView.findViewById(R.id.form_kode_komputer);
            id_asis = (TextView) itemView.findViewById(R.id.form_id_asisten);
            id_kom = (TextView) itemView.findViewById(R.id.form_id_kom);
            prosesor = (TextView) itemView.findViewById(R.id.form_prosesor);
            ram = (TextView) itemView.findViewById(R.id.form_ram);
            hdd = (TextView) itemView.findViewById(R.id.form_hdd);
            monitor = (TextView) itemView.findViewById(R.id.form_monitor);
            casing = (TextView) itemView.findViewById(R.id.form_casing);
            vga = (TextView) itemView.findViewById(R.id.form_vga);
            lan = (TextView) itemView.findViewById(R.id.form_lan);
            power_suply = (TextView) itemView.findViewById(R.id.form_power_supply);
            sound_card = (TextView) itemView.findViewById(R.id.form_sound_card);
            mouse = (TextView) itemView.findViewById(R.id.form_mouse);
            keyboard = (TextView) itemView.findViewById(R.id.form_keyboard);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

            //checkbox cek komputer
            checkbox_baik = (CheckBox) itemView.findViewById(R.id.checkbox_baik);
            checkBox_masalah = (CheckBox) itemView.findViewById(R.id.checkbox_masalah);
            checkbox_baik1 = (CheckBox) itemView.findViewById(R.id.checkbox_baik1);
            checkBox_masalah1 = (CheckBox) itemView.findViewById(R.id.checkbox_masalah1);
            checkbox_baik2 = (CheckBox) itemView.findViewById(R.id.checkbox_baik2);
            checkBox_masalah2 = (CheckBox) itemView.findViewById(R.id.checkbox_masalah2);
            checkbox_baik3 = (CheckBox) itemView.findViewById(R.id.checkbox_baik3);
            checkBox_masalah3 = (CheckBox) itemView.findViewById(R.id.checkbox_masalah3);
            checkbox_baik4 = (CheckBox) itemView.findViewById(R.id.checkbox_baik4);
            checkBox_masalah4 = (CheckBox) itemView.findViewById(R.id.checkbox_masalah4);
            checkbox_baik5 = (CheckBox) itemView.findViewById(R.id.checkbox_baik5);
            checkBox_masalah5 = (CheckBox) itemView.findViewById(R.id.checkbox_masalah5);
            checkbox_baik6 = (CheckBox) itemView.findViewById(R.id.checkbox_baik7);
            checkBox_masalah6 = (CheckBox) itemView.findViewById(R.id.checkbox_masalah7);
            checkbox_baik7 = (CheckBox) itemView.findViewById(R.id.checkbox_baik8);
            checkBox_masalah7 = (CheckBox) itemView.findViewById(R.id.checkbox_masalah8);
            checkbox_baik8 = (CheckBox) itemView.findViewById(R.id.checkbox_baik9);
            checkBox_masalah8 = (CheckBox) itemView.findViewById(R.id.checkbox_masalah9);
            checkbox_baik9 = (CheckBox) itemView.findViewById(R.id.checkbox_baik10);
            checkBox_masalah9 = (CheckBox) itemView.findViewById(R.id.checkbox_masalah10);
            checkbox_baik10 = (CheckBox) itemView.findViewById(R.id.checkbox_baik11);
            checkBox_masalah10 = (CheckBox) itemView.findViewById(R.id.checkbox_masalah11);

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = context.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    asisten = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");

                    final String id_komputer = btn.getText().toString();
                    final String prosesor1 = prosesor.getText().toString();
                    final String hardisk = hdd.getText().toString();
                    final String memory1 = ram.getText().toString();
                    final String vga1 = vga.getText().toString();
                    final String lan1 = lan.getText().toString();
                    final String sondcard = sound_card.getText().toString();
                    final String keyboard1 = keyboard.getText().toString();
                    final String mouse1 = mouse.getText().toString();
                    final String casing1 = casing.getText().toString();
                    final String monitor1 = monitor.getText().toString();
                    final String power_supply = power_suply.getText().toString();
                    final String asis = asisten;
                    if (checkbox_baik.isChecked()){
                        kon_prosesor = checkbox_baik.getText().toString();
                    }else if (checkBox_masalah.isChecked()){
                        kon_prosesor = checkBox_masalah.getText().toString();
                    }
                    if (checkbox_baik1.isChecked()){
                        kon_hdd = checkbox_baik1.getText().toString();
                    }else if (checkBox_masalah1.isChecked()){
                        kon_hdd = checkBox_masalah1.getText().toString();
                    }
                    if (checkbox_baik2.isChecked()){
                        kon_ram = checkbox_baik1.getText().toString();
                    }else if (checkBox_masalah2.isChecked()){
                        kon_ram = checkBox_masalah2.getText().toString();
                    }

                    if (checkbox_baik3.isChecked()){
                        kon_vga = checkbox_baik3.getText().toString();
                    }else if (checkBox_masalah3.isChecked()){
                        kon_vga = checkBox_masalah3.getText().toString();
                    }
                    if (checkbox_baik4.isChecked()){
                        kon_lan = checkbox_baik4.getText().toString();
                    }else if (checkBox_masalah4.isChecked()){
                        kon_lan = checkBox_masalah4.getText().toString();
                    }
                    if (checkbox_baik5.isChecked()){
                        kon_sound = checkbox_baik5.getText().toString();
                    }else if (checkBox_masalah5.isChecked()){
                        kon_sound = checkBox_masalah5.getText().toString();
                    }
                    if (checkbox_baik6.isChecked()){
                        kon_keyboard = checkbox_baik6.getText().toString();
                    }else if (checkBox_masalah6.isChecked()){
                        kon_keyboard = checkBox_masalah6.getText().toString();
                    }
                    if (checkbox_baik7.isChecked()){
                        kon_mouse = checkbox_baik7.getText().toString();
                    }else if (checkBox_masalah7.isChecked()){
                        kon_mouse = checkBox_masalah7.getText().toString();
                    }
                    if (checkbox_baik8.isChecked()){
                        kon_power = checkbox_baik8.getText().toString();
                    }else if (checkBox_masalah8.isChecked()){
                        kon_power = checkBox_masalah8.getText().toString();
                    }
                    if (checkbox_baik9.isChecked()){
                        kon_monitor = checkbox_baik9.getText().toString();
                    }else if (checkBox_masalah9.isChecked()){
                        kon_monitor = checkBox_masalah9.getText().toString();
                    }
                    if (checkbox_baik10.isChecked()){
                        kon_casing = checkbox_baik10.getText().toString();
                    }else if (checkBox_masalah10.isChecked()){
                        kon_casing = checkBox_masalah10.getText().toString();
                    }

                    if ((checkbox_baik.isChecked() == false) && (checkBox_masalah.isChecked() == false) ||
                            (checkbox_baik1.isChecked() == false) && (checkBox_masalah1.isChecked() == false) ||
                            (checkbox_baik2.isChecked() == false) && (checkBox_masalah2.isChecked() == false) ||
                            (checkbox_baik3.isChecked() == false) && (checkBox_masalah3.isChecked() == false) ||
                            (checkbox_baik4.isChecked() == false) && (checkBox_masalah4.isChecked() == false) ||
                            (checkbox_baik5.isChecked() == false) && (checkBox_masalah5.isChecked() == false) ||
                            (checkbox_baik6.isChecked() == false) && (checkBox_masalah6.isChecked() == false) ||
                            (checkbox_baik7.isChecked() == false) && (checkBox_masalah7.isChecked() == false) ||
                            (checkbox_baik8.isChecked() == false) && (checkBox_masalah8.isChecked() == false) ||
                            (checkbox_baik9.isChecked() == false) && (checkBox_masalah9.isChecked() == false) ||
                            (checkbox_baik10.isChecked() == false) && (checkBox_masalah10.isChecked() == false)){
                        Toast.makeText(context,"harap cek maintennace dengan benar",Toast.LENGTH_LONG).show();
                    } else {
                    /*maintenance(id_komputer,prosesor1,hardisk,memory1,vga1,lan1,sondcard,
                            keyboard1,mouse1,casing1,monitor1,power_supply,
                            kon_prosesor,kon_hdd,kon_ram,kon_vga,kon_lan,kon_sound,kon_keyboard,kon_mouse,
                            kon_power,kon_monitor,kon_casing,asis);*/

                        final ProgressDialog loading = ProgressDialog.show(context, "MAINTENANCE...", "Please wait...", false, false);
                        StringRequest postRequest = new StringRequest(Request.Method.POST, config.MAINTENANCE_HARD,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        loading.dismiss();
                                        Toast.makeText(context, s.toString(), Toast.LENGTH_LONG).show();
                                        System.out.println("tambah " + s);
                                        notifyDataSetChanged();
                                        checkbox_baik.setChecked(false);
                                        checkBox_masalah.setChecked(false);
                                        checkbox_baik1.setChecked(false);
                                        checkBox_masalah1.setChecked(false);
                                        checkbox_baik2.setChecked(false);
                                        checkBox_masalah2.setChecked(false);
                                        checkbox_baik3.setChecked(false);
                                        checkBox_masalah3.setChecked(false);
                                        checkbox_baik4.setChecked(false);
                                        checkBox_masalah4.setChecked(false);
                                        checkbox_baik5.setChecked(false);
                                        checkBox_masalah5.setChecked(false);
                                        checkbox_baik6.setChecked(false);
                                        checkBox_masalah6.setChecked(false);
                                        checkbox_baik7.setChecked(false);
                                        checkBox_masalah7.setChecked(false);
                                        checkbox_baik8.setChecked(false);
                                        checkBox_masalah8.setChecked(false);
                                        checkbox_baik9.setChecked(false);
                                        checkBox_masalah9.setChecked(false);
                                        checkbox_baik10.setChecked(false);
                                        checkBox_masalah10.setChecked(false);
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
                                params.put(config.KEY_ID_asis, asis);
                                params.put(config.KEY_KONDISI_PROSESOR, kon_prosesor);
                                params.put(config.KEY_KONDISI_RAM, kon_ram);
                                params.put(config.KEY_KONDISI_HDD, kon_hdd);
                                params.put(config.KEY_KONDISI_VGA, kon_vga);
                                params.put(config.KEY_KONDISI_LAN, kon_lan);
                                params.put(config.KEY_KONDISI_SOUNDCARD, kon_sound);
                                params.put(config.KEY_KONDISI_KEYBOARD, kon_keyboard);
                                params.put(config.KEY_KONDISI_MOUSE, kon_mouse);
                                params.put(config.KEY_KONDISI_POWER, kon_power);
                                params.put(config.KEY_KONDISI_MONITOR, kon_monitor);
                                params.put(config.KEY_KONDISI_CASING, kon_casing);
                                params.put(config.KEY_ID_KOM, id_komputer);
                                params.put(config.KEY_PROSESOR, prosesor1);
                                params.put(config.KEY_HDD, hardisk);
                                params.put(config.KEY_RAM, memory1);
                                params.put(config.KEY_VGA, vga1);
                                params.put(config.KEY_LAN, lan1);
                                params.put(config.KEY_SOUNDCARD, sondcard);
                                params.put(config.KEY_KEYBOARD, keyboard1);
                                params.put(config.KEY_MOUSE, mouse1);
                                params.put(config.KEY_POWER, power_supply);
                                params.put(config.KEY_MONITOR, monitor1);
                                params.put(config.KEY_CASING, casing1);

                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(postRequest);
                    }
                }
            });
            }
    }

}