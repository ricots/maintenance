package com.acer.e_maintenance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acer.e_maintenance.oop.Item_komputer;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.oop.Item;

public class adapter_hardware extends RecyclerView.Adapter<adapter_hardware.ViewHolder> {
    private ImageLoader imageLoader;
    private Context context;
    List<Item_komputer> daftar;
    ArrayList<String> barcode;



    public adapter_hardware(List<Item_komputer> daftar, Context context){
        super();
        //Getting all the superheroes
        this.daftar = daftar;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_hardware, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Item_komputer kom =  daftar.get(position);

        holder.kode_cpu.setText(kom.getId_komputer());
        holder.item = kom;
    }

    @Override
    public int getItemCount() {
        return daftar.size();
    }

    public void removeItem(int position) {
        daftar.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView kode_cpu;
        Item_komputer item;
        public ViewHolder(View itemView) {
            super(itemView);

            kode_cpu = (TextView) itemView.findViewById(R.id.kode_cpu);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goDetail = new Intent(context, detail_hardware.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",kode_cpu.getText().toString());
                    goDetail.putExtras(bundle);
                    goDetail.putExtra(handler.KEY_ID_komputer, item.getId_komputer());
                    v.getContext().startActivity(goDetail);
                }
            });
        }*/
    }



}

}