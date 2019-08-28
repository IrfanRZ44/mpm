package com.exomatik.mpm.mpm.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.mpm.mpm.Model.ModelKegiatan;
import com.exomatik.mpm.mpm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerGaleri extends RecyclerView.Adapter<RecyclerGaleri.bidangViewHolder> {
    private ArrayList<ModelKegiatan> dataList;
    private Context context;

    public RecyclerGaleri(ArrayList<ModelKegiatan> dataList) {
        this.dataList = dataList;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_galeri, parent, false);
        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bidangViewHolder holder, int position) {
//        holder.imageView.setImageResource(dataList.get(position).gambarKegiatan);
        Uri uri = Uri.parse(dataList.get(position).gambarKegiatan);
        Picasso.with(context).load(uri).into(holder.imageView);

        holder.txtTitle.setText(dataList.get(position).namaKegiatan);
        holder.txtTanggal.setText(dataList.get(position).tanggalKegiatan);
        holder.txtLokasi.setText(dataList.get(position).lokasiKegiatan);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle, txtTanggal, txtLokasi;
        private ImageView imageView;

        public bidangViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageView = (ImageView) itemView.findViewById(R.id.listImage);
            txtTanggal = (TextView) itemView.findViewById(R.id.text_tanggal);
            txtLokasi = (TextView) itemView.findViewById(R.id.text_lokasi);
        }
    }
}
