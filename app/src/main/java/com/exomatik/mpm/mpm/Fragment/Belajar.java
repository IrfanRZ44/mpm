package com.exomatik.mpm.mpm.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Activity.ListPembelajaran;
import com.exomatik.mpm.mpm.Adapter.ItemClickSupport;
import com.exomatik.mpm.mpm.Adapter.RecyclerKategoriPembelajaran;
import com.exomatik.mpm.mpm.CustomDialog.DialogTambahKategoriBelajar;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class Belajar extends Fragment {
    private CircleImageView btnAddBelajar;
    private ArrayList<String> listBelajar = new ArrayList<String>();
    private RecyclerView rcBelajar;
    private UserPreference userPreference;
    private View view;
    private ShimmerLayout shimmerLoad;
    private TextView textNothing;
    private RecyclerKategoriPembelajaran adapter;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        view = paramLayoutInflater.inflate(R.layout.content_belajar, paramViewGroup, false);

        init();
        setAdapter();

        getDataBelajar();
        onClick();
        return view;
    }

    private void setAdapter() {
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity());
        rcBelajar.setHasFixedSize(true);
        rcBelajar.setLayoutManager(layoutManager);
        adapter = new RecyclerKategoriPembelajaran(listBelajar);
        rcBelajar.setAdapter(adapter);
    }

    private void init() {
        btnAddBelajar = ((CircleImageView) view.findViewById(R.id.btn_tambah_belajar));
        shimmerLoad = (ShimmerLayout) view.findViewById(R.id.shimmer_load);
        textNothing = (TextView) view.findViewById(R.id.text_nothing);
        rcBelajar = ((RecyclerView) view.findViewById(R.id.rc_belajar));

        shimmerLoad.startShimmerAnimation();
        userPreference = new UserPreference(getActivity());

        if ((userPreference.getKEY_USER() != null) && (userPreference.getKEY_USER().toString().equals("Admin"))) {
            btnAddBelajar.setVisibility(View.VISIBLE);
        }
    }

    private void onClick() {
        btnAddBelajar.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                DialogTambahKategoriBelajar.namaPembelajaran = null;
                DialogTambahKategoriBelajar tambahKegiatan = DialogTambahKategoriBelajar.newInstance();
                tambahKegiatan.setCancelable(false);
                tambahKegiatan.show(getActivity().getFragmentManager(), "dialog");
            }
        });

        ItemClickSupport.addTo(rcBelajar).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView paramRecyclerView, int position, View paramView) {
                ListPembelajaran.kategoriPembelajaran = listBelajar.get(position);
                startActivity(new Intent(getActivity(), ListPembelajaran.class));
                getActivity().finish();
            }
        });

        ItemClickSupport.addTo(rcBelajar).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView paramRecyclerView, int paramInt, View paramView) {
                DialogTambahKategoriBelajar.namaPembelajaran = listBelajar.get(paramInt);
                DialogTambahKategoriBelajar tambahKegiatan = DialogTambahKategoriBelajar.newInstance();
                tambahKegiatan.setCancelable(false);
                tambahKegiatan.show(getActivity().getFragmentManager(), "dialog");

                return false;
            }
        });
    }

        ValueEventListener valueEventListener = new ValueEventListener() {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            Toast.makeText(getActivity(), paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
            if (paramAnonymousDataSnapshot.exists()) {
                Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator.hasNext()) {
                    listBelajar.add(((DataSnapshot) localIterator.next()).getKey());
                    adapter.notifyDataSetChanged();
                }
            }
            cekListKategori();
        }
    };

    private void cekListKategori(){
        if (listBelajar.size() == 0){
            textNothing.setVisibility(View.VISIBLE);
            shimmerLoad.setVisibility(View.GONE);
            shimmerLoad.stopShimmerAnimation();
        }
        else {
            rcBelajar.setVisibility(View.VISIBLE);
            textNothing.setVisibility(View.GONE);
            shimmerLoad.setVisibility(View.GONE);
            shimmerLoad.stopShimmerAnimation();
        }
    }

    private void getDataBelajar() {
        FirebaseDatabase.getInstance().getReference("pembelajaran").addListenerForSingleValueEvent(valueEventListener);
    }
}