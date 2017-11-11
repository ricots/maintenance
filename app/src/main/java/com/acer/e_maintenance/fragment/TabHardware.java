package com.acer.e_maintenance.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.activity.add_hardware;
import com.acer.e_maintenance.activity.detail_software;
import com.acer.e_maintenance.adapter.Hardware_adapter;
import com.acer.e_maintenance.adapter.adapter_hardware;
import com.acer.e_maintenance.koneksi.config;
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

/**
 * Created by Admin on 5/25/2016.
 */
public class TabHardware extends Fragment {

    private List<Item_komputer> list_hard_kom = new ArrayList<>();
    private Hardware_adapter mAdapter;
    private ArrayList<Item_komputer> posts;

    private RecyclerView recyclerView_hrad;
    private RecyclerView.LayoutManager layoutManager;
    private WifiManager wfm;
    private ConnectivityManager cntm;
    SharedPreferences sp;

    private ProgressDialog mProgressDialog;
    String asisten;
    FloatingActionButton fab;
    String url_list = "http://mydeveloper.id/maintenance/list_detail_hardware.php?id_asisten=";
    private RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_hardware, container, false);
        setHasOptionsMenu(true);
        sp = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        asisten = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");
        //Toast.makeText(getActivity(), "kode asisten " + asisten, Toast.LENGTH_LONG).show();

        mProgressDialog = new ProgressDialog(getActivity());
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), add_hardware.class);
                startActivity(in);
            }
        });

        recyclerView_hrad = (RecyclerView) v.findViewById(R.id.list_hard);
        mAdapter = new Hardware_adapter((ArrayList<Item_komputer>) list_hard_kom);
        recyclerView_hrad.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 5);
        recyclerView_hrad.setLayoutManager(layoutManager);
        //list_hard = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        loadData();

        return v;
    }

    private void loadData() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url_list + asisten, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray episodes = response.getJSONArray("list_detail");
                    for (int i = 0; i < episodes.length(); i++) {

                        JSONObject episode = episodes.getJSONObject(i);
                        String id_kom = episode.getString("id_komputer");
                        Log.i("Provera Object", id_kom);

                        Item_komputer lostItem = new Item_komputer(
                                id_kom
                        );
                        list_hard_kom.add(lostItem);
                        mAdapter.notifyDataSetChanged();
                    }
                    recyclerView_hrad.setAdapter(mAdapter);
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

        Volley.newRequestQueue(getActivity()).add(jsonObjReq);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.load){
            list_hard_kom.clear();
            list_hard_kom.isEmpty();
            loadData();
            //
            //return true;
        }else {
            Toast.makeText(getActivity(), "tidak ada software terupdate", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

