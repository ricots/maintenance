package com.acer.e_maintenance.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.adapter.adapter_data_hardware;
import com.acer.e_maintenance.adapter.adapter_list_tindak_hard;
import com.acer.e_maintenance.koneksi.config;
import com.acer.e_maintenance.oop.Item_data_hardware;
import com.acer.e_maintenance.oop.Item_tindak_hard;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class data_hardware extends Fragment implements View.OnClickListener{
    RecyclerView recyle_data_hard;
    private List<Item_data_hardware> list_data_hard = new ArrayList<>();
    private adapter_data_hardware mAdapter;

    private RecyclerView.LayoutManager layoutManager;
    String asisten;
    SharedPreferences sp;
    RequestQueue requestQueue;
    EditText tgl_data_hard;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_data_hardware, container, false);
        sp = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        asisten = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");

        recyle_data_hard = (RecyclerView) v.findViewById(R.id.recyle_data_hard);
        mAdapter = new adapter_data_hardware((ArrayList<Item_data_hardware>) list_data_hard);
        recyle_data_hard.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyle_data_hard.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(getActivity());

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        tgl_data_hard = (EditText) v.findViewById(R.id.tgl_data_hard);
        tgl_data_hard.setInputType(InputType.TYPE_NULL);
        setDateTimeField();
        tgl_data_hard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loadData();
            }
        });
        return v;
    }

    private void loadData() {
        toDatePickerDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                config.LIST_DATA_HARD + tgl_data_hard.getText().toString() + "&id_asisten="
                        + asisten, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray episodes = response.getJSONArray("list_maintenance");
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

                        String kon_pros = episode.getString(config.KEY_KONDISI_PROSESOR);
                        String kon_hdd = episode.getString(config.KEY_KONDISI_HDD);
                        String kon_ram = episode.getString(config.KEY_KONDISI_RAM);
                        String kon_vga = episode.getString(config.KEY_KONDISI_VGA);
                        String kon_lan = episode.getString(config.KEY_KONDISI_LAN);
                        String kon_sound = episode.getString(config.KEY_KONDISI_SOUNDCARD);
                        String kon_keyboard = episode.getString(config.KEY_KONDISI_KEYBOARD);
                        String kon_mouse = episode.getString(config.KEY_KONDISI_MOUSE);
                        String kon_power = episode.getString(config.KEY_KONDISI_POWER);
                        String kon_monitor = episode.getString(config.KEY_KONDISI_MONITOR);
                        String kon_casing = episode.getString(config.KEY_KONDISI_CASING);
                        Item_data_hardware lostItem = new Item_data_hardware(
                                id_kom1, prosesor1, hard_disk1, memory1,vga1,lan1,sound_card1,keyboard1,
                                mouse1,power_supplay1,monitor1,casing1,kon_pros,kon_hdd,kon_ram,kon_vga,kon_lan,
                                kon_sound,kon_keyboard,kon_mouse,kon_power,kon_monitor,kon_casing
                        );
                        list_data_hard.add(lostItem);
                        //mAdapter.notifyDataSetChanged();
                    }
                    recyle_data_hard.setAdapter(mAdapter);
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

    private void setDateTimeField() {
        tgl_data_hard.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tgl_data_hard.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tgl_data_hard.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View v) {
        if(v == tgl_data_hard) {
            toDatePickerDialog.show();
        }

    }
}
