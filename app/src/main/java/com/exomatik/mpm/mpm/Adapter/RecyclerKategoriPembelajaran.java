package com.exomatik.mpm.mpm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exomatik.mpm.mpm.R;

import java.util.ArrayList;

public class RecyclerKategoriPembelajaran
        extends Adapter<RecyclerKategoriPembelajaran.bidangViewHolder> {
    private Context context;
    private ArrayList<String> dataList;

    public RecyclerKategoriPembelajaran(ArrayList<String> paramArrayList) {
        this.dataList = paramArrayList;
    }

    public int getItemCount() {
        if (this.dataList != null) {
            return this.dataList.size();
        }
        return 0;
    }

    public void onBindViewHolder(bidangViewHolder parambidangViewHolder, int paramInt) {
        parambidangViewHolder.textNama.setText(dataList.get(paramInt));
    }

    public bidangViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View localView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.list_kategori_pembelajaran, paramViewGroup, false);
        this.context = paramViewGroup.getContext();
        return new bidangViewHolder(localView);
    }

    public class bidangViewHolder
            extends ViewHolder {
        private TextView textNama;

        public bidangViewHolder(View paramView) {
            super(paramView);
            this.textNama = ((TextView) paramView.findViewById(R.id.textNama));
        }
    }
}