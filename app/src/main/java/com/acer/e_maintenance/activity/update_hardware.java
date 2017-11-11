package com.acer.e_maintenance.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.koneksi.JSONParser;
import com.acer.e_maintenance.koneksi.NetworkUtils;
import com.acer.e_maintenance.koneksi.config;
import com.acer.e_maintenance.oop.Item;

public class update_hardware extends AppCompatActivity {
    private List<Item> list_detail_hard;
    private ProgressDialog mProgressDialog;
    String txtkode;
    String vga1,prosesor1,ram1,hdd1,monitor1,casing1,lan1,power_suply1,sound_card1,mouse1,keyboard1;
    String url = "http://mydeveloper.id/maintenance/detail_hardware.php?id_komputer=";
    EditText prosesor,ram,hdd,monitor,casing,vga,lan,power_suply,sound_card,mouse,keyboard,id_kom;
    Button updatehard;
    String url_list = "http://mydeveloper.id/maintenance/list_detail_komputer.php?id_komputer=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hardware);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        /*toolbar1.setTitleTextColor(Color.WHITE);
        toolbar1.setSubtitleTextColor(Color.WHITE);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("UPDATE HARDWARE KOMPUTER");

        prosesor = (EditText) findViewById(R.id.update_prosesor);
        ram = (EditText) findViewById(R.id.update_ram);
        hdd = (EditText) findViewById(R.id.update_hdd);
        monitor = (EditText) findViewById(R.id.update_monitor);
        casing = (EditText) findViewById(R.id.update_casing);
        vga = (EditText) findViewById(R.id.update_vga);
        lan = (EditText) findViewById(R.id.update_lan);
        power_suply = (EditText) findViewById(R.id.update_power_supply);
        sound_card = (EditText) findViewById(R.id.update_soundcard);
        mouse = (EditText) findViewById(R.id.update_mouse);
        keyboard = (EditText) findViewById(R.id.update_keyboard);

        final Bundle bundle = getIntent().getExtras();
        txtkode = bundle.getString("kode");
        Toast.makeText(this,"kode cpunya " + txtkode,Toast.LENGTH_LONG).show();

        updatehard = (Button) findViewById(R.id.updatehard);
        updatehard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_hard();
            }
        });

        mProgressDialog = new ProgressDialog(this);
        getData();

    }


    public void getData() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url_list + txtkode, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    //JSONObject emedded = response.getJSONObject("_embedded");
                    JSONArray episodes = response.getJSONArray("list_detail_komputer");
                    for (int i = 0; i < episodes.length(); i++) {

                        JSONObject episode = episodes.getJSONObject(i);
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


                        prosesor.setText(prosesor1);
                        hdd.setText(hard_disk1);
                        ram.setText(memory1);
                        vga.setText(vga1);
                        lan.setText(lan1);
                        sound_card.setText(sound_card1);
                        keyboard.setText(keyboard1);
                        mouse.setText(mouse1);
                        power_suply.setText(power_supplay1);
                        monitor.setText(monitor1);
                        casing.setText(casing1);
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

        Volley.newRequestQueue(this).add(jsonObjReq);

    }


    public void update_hard() {
        mProgressDialog.setMessage("silahkan tunggu...");
        mProgressDialog.show();
        final String proses2 = prosesor.getText().toString();
        final String lan2 = lan.getText().toString();
        final String vga2 = vga.getText().toString();
        final String sound2 = sound_card.getText().toString();
        final String keyboard2 = keyboard.getText().toString();
        final String mouse2 = mouse.getText().toString();
        final String casing2 = casing.getText().toString();
        final String monitor2 = monitor.getText().toString();
        final String hdd2 = hdd.getText().toString();
        final String ram2 = ram.getText().toString();
        final String power2 = power_suply.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, config.UPDATE_HARDWARE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"sukses update harware",Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                        /*Intent out = new Intent(update_hardware.this,detail_hardware.class);
                        startActivity(out);*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(config.KEY_PROSESOR,proses2);
                params.put(config.KEY_HDD,hdd2);
                params.put(config.KEY_RAM,ram2);
                params.put(config.KEY_VGA,vga2);
                params.put(config.KEY_LAN,lan2);
                params.put(config.KEY_SOUNDCARD,sound2);
                params.put(config.KEY_KEYBOARD,keyboard2);
                params.put(config.KEY_MOUSE,mouse2);
                params.put(config.KEY_POWER,power2);
                params.put(config.KEY_MONITOR,monitor2);
                params.put(config.KEY_CASING,casing2);
                params.put(config.KEY_ID_KOM,txtkode);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(update_hardware.this);
        requestQueue.add(postRequest);
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
