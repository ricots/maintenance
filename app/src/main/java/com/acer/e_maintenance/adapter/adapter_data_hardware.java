package com.acer.e_maintenance.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.koneksi.config;
import com.acer.e_maintenance.oop.Item;
import com.acer.e_maintenance.oop.Item_data_hardware;
import com.acer.e_maintenance.oop.Item_hardware;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapter_data_hardware extends RecyclerView.Adapter<adapter_data_hardware.DataObjectHolder> {
    private ArrayList<Item_data_hardware> mDataset;
    private Context context;
    private static int currentPosition = 0;

    public adapter_data_hardware(ArrayList<Item_data_hardware> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public adapter_data_hardware.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_maintenance_hard, parent, false);
        adapter_data_hardware.DataObjectHolder dataObjectHolder = new adapter_data_hardware.DataObjectHolder(view);
        context = parent.getContext();
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final adapter_data_hardware.DataObjectHolder holder, final int position) {


        holder.btn.setText(mDataset.get(position).getId_komputer());
        holder.prosesor.setText(mDataset.get(position).getProsesor());
        holder.ram.setText(mDataset.get(position).getMemory());
        holder.hdd.setText(mDataset.get(position).getHardisk());
        holder.monitor.setText(mDataset.get(position).getMonitor());
        holder.casing.setText(mDataset.get(position).getCasing());
        holder.vga.setText(mDataset.get(position).getVga());
        holder.lan.setText(mDataset.get(position).getLan());
        holder.power_suply.setText(mDataset.get(position).getPower_supply());
        holder.sound_card.setText(mDataset.get(position).getSondcard());
        holder.mouse.setText(mDataset.get(position).getMouse());
        holder.keyboard.setText(mDataset.get(position).getKeyboard());
        holder.data_kon_casing.setText(mDataset.get(position).getKon_casing());
        holder.data_kon_hdd.setText(mDataset.get(position).getKon_hdd());
        holder.data_kon_keyboard.setText(mDataset.get(position).getKon_keyboard());
        holder.data_kon_lan.setText(mDataset.get(position).getKon_lan());
        holder.data_kon_monitor.setText(mDataset.get(position).getKon_monitor());
        holder.data_kon_mouse.setText(mDataset.get(position).getKon_mouse());
        holder.data_kon_prosesor.setText(mDataset.get(position).getKon_prosesor());
        holder.data_kon_ram.setText(mDataset.get(position).getKon_ram());
        holder.data_kon_sound.setText(mDataset.get(position).getKon_sound());
        holder.data_kon_vga.setText(mDataset.get(position).getKon_vga());
        holder.kon_power.setText(mDataset.get(position).getKon_power());
        holder.linearLayout.setVisibility(View.GONE);
        if (currentPosition == position) {
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.linearLayout.startAnimation(slideDown);
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView prosesor,ram,hdd,monitor,casing,vga,lan,power_suply,sound_card,mouse,keyboard,id_kom,id_asis;
        Item item;
        String asisten;
        SharedPreferences sp;
        Button btn,btn_submit;
        LinearLayout linearLayout;
        TextView data_kon_prosesor,data_kon_hdd,data_kon_ram,data_kon_vga,data_kon_lan,
                data_kon_sound ,data_kon_keyboard ,data_kon_mouse ,kon_power,data_kon_monitor,data_kon_casing;

        public DataObjectHolder(View itemView) {
            super(itemView);
            btn = (Button) itemView.findViewById(R.id.data_id_komputer);
            id_asis = (TextView) itemView.findViewById(R.id.form_id_asisten);
            id_kom = (TextView) itemView.findViewById(R.id.form_id_kom);
            prosesor = (TextView) itemView.findViewById(R.id.data_prosesor);
            ram = (TextView) itemView.findViewById(R.id.data_ram);
            hdd = (TextView) itemView.findViewById(R.id.data_hdd);
            monitor = (TextView) itemView.findViewById(R.id.data_monitor);
            casing = (TextView) itemView.findViewById(R.id.data_casing);
            vga = (TextView) itemView.findViewById(R.id.data_vga);
            lan = (TextView) itemView.findViewById(R.id.data_lan);
            power_suply = (TextView) itemView.findViewById(R.id.data_power_supply);
            sound_card = (TextView) itemView.findViewById(R.id.data_sound_card);
            mouse = (TextView) itemView.findViewById(R.id.data_mouse);
            keyboard = (TextView) itemView.findViewById(R.id.data_keyboard);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout_data);

            data_kon_prosesor = (TextView) itemView.findViewById(R.id.data_kon_prosesor);
            data_kon_hdd = (TextView) itemView.findViewById(R.id.data_kon_hdd);
            data_kon_ram = (TextView) itemView.findViewById(R.id.data_kon_ram);
            data_kon_vga = (TextView) itemView.findViewById(R.id.data_kon_vga);
            data_kon_lan = (TextView) itemView.findViewById(R.id.data_kon_lan);
            data_kon_sound = (TextView) itemView.findViewById(R.id.data_kon_sound_card);
            data_kon_keyboard = (TextView) itemView.findViewById(R.id.data_kon_keyboard);
            data_kon_mouse = (TextView) itemView.findViewById(R.id.data_kon_mouse);
            kon_power = (TextView) itemView.findViewById(R.id.data_kon_power_supply);
            data_kon_monitor = (TextView) itemView.findViewById(R.id.data_kon_monitor);
            data_kon_casing = (TextView) itemView.findViewById(R.id.data_kon_casing);

            }
    }

}