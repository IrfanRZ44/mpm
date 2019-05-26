package com.exomatik.mpm.mpm.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.CustomDialog.DialogTambahKegiatan;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Fragment.Kegiatan;
import com.exomatik.mpm.mpm.Model.ModelKegiatan;
import com.exomatik.mpm.mpm.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;

public class RecyclerKegiatan
        extends RecyclerView.Adapter<RecyclerKegiatan.bidangViewHolder> {
    private Context context;
    private ArrayList<ModelKegiatan> dataList;
    private Activity activity;
    private UserPreference userPreference;

    public RecyclerKegiatan(ArrayList<ModelKegiatan> paramArrayList, Activity activity) {
        this.dataList = paramArrayList;
        this.activity = activity;
        userPreference = new UserPreference(activity);
    }

    public int getItemCount() {
        if (this.dataList != null) {
            return this.dataList.size();
        }
        return 0;
    }

    public void onBindViewHolder(bidangViewHolder parambidangViewHolder, final int paramInt) {
        Uri localUri = Uri.parse(((ModelKegiatan) this.dataList.get(paramInt)).getFoto());
        Picasso.with(this.context).load(localUri).into(parambidangViewHolder.imageView);

        parambidangViewHolder.txtTitle.setText(((ModelKegiatan) this.dataList.get(paramInt)).getNama());
        parambidangViewHolder.txtTanggal.setText(((ModelKegiatan) this.dataList.get(paramInt)).getTanggal());
        parambidangViewHolder.txtLokasi.setText(((ModelKegiatan) this.dataList.get(paramInt)).getTempat());

        if (userPreference.getKEY_USER() != null){
            if (userPreference.getKEY_USER().toString().equals("Admin")){
                parambidangViewHolder.rlItemKegiatan.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DialogTambahKegiatan.dataEditKegiatan = new ModelKegiatan(dataList.get(paramInt).getNama()
                                , dataList.get(paramInt).getTanggal(), dataList.get(paramInt).getTempat()
                                , dataList.get(paramInt).getDesc(), dataList.get(paramInt).getFoto());
                        DialogTambahKegiatan tambahKegiatan = DialogTambahKegiatan.newInstance();
                        tambahKegiatan.setCancelable(false);
                        tambahKegiatan.show(activity.getFragmentManager(), "dialog");
                        return false;
                    }
                });
            }
        }
    }

    public bidangViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View localView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.list_kegiatan, paramViewGroup, false);
        this.context = paramViewGroup.getContext();
        return new bidangViewHolder(localView);
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txtLokasi;
        private TextView txtTanggal;
        private TextView txtTitle;
        private RelativeLayout rlItemKegiatan;

        public bidangViewHolder(View paramView) {
            super(paramView);
            this.txtTitle = ((TextView) paramView.findViewById(R.id.text_title));
            this.imageView = ((ImageView) paramView.findViewById(R.id.listImage));
            this.txtTanggal = ((TextView) paramView.findViewById(R.id.text_tanggal));
            this.txtLokasi = ((TextView) paramView.findViewById(R.id.text_lokasi));
            this.rlItemKegiatan = (RelativeLayout) paramView.findViewById(R.id.rl_item_kegiatan);
        }
    }
}