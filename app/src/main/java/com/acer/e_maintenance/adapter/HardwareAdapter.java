package com.acer.e_maintenance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import com.acer.e_maintenance.R;
import com.acer.e_maintenance.oop.Item;

/**
 * Created by PP on 6/18/2015.
 */
public class HardwareAdapter extends BaseAdapter {
    Context context;
    List<Item> listData;
    private static HardwareAdapter mInstance;

    public void onCreate() {
        mInstance = this;
    }

    public HardwareAdapter(Context context, List<Item> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        public TextView kode_cpu;
        private ImageView logo;
        ListView list;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_hardware, null);
            viewHolder = new ViewHolder();
            //holder = new ImageHolder();
            viewHolder.kode_cpu = (TextView) view.findViewById(R.id.kode_cpu);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            //holder = (ImageHolder)view.getTag();
        }
        //if(imageLoader==null);
        Item hard = listData.get(position);
        String kode = hard.getId_komputer();
        //String logo_kampus = city.getLogo();
        //Log.d("isine ", logo_kampus);


        //set Logo menggunakan library picasso lebih ringan untuk memory dg sistem cache

        viewHolder.kode_cpu.setText(kode);
              return view;

    }
}
