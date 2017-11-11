package com.acer.e_maintenance.activity;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.adapter.Hardware_adapter;
import com.acer.e_maintenance.adapter.adapter_detail_software;
import com.acer.e_maintenance.koneksi.JSONParser;
import com.acer.e_maintenance.koneksi.NetworkUtils;
import com.acer.e_maintenance.oop.Item;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class detail_software extends AppCompatActivity {
    private List<Item> list_soft = new ArrayList<>();
    private adapter_detail_software mAdapter;

    private RecyclerView recyclerView_detail;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter soft_adapter;
    private WifiManager wfm;
    private ConnectivityManager cntm;

    private ProgressDialog mProgressDialog;
    String txtkode;
    String url = "http://mydeveloper.id/maintenance/detail_software.php?id_komputer=";
    String id_kom_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_software);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        /*toolbar1.setTitleTextColor(Color.WHITE);
        toolbar1.setSubtitleTextColor(Color.WHITE);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DETAIL SOFTWARE KOMPUTER");

        final Bundle bundle = getIntent().getExtras();
        id_kom_detail = bundle.getString("id");
        Toast.makeText(this,"kode cpu " + id_kom_detail,Toast.LENGTH_LONG).show();

        recyclerView_detail = (RecyclerView) findViewById(R.id.list_detail_software);
        mAdapter = new adapter_detail_software((ArrayList<Item>) list_soft);
        recyclerView_detail.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView_detail.setLayoutManager(layoutManager);
        loadData();
    }


    private void loadData() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url + id_kom_detail, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    //JSONObject emedded = response.getJSONObject("_embedded");
                    JSONArray episodes = response.getJSONArray("list_detail");
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
                    recyclerView_detail.setAdapter(mAdapter);
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

        Volley.newRequestQueue(this).add(jsonObjReq);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        if (item.getItemId() == R.id.load) {
            list_soft.clear();
            list_soft.isEmpty();
            loadData();
                //
                //return true;
            }else {
                Toast.makeText(detail_software.this, "tidak ada software terupdate", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.load).setVisible(true);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }
}
