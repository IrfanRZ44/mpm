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
import android.widget.Toast;

import com.exomatik.mpm.mpm.Activity.DetailKegiatan;
import com.exomatik.mpm.mpm.Adapter.ItemClickSupport;
import com.exomatik.mpm.mpm.Adapter.RecyclerKegiatan;
import com.exomatik.mpm.mpm.CustomDialog.DialogTambahKegiatan;
import com.exomatik.mpm.mpm.CustomDialog.DialogTambahQuran;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Model.ModelKegiatan;
import com.exomatik.mpm.mpm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import io.supercharge.shimmerlayout.ShimmerLayout;

import java.util.ArrayList;
import java.util.Iterator;

public class Kegiatan extends Fragment implements ItemClickSupport.OnItemClickListener {
    private CircleImageView btnAddKegiatan;
    private ArrayList<ModelKegiatan> listKegiatan = new ArrayList();
    private RecyclerView rcKegiatan;
    private UserPreference userPreference;
    private ShimmerLayout shimmerLoad;
    private View view;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        view = paramLayoutInflater.inflate(R.layout.content_kegiatan, paramViewGroup, false);

        this.rcKegiatan = ((RecyclerView) this.view.findViewById(R.id.rc_quran));
        this.btnAddKegiatan = ((CircleImageView) this.view.findViewById(R.id.btn_tambah_quran));
        this.shimmerLoad = (ShimmerLayout) this.view.findViewById(R.id.shimmer_load);

        shimmerLoad.startShimmerAnimation();
        this.userPreference = new UserPreference(getActivity());
        getDataKegiatan();
        ItemClickSupport.addTo(this.rcKegiatan).setOnItemClickListener(this);
        if ((this.userPreference.getKEY_USER() != null) && (this.userPreference.getKEY_USER().toString().equals("Admin"))) {
            this.btnAddKegiatan.setVisibility(View.VISIBLE);
        }
        this.btnAddKegiatan.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                DialogTambahKegiatan.dataEditKegiatan = null;
                DialogTambahKegiatan tambahKegiatan = DialogTambahKegiatan.newInstance();
                tambahKegiatan.setCancelable(false);
                tambahKegiatan.show(Kegiatan.this.getActivity().getFragmentManager(), "dialog");
            }
        });
        return view;
    }

    public void onItemClicked(RecyclerView paramRecyclerView, int paramInt, View paramView) {
        DetailKegiatan.detailKegiatan = new ModelKegiatan(((ModelKegiatan) this.listKegiatan.get(paramInt)).getNama().toString(), ((ModelKegiatan) this.listKegiatan.get(paramInt)).getTanggal().toString(), ((ModelKegiatan) this.listKegiatan.get(paramInt)).getTempat().toString(), ((ModelKegiatan) this.listKegiatan.get(paramInt)).getDesc().toString(), ((ModelKegiatan) this.listKegiatan.get(paramInt)).getFoto().toString());
        startActivity(new Intent(getActivity(), DetailKegiatan.class));
        getActivity().finish();
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            Toast.makeText(Kegiatan.this.getActivity(), paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
            if (paramAnonymousDataSnapshot.exists()) {
                Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator.hasNext()) {
                    ModelKegiatan localModelKegiatan = (ModelKegiatan) ((DataSnapshot) localIterator.next()).getValue(ModelKegiatan.class);
                    Kegiatan.this.listKegiatan.add(new ModelKegiatan(localModelKegiatan.getNama(), localModelKegiatan.getTanggal(), localModelKegiatan.getTempat(), localModelKegiatan.getDesc(), localModelKegiatan.getFoto()));
                }
                rcKegiatan.setVisibility(View.VISIBLE);
                shimmerLoad.setVisibility(View.GONE);
                shimmerLoad.stopShimmerAnimation();
                RecyclerKegiatan localRecyclerKegiatan = new RecyclerKegiatan(Kegiatan.this.listKegiatan, getActivity());
                LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(Kegiatan.this.getContext(), 1, false);
                Kegiatan.this.rcKegiatan.setLayoutManager(localLinearLayoutManager);
                Kegiatan.this.rcKegiatan.setNestedScrollingEnabled(false);
                Kegiatan.this.rcKegiatan.setAdapter(localRecyclerKegiatan);
            }
        }
    };

    private void getDataKegiatan() {
        FirebaseDatabase.getInstance().getReference("kegiatan").addListenerForSingleValueEvent(this.valueEventListener);
    }
}