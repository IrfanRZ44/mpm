package com.exomatik.mpm.mpm.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.mpm.mpm.Model.ModelImage;
import com.exomatik.mpm.mpm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class AdapterGridKegiatan extends BaseAdapter {
    private Context context;
    private ArrayList<ModelImage> dataImage;

    public AdapterGridKegiatan(Context context, ArrayList<ModelImage> dataImage) {
        this.context = context;
        this.dataImage = dataImage;
    }

    @Override
    public int getCount() {
        return dataImage.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.grid_image_kegiatan, null);

        ImageView img = (ImageView) v.findViewById(R.id.image_warna);
        TextView text = (TextView) v.findViewById(R.id.text_warna);

        Uri uri = Uri.parse(dataImage.get(position).image);
        Picasso.with(context).load(uri).into(img);
        text.setText(dataImage.get(position).nama);

        return v;
    }
}
