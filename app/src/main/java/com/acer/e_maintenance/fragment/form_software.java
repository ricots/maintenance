package com.acer.e_maintenance.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.adapter.adapter_maintenace;
import com.acer.e_maintenance.adapter.adapter_maintenance_soft;
import com.acer.e_maintenance.koneksi.config;
import com.acer.e_maintenance.oop.Item;
import com.acer.e_maintenance.oop.Item_hardware;
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
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class form_software extends Fragment {
    private LinearLayoutManager layoutManager;
    private List<Item_komputer> list_soft = new ArrayList<>();
    private adapter_maintenance_soft mAdapter;
    private RecyclerView.Adapter soft_adapter;
    String asisten;
    SharedPreferences sp;
    private RecyclerView recyclerView_soft;
    private RequestQueue requestQueue;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_form_software, container, false);
        sp = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        asisten = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");
        Toast.makeText(getActivity(),"kode asisten " + asisten,Toast.LENGTH_LONG).show();
        setHasOptionsMenu(true);
        recyclerView_soft = (RecyclerView) v.findViewById(R.id.recyle_maintenace_soft);
        mAdapter = new adapter_maintenance_soft((ArrayList<Item_komputer>) list_soft);
        recyclerView_soft.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_soft.setLayoutManager(layoutManager);
        //recyclerView_soft.setAdapter(mAdapter);
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.getCache().clear();
        list_soft.clear();
        loadData();
        return v;
    }

    private void loadData() {
        /*StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                config.URL_SOFT + asisten, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
                    JSONObject sf = jsonObject.getJSONObject("detail_software");
                    JSONArray list_software = sf.getJSONArray("aplikasi1-01");
                    for (int i = 0; i < list_software.length(); i++) {

                        JSONObject software = list_software.getJSONObject(i);
                        String soft = software.getString("id_software");
                        String nm_soft = software.getString("nama_software");

                        Item lostItem = new Item(
                                soft,nm_soft
                        );

                        list_soft.add(lostItem);
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });*/

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                config.DAFTAR_HARDWARE + asisten, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray episodes = response.getJSONArray("list_detail");
                    for (int i = 0; i < episodes.length(); i++) {

                        JSONObject episode = episodes.getJSONObject(i);
                        String id_kom = episode.getString("id_komputer");

                        Item_komputer lostItem = new Item_komputer(
                                id_kom
                        );
                        list_soft.add(lostItem);
                        mAdapter.notifyDataSetChanged();
                    }
                    recyclerView_soft.setAdapter(mAdapter);
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
}
