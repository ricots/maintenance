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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.koneksi.config;

public class add_software extends AppCompatActivity implements Spinner.OnItemSelectedListener{
    Spinner spin_soft;
    private JSONArray result;
    private ArrayList<String> soft;
    TextView id_soft;
    ImageButton newsoft,deletesoft;
    EditText input_newsoft;
    Button addsoft;
    ProgressDialog PD;
    SharedPreferences sp;
    String asisten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_software);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TAMBAH SOFTWARE");

        PD = new ProgressDialog(this);
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);

        sp = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        asisten = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");
        Toast.makeText(this,"kode asisten " + asisten,Toast.LENGTH_LONG).show();

        soft = new ArrayList<String>();
        id_soft = (TextView) findViewById(R.id.id_soft);
        spin_soft = (Spinner) findViewById(R.id.spin_soft);
        spin_soft.setOnItemSelectedListener(this);
        input_newsoft = (EditText) findViewById(R.id.input_newsoft);
        newsoft = (ImageButton) findViewById(R.id.newsoft);
        deletesoft = (ImageButton) findViewById(R.id.deletesoft);

        deletesoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_soft();
                delete_soft_kom();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        add_software.this, android.R.layout.simple_spinner_dropdown_item,
                        soft);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_soft.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                //getData();
            }
        });

        newsoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_newsoft.setEnabled(true);
                spin_soft.setEnabled(false);
                deletesoft.setVisibility(View.GONE);
            }
        });
        addsoft = (Button) findViewById(R.id.addsoft);
        addsoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_newsoft.getText().toString().equals("")){
                    add_softnew();
                }else {
                    add_soft();
                }
            }
        });

        getData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                add_software.this, android.R.layout.simple_spinner_dropdown_item,
                soft);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_soft.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getData(){
        //Creating a string request
        final ProgressDialog loading = ProgressDialog.show(add_software.this,"Loading Data", "Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(config.DAFTAR_SOFTWARE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(config.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getsoft(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(add_software.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                loading.dismiss();
                Toast.makeText(add_software.this,"silahkan coba lagi",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getsoft(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                soft.add(json.getString(config.KEY_SOFT));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        add_software.this, android.R.layout.simple_spinner_dropdown_item,
                soft);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_soft.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //Setting adapter to show the items in the spinner
        //spin_soft.setAdapter(new ArrayAdapter<String>(add_software.this, android.R.layout.simple_spinner_dropdown_item, soft));

    }

    private String getid_soft(int position){
        String alamat_kampus="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            alamat_kampus = json.getString(config.KEY_id_SOft);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return alamat_kampus;
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

    public void add_soft() {
        PD.show();
        final String idnya = id_soft.getText().toString();
        final String soft = input_newsoft.getText().toString();


        StringRequest postRequest = new StringRequest(Request.Method.POST, config.ADD_SOFTWARE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        Toast.makeText(add_software.this,"laporan " + response.toString(),Toast.LENGTH_LONG).show();
                        Log.d("laporan ",response.toString());
                        Toast.makeText(getApplicationContext(),
                                "berhasil simpan data",
                                Toast.LENGTH_SHORT).show();
                        input_newsoft.setText("");
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
                    params.put(config.KEY_ID_asis, asisten);
                    //params.put(config.KEY_id_SOft, idnya);
                    params.put(config.KEY_SOFT, soft);
                    return params;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(add_software.this);
        requestQueue1.add(postRequest);
    }

    public void add_softnew() {
        PD.show();
        final String idnya = id_soft.getText().toString();
        final String soft = input_newsoft.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, config.ADD_SOFTWARE_new,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        Toast.makeText(add_software.this,"laporan " + response.toString(),Toast.LENGTH_LONG).show();
                        Log.d("laporan ",response.toString());
                        Toast.makeText(getApplicationContext(),
                                "berhasil simpan data",
                                Toast.LENGTH_SHORT).show();
                        input_newsoft.setText("");
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
                params.put(config.KEY_ID_asis, asisten);
                params.put(config.KEY_id_SOft, idnya);
                //params.put(config.KEY_SOFT, soft);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(add_software.this);
        requestQueue.add(postRequest);
    }

    public void delete_soft() {
        PD.show();
        final String idnya = id_soft.getText().toString();

        //delete tbl_komputer_software
        StringRequest postRequest = new StringRequest(Request.Method.GET, config.DELETE_SOFT + idnya +
                "&id_asisten=" + asisten,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        Toast.makeText(add_software.this,"laporan " + response.toString(),Toast.LENGTH_LONG).show();
                        Log.d("laporan ",response.toString());
                        Toast.makeText(getApplicationContext(),
                                "berhasil simpan data",
                                Toast.LENGTH_SHORT).show();
                        input_newsoft.setText("");
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
                params.put(config.KEY_ID_asis, asisten);
                params.put(config.KEY_id_SOft, idnya);
                //params.put(config.KEY_SOFT, soft);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(add_software.this);
        requestQueue.add(postRequest);
    }

    public void delete_soft_kom(){
        PD.show();
        final String idnya = id_soft.getText().toString();

        StringRequest postRequest1 = new StringRequest(Request.Method.GET, config.DELETE_KOM_SOFT + idnya,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        Toast.makeText(add_software.this,"laporan " + response.toString(),Toast.LENGTH_LONG).show();
                        Log.d("laporan ",response.toString());
                        Toast.makeText(getApplicationContext(),
                                "berhasil simpan data",
                                Toast.LENGTH_SHORT).show();
                        input_newsoft.setText("");
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
                //params.put(config.KEY_ID_asis, asisten);
                params.put(config.KEY_id_SOft, idnya);
                //params.put(config.KEY_SOFT, soft);
                return params;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(add_software.this);
        requestQueue1.add(postRequest1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.load).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        id_soft.setText(getid_soft(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        id_soft.setText("");
    }
}
