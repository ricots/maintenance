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
import android.widget.Toast;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.adapter.adapter_list_tindak_hard;
import com.acer.e_maintenance.adapter.adapter_list_tindak_soft;
import com.acer.e_maintenance.koneksi.config;
import com.acer.e_maintenance.oop.Item_tindak_hard;
import com.acer.e_maintenance.oop.Item_tindak_soft;
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

public class tindak_lanjut_soft extends Fragment implements View.OnClickListener{
    RecyclerView recyle_tindak;
    private List<Item_tindak_soft> list_soft_kom = new ArrayList<>();
    private adapter_list_tindak_soft mAdapter;

    private RecyclerView.LayoutManager layoutManager;
    String asisten;
    SharedPreferences sp;
    RequestQueue requestQueue;
    EditText tgl_tindak;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tindak_lanjut_soft, container, false);
        sp = getActivity().getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        asisten = sp.getString(config.EMAIL_SHARED_PREF, "Not Available");
        recyle_tindak = (RecyclerView) v.findViewById(R.id.recyle_tindak_soft);
        mAdapter = new adapter_list_tindak_soft((ArrayList<Item_tindak_soft>) list_soft_kom);
        recyle_tindak.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyle_tindak.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(getActivity());
        //loadData();

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        tgl_tindak = (EditText) v.findViewById(R.id.tgl_tindak_soft);
        tgl_tindak.setInputType(InputType.TYPE_NULL);
        setDateTimeField();
        tgl_tindak.addTextChangedListener(new TextWatcher() {
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
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                config.LIST_TINDAK_SOFT + tgl_tindak.getText().toString() + "&id_asisten="
                        + asisten, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray episodes = response.getJSONArray("list_tindak_lanjut");
                    for (int i = 0; i < episodes.length(); i++) {

                        JSONObject episode = episodes.getJSONObject(i);
                        String id_kom = episode.getString(config.KEY_ID_KOM_SOFT);
                        String nama_kom = episode.getString(config.KEY_NAMA_SOFT);
                        String ket_tindak = episode.getString(config.KEY_KETERANGAN_TINDAK);
                        String ket = episode.getString(config.KEY_KET_SOFT);
                        Item_tindak_soft lostItem = new Item_tindak_soft(
                                id_kom, nama_kom, ket_tindak, ket
                        );
                        list_soft_kom.add(lostItem);
                        //mAdapter.notifyDataSetChanged();
                    }
                    recyle_tindak.setAdapter(mAdapter);
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
        tgl_tindak.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tgl_tindak.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tgl_tindak.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View v) {
        if(v == tgl_tindak) {
            toDatePickerDialog.show();
        }

    }
}
