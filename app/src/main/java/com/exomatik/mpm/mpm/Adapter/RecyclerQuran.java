package com.exomatik.mpm.mpm.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.mpm.mpm.Model.ModelKegiatan;
import com.exomatik.mpm.mpm.Model.ModelQuran;
import com.exomatik.mpm.mpm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerQuran
        extends Adapter<RecyclerQuran.bidangViewHolder> {
    private Context context;
    private ArrayList<ModelQuran> dataList = new ArrayList<ModelQuran>();

    public RecyclerQuran(ArrayList<ModelQuran> paramArrayList) {
        this.dataList = paramArrayList;
    }

    public int getItemCount() {
        if (this.dataList != null) {
            return this.dataList.size();
        }
        return 0;
    }

    public void onBindViewHolder(bidangViewHolder parambidangViewHolder, int paramInt) {

        parambidangViewHolder.textNomor.setText(Integer.toString(dataList.get(paramInt).getUrutan()));
        parambidangViewHolder.textSurah.setText(dataList.get(paramInt).getSurah());
        parambidangViewHolder.textJus.setText("Jus - " + dataList.get(paramInt).getJus());
    }

    public bidangViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View localView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.list_quran, paramViewGroup, false);
        this.context = paramViewGroup.getContext();
        return new bidangViewHolder(localView);
    }

    public class bidangViewHolder
            extends ViewHolder {
        private TextView textNomor, textSurah, textJus;

        public bidangViewHolder(View paramView) {
            super(paramView);
            this.textNomor = ((TextView) paramView.findViewById(R.id.text_nomor));
            this.textSurah = ((TextView) paramView.findViewById(R.id.text_surah));
            this.textJus= ((TextView) paramView.findViewById(R.id.text_jus));
        }
    }
}