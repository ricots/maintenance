package com.acer.e_maintenance.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.koneksi.config;
import com.acer.e_maintenance.oop.Item;
import com.acer.e_maintenance.oop.Item_data_soft;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class adapter_tombol_data_soft extends RecyclerView.Adapter<adapter_tombol_data_soft.DataObjectHolder> {
    private ArrayList<Item_komputer> mDataset;
    Context context;
    private static int currentPosition = 0;
    String tgl;

    public adapter_tombol_data_soft(ArrayList<Item_komputer> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public adapter_tombol_data_soft.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tombol_data_soft, parent, false);
        adapter_tombol_data_soft.DataObjectHolder dataObjectHolder = new adapter_tombol_data_soft.DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final adapter_tombol_data_soft.DataObjectHolder holder, final int position) {

      /*  if (currentPosition == position) {

        }*/

        holder.form_kode_komputer_soft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("kirim_tgl",0);
                SharedPreferences.Editor spe = sp.edit();
                tgl = sp.getString("kirim_tgl","");

                sp = context.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String asisten = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");

                final Dialog dialog = new Dialog(context);
                //dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                //dialog.setTitle("");
                dialog.setContentView(R.layout.pop_data_soft);
                //dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.keluar);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.imgkeluar);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                final List<Item_data_soft> list_soft = new ArrayList<>();
                final adapter_data_software mAdapter;
                RecyclerView.LayoutManager layoutManager;
                final RecyclerView recyle_check = (RecyclerView) dialog.findViewById(R.id.data_recyle_check);
                mAdapter = new adapter_data_software((ArrayList<Item_data_soft>) list_soft);
                recyle_check.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(context);
                recyle_check.setLayoutManager(layoutManager);
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.getCache().clear();
               JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        config.DATA_SOFTWARE + tgl + "&id_asisten=" + asisten, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //JSONObject emedded = response.getJSONObject("_embedded");
                            JSONArray episodes = response.getJSONArray("list_data");
                            for (int i = 0; i < episodes.length(); i++) {

                                JSONObject episode = episodes.getJSONObject(i);
                                String id_kom = episode.getString("id_komputer");
                                String id_soft = episode.getString("id_software");
                                String nm_soft = episode.getString("nama_software");
                                String kon_soft = episode.getString("kondisi_soft");

                                Item_data_soft lostItem = new Item_data_soft(
                                        id_kom,id_soft,nm_soft,kon_soft
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
        public TextView nama_software, id_detail, id_idsoft,kirim_tgl;
        Button form_kode_komputer_soft;
        Item item;
        String asisten;
        LinearLayout linearLayout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            form_kode_komputer_soft = (Button) itemView.findViewById(R.id.data_kode_komputer_soft);
            kirim_tgl = (TextView) itemView.findViewById(R.id.kirim_tgl);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout_data_soft);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
    }