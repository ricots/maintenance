package com.acer.e_maintenance.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.koneksi.config;

public class add_hardware extends AppCompatActivity {
    EditText input_prosesor, input_hdd, input_ram, input_vga, input_power, input_casing, input_monitor,
    input_mouse,input_keyboard, input_lan,input_sound_card;
    ProgressDialog PD;
    Button addhard;
    String asisten;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hardware);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TAMBAH HARDWARE KOMPUTER");
        PD = new ProgressDialog(this);
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);

        sp = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        asisten = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");
        Toast.makeText(this,"kode asisten " + asisten,Toast.LENGTH_LONG).show();

        input_prosesor = (EditText) findViewById(R.id.input_prosesor);
        input_hdd = (EditText) findViewById(R.id.input_hdd);
        input_ram = (EditText) findViewById(R.id.input_memory);
        input_vga = (EditText) findViewById(R.id.input_vga);
        input_power = (EditText) findViewById(R.id.input_power_supply);
        input_casing = (EditText) findViewById(R.id.input_casing);
        input_monitor = (EditText) findViewById(R.id.input_monitor);
        input_mouse = (EditText) findViewById(R.id.input_mouse);
        input_keyboard = (EditText) findViewById(R.id.input_keyboard);
        input_lan = (EditText) findViewById(R.id.input_lan);
        input_sound_card = (EditText) findViewById(R.id.input_soundcard);
        addhard = (Button) findViewById(R.id.addhard);
        addhard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_hard();
            }
        });
    }

    public void add_hard() {
        PD.show();
        final String proses = input_prosesor.getText().toString();
        final String lan = input_lan.getText().toString();
        final String vga = input_vga.getText().toString();
        final String sound = input_sound_card.getText().toString();
        final String keyboard = input_keyboard.getText().toString();
        final String mouse = input_mouse.getText().toString();
        final String casing = input_casing.getText().toString();
        final String monitor = input_monitor.getText().toString();
        final String hdd = input_hdd.getText().toString();
        final String ram = input_ram.getText().toString();
        final String power = input_power.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, config.ADD_HARDWARE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        Toast.makeText(add_hardware.this,"laporan " + response.toString(),Toast.LENGTH_LONG).show();
                        Log.d("laporan ",response.toString());
                        Toast.makeText(getApplicationContext(),
                                "berhasil simpan data",
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(config.KEY_ID_asis,asisten);
                params.put(config.KEY_PROSESOR,proses);
                params.put(config.KEY_HDD,hdd);
                params.put(config.KEY_RAM,ram);
                params.put(config.KEY_VGA,vga);
                params.put(config.KEY_LAN,lan);
                params.put(config.KEY_SOUNDCARD,sound);
                params.put(config.KEY_KEYBOARD,keyboard);
                params.put(config.KEY_MOUSE,mouse);
                params.put(config.KEY_POWER,power);
                params.put(config.KEY_MONITOR,monitor);
                params.put(config.KEY_CASING,casing);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(add_hardware.this);
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.load).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }
}
