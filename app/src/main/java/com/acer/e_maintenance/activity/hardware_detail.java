package com.acer.e_maintenance.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.e_maintenance.R;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class hardware_detail extends AppCompatActivity implements View.OnClickListener{
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    TextView prosesor,ram,hdd,monitor,casing,vga,lan,power_suply,sound_card,mouse,keyboard,id_kom;
    String id_kom_detail;
    String url_list = "http://mydeveloper.id/maintenance/list_detail_komputer.php?id_komputer=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_detail);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        /*toolbar1.setTitleTextColor(Color.WHITE);
        toolbar1.setSubtitleTextColor(Color.WHITE);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DETAIL HARDWARE KOMPUTER");
        id_kom = (TextView) findViewById(R.id.id_kom_detail);
        prosesor = (TextView) findViewById(R.id.prosesor);
        ram = (TextView) findViewById(R.id.ram);
        hdd = (TextView) findViewById(R.id.hdd);
        monitor = (TextView) findViewById(R.id.monitor);
        casing = (TextView) findViewById(R.id.casing);
        vga = (TextView) findViewById(R.id.vga);
        lan = (TextView) findViewById(R.id.lan);
        power_suply = (TextView) findViewById(R.id.power_supply);
        sound_card = (TextView) findViewById(R.id.sound_card);
        mouse = (TextView) findViewById(R.id.mouse);
        keyboard = (TextView) findViewById(R.id.keyboard);

        final Bundle bundle = getIntent().getExtras();
        id_kom_detail = bundle.getString("id");
        Toast.makeText(this,"kode cpu " + id_kom_detail,Toast.LENGTH_LONG).show();

        getData();
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
    }


    public void getData() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url_list + id_kom_detail, new Response.Listener<JSONObject>() {

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:
                Intent in = new Intent(hardware_detail.this,update_hardware.class);
                Bundle bundle = new Bundle();
                bundle.putString("kode",id_kom_detail);
                in.putExtras(bundle);
                startActivity(in);
                break;
            case R.id.fab2:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("apakah anda yakin ingin menghapus ?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                delete();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                //Showing the alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    public void delete() {
        final ProgressDialog loading = ProgressDialog.show(this,"Delete Data", "Please wait...",false,false);

        StringRequest hapusRequest = new StringRequest(Request.Method.GET, config.DELETE_HARDWARE + id_kom_detail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        //item_et.setText("");
                        Toast.makeText(getApplicationContext(),
                                "delete sukses",
                                Toast.LENGTH_SHORT).show();
                        System.out.println("erornya " +response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(config.KEY_ID_KOM,id_kom_detail);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(hapusRequest);

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
            prosesor.setText("");
            hdd.setText("");
            ram.setText("");
            vga.setText("");
            lan.setText("");
            sound_card.setText("");
            keyboard.setText("");
            mouse.setText("");
            power_suply.setText("");
            monitor.setText("");
            casing.setText("");
            getData();
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
