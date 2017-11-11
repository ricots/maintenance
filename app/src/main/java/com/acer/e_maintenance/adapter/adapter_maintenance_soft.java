package com.acer.e_maintenance.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.activity.check_maintenance_software;
import com.acer.e_maintenance.koneksi.config;
import com.acer.e_maintenance.oop.Item;
import com.acer.e_maintenance.oop.Item_komputer;
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
import java.util.List;
import java.util.Map;

public class adapter_maintenance_soft extends RecyclerView.Adapter<adapter_maintenance_soft.DataObjectHolder> {
    private ArrayList<Item_komputer> mDataset;
    Context context;
    private static int currentPosition = 0;
    String id_komputer;

    public adapter_maintenance_soft(ArrayList<Item_komputer> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public adapter_maintenance_soft.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_maintence_soft, parent, false);
        adapter_maintenance_soft.DataObjectHolder dataObjectHolder = new adapter_maintenance_soft.DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final adapter_maintenance_soft.DataObjectHolder holder, final int position) {

      /*  if (currentPosition == position) {

        }*/

        holder.form_kode_komputer_soft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("idkomnya",0);
                SharedPreferences.Editor spe = sp.edit();
                id_komputer = holder.form_kode_komputer_soft.getText().toString();
                spe.putString("idkomnya",id_komputer);
                spe.commit();

                final Dialog dialog = new Dialog(context);
                //dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                ///dialog.setTitle("");
                dialog.setContentView(R.layout.activity_check_maintenance_software);
                //dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.keluar);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.imgkeluar);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                final List<Item> list_soft = new ArrayList<>();
                final adapter_check mAdapter;
                RecyclerView.LayoutManager layoutManager;
                final RecyclerView recyle_check = (RecyclerView) dialog.findViewById(R.id.recyle_check);
                mAdapter = new adapter_check((ArrayList<Item>) list_soft);
                recyle_check.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(context);
                recyle_check.setLayoutManager(layoutManager);
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.getCache().clear();
                list_soft.clear();
                /*final TextView id_idsoft = (TextView) dialog.findViewById(R.id.id_idsof_maint);
                final CheckBox check_ada = (CheckBox) dialog.findViewById(R.id.check_ada);
                final CheckBox check_tidak = (CheckBox) dialog.findViewById(R.id.check_tidak);*/
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        config.DETAIL_SOFTWARE_RUANG + id_komputer, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //JSONObject emedded = response.getJSONObject("_embedded");
                            JSONArray episodes = response.getJSONArray("detail_software");
                            for (int i = 0; i < episodes.length(); i++) {

                                JSONObject episode = episodes.getJSONObject(i);
                                String id_kom = episode.getString("id_komputer");
                                String id_soft = episode.getString("id_software");
                                String nm_soft = episode.getString("nama_software");

                                Item lostItem = new Item(
                                        id_kom,id_soft,nm_soft
                                );
                                list_soft.add(lostItem);
                                mAdapter.notifyDataSetChanged();
                            }
                            recyle_check.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })

                {
                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        try {
                            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                            if (cacheEntry == null) {
                                cacheEntry = new Cache.Entry();
                            }
                            final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                            final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                            long now = System.currentTimeMillis();
                            final long softExpire = now + cacheHitButRefreshed;
                            final long ttl = now + cacheExpired;
                            cacheEntry.data = response.data;
                            cacheEntry.softTtl = softExpire;
                            cacheEntry.ttl = ttl;
                            String headerValue;
                            headerValue = response.headers.get("Date");
                            if (headerValue != null) {
                                cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                            }
                            headerValue = response.headers.get("Last-Modified");
                            if (headerValue != null) {
                                cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                            }
                            cacheEntry.responseHeaders = response.headers;
                            final String jsonString = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers));
                            return Response.success(new JSONObject(jsonString), cacheEntry);
                        } catch (UnsupportedEncodingException e) {
                            return Response.error(new ParseError(e));
                        } catch (JSONException e) {
                            return Response.error(new ParseError(e));
                        }
                    }

                    @Override
                    protected void deliverResponse(JSONObject response) {
                        super.deliverResponse(response);
                    }

                    @Override
                    public void deliverError(VolleyError error) {
                        super.deliverError(error);
                    }

                    @Override
                    protected VolleyError parseNetworkError(VolleyError volleyError) {
                        return super.parseNetworkError(volleyError);
                    }
                };

                Volley.newRequestQueue(context).add(jsonObjReq);
                dialog.show();
                //dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.keluar);

            }
        });

        holder.form_kode_komputer_soft.setText(mDataset.get(position).getId_komputer());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        public TextView nama_software, id_detail, id_idsoft;
        Button form_kode_komputer_soft;
        Item item;
        String asisten;
        LinearLayout linearLayout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            form_kode_komputer_soft = (Button) itemView.findViewById(R.id.form_kode_komputer_soft);
            /*check_ada = (CheckBox) itemView.findViewById(R.id.check_ada);
            check_tidak = (CheckBox) itemView.findViewById(R.id.check_tidak);*/

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout_soft);

            //Toast.makeText(context, "kode asisten " + asisten, Toast.LENGTH_LONG).show();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
    }