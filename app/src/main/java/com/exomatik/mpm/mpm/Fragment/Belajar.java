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

import com.exomatik.mpm.mpm.Activity.BuatPembelajaran;
import com.exomatik.mpm.mpm.Activity.DetailKegiatan;
import com.exomatik.mpm.mpm.Adapter.AdapterBelajar;
import com.exomatik.mpm.mpm.Adapter.ItemClickSupport;
import com.exomatik.mpm.mpm.Adapter.MyAdapter;
import com.exomatik.mpm.mpm.Adapter.RecyclerKegiatan;
import com.exomatik.mpm.mpm.CustomDialog.DialogTambahKegiatan;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Model.ModelBelajar;
import com.exomatik.mpm.mpm.Model.ModelKegiatan;
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
    private ArrayList<ModelBelajar> listBelajar = new ArrayList();
    private RecyclerView rcBelajar;
    private UserPreference userPreference;
    private View view;
    RecyclerView.LayoutManager layoutManager;
    private ShimmerLayout shimmerLoad;
    private TextView textNothing;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        view = paramLayoutInflater.inflate(R.layout.content_belajar, paramViewGroup, false);

        rcBelajar = ((RecyclerView) this.view.findViewById(R.id.rc_belajar));
        btnAddBelajar = ((CircleImageView) this.view.findViewById(R.id.btn_tambah_belajar));
        shimmerLoad = (ShimmerLayout) view.findViewById(R.id.shimmer_load);
        textNothing = (TextView) view.findViewById(R.id.text_nothing);

        shimmerLoad.startShimmerAnimation();
        userPreference = new UserPreference(getActivity());
        rcBelajar.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rcBelajar.setLayoutManager(layoutManager);

        getDataBelajar();
        if ((userPreference.getKEY_USER() != null) && (this.userPreference.getKEY_USER().toString().equals("Admin"))) {
            btnAddBelajar.setVisibility(View.VISIBLE);
        }
        btnAddBelajar.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                BuatPembelajaran.dataBelajar = null;
                startActivity(new Intent(getActivity(), BuatPembelajaran.class));
                getActivity().finish();
            }
        });
        return view;
    }

        ValueEventListener valueEventListener = new ValueEventListener() {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            Toast.makeText(Belajar.this.getActivity(), paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
            if (paramAnonymousDataSnapshot.exists()) {
                Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator.hasNext()) {
                    ModelBelajar localModelBelajar = (ModelBelajar) ((DataSnapshot) localIterator.next()).getValue(ModelBelajar.class);
                    listBelajar.add(new ModelBelajar(localModelBelajar.getNama(), localModelBelajar.getIsi(), localModelBelajar.getDesc(), true));
                }

                rcBelajar.setVisibility(View.VISIBLE);
                shimmerLoad.setVisibility(View.GONE);
                shimmerLoad.stopShimmerAnimation();
                AdapterBelajar adapter = new AdapterBelajar(listBelajar, getActivity());
                rcBelajar.setAdapter(adapter);
            }
            else {
                textNothing.setVisibility(View.VISIBLE);
                shimmerLoad.setVisibility(View.GONE);
                shimmerLoad.stopShimmerAnimation();
            }
        }
    };

    private void getDataBelajar() {
        FirebaseDatabase.getInstance().getReference("pembelajaran").addListenerForSingleValueEvent(this.valueEventListener);
    }
}