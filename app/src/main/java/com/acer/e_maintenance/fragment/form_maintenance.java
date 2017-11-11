package com.acer.e_maintenance.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.e_maintenance.adapter.Hardware_adapter;
import com.acer.e_maintenance.adapter.adapter_maintenace;
import com.acer.e_maintenance.koneksi.config;
import com.acer.e_maintenance.oop.Item_hardware;
import com.acer.e_maintenance.oop.Item_komputer;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.acer.e_maintenance.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class form_maintenance extends Fragment {
    ExpandableRelativeLayout expandableLayout1;
    String url_list = "http://mydeveloper.id/maintenance/list_detail_hardware.php?id_asisten=";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<Item_hardware> list_hard = new ArrayList<>();
    private adapter_maintenace mAdapter;
    String asisten;
    SharedPreferences sp;
    Button form_kode_komputer;


    private RecyclerView recyclerView_hard;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.activity_form_maintenance, container, false);
           sp = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        asisten = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");
        Toast.makeText(getActivity(),"kode asisten " + asisten,Toast.LENGTH_LONG).show();
        setHasOptionsMenu(true);

        recyclerView_hard = (RecyclerView) v.findViewById(R.id.recyle_maintenace);
        mAdapter = new adapter_maintenace((ArrayList<Item_hardware>) list_hard);
        recyclerView_hard.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_hard.setLayoutManager(layoutManager);
        recyclerView_hard.setAdapter(mAdapter);
        loadData();
            return v;
    }


    private void loadData() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url_list + asisten, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    //JSONObject emedded = response.getJSONObject("_embedded");
                    JSONArray episodes = response.getJSONArray("list_detail");
                    for (int i = 0; i < episodes.length(); i++) {

                        JSONObject episode = episodes.getJSONObject(i);
                        String id_kom1 = episode.getString("id_komputer");
                        String prosesor1 = episode.getString("prosesor");
                        String hard_disk1 = episode.getString("hard_disk");
                        String memory1 = episode.getString("memory");
                        String vga1 = episode.getString("vga");
                        String lan1 = episode.getString("nic_lan");
                        String sound_card1 = episode.getString("sound_card");
                        String keyboard1 = episode.getString("keyboard");
                        String mouse1 = episode.getString("mouse");
                        String power_supplay1 = episode.getString("power_supply");
                        String monitor1 = episode.getString("monitor");
                        String casing1 = episode.getString("casing");

                        Item_hardware lostItem = new Item_hardware(
                                id_kom1,prosesor1,hard_disk1,memory1,vga1,lan1,sound_card1,
                                keyboard1,mouse1,power_supplay1,monitor1,casing1
                        );

                        list_hard.add(lostItem);
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


    /*public void tes(String keyword_ast){
        dbHandler = new DBHandler(getActivity());
        list_hard = dbHandler.getAlldetailHardware();
        DBHandler databaseAccess = DBHandler.getInstance(getActivity());
        List<String> mahasiswaList = databaseAccess.getid_asisten(keyword_ast);
        databaseAccess.close();
        hardware_adapter = new adapter_list_form_maintenance(list_hard,getActivity());
        Log.d(getClass().getSimpleName(), "mengambil data dari DB");
        recyclerView.setAdapter(hardware_adapter);
        hardware_adapter.notifyDataSetChanged();
        Log.d("isine", list_hard.toString());
        Toast.makeText(getActivity(),list_hard.toString(),Toast.LENGTH_LONG).show();
    }*/
   /* public void listChanger(String keyword_ast){
        DBHandler databaseAccess = DBHandler.getInstance(getActivity());
        //databaseAccess.open();
        List<String> mahasiswaList = databaseAccess.getid_asisten(keyword_ast);
        databaseAccess.close();

        hardware_adapter = new adapter_maintenance_hardware(mahasiswaList,getActivity());
        recyclerView.setAdapter(adapter);

    }*/


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

      /*  if (item.getItemId() == R.id.load){
            *//*recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycle_maintenance);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);*//*
            dbHandler = new DBHandler(getActivity());
            mahasiswaList = dbHandler.getAlldetailHardware();
            adapter = new adapter_maintenance_hardware(mahasiswaList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }*/
        return super.onOptionsItemSelected(item);
    }
}
