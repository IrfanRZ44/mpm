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

import com.exomatik.mpm.mpm.Activity.DetailQuran;
import com.exomatik.mpm.mpm.Adapter.ItemClickSupport;
import com.exomatik.mpm.mpm.Adapter.ItemClickSupport.OnItemClickListener;
import com.exomatik.mpm.mpm.Adapter.RecyclerQuran;
import com.exomatik.mpm.mpm.CustomDialog.DialogTambahQuran;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Model.ModelQuran;
import com.exomatik.mpm.mpm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class Quran extends Fragment implements OnItemClickListener {
    private CircleImageView btnAddQuran;
    private ArrayList<ModelQuran> listQuran = new ArrayList();
    private RecyclerView rcQuran;
    private UserPreference userPreference;
    private View view;
    private ShimmerLayout shimmerLayout, shimmerLayout2;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        view = paramLayoutInflater.inflate(R.layout.content_quran, paramViewGroup, false);

        this.rcQuran = ((RecyclerView) this.view.findViewById(R.id.rc_quran));
        this.btnAddQuran = ((CircleImageView) this.view.findViewById(R.id.btn_tambah_quran));
        shimmerLayout = (ShimmerLayout) view.findViewById(R.id.shimmer_load);
        shimmerLayout2 = (ShimmerLayout) view.findViewById(R.id.shimmer_load2);

        shimmerLayout.startShimmerAnimation();
        shimmerLayout2.startShimmerAnimation();

        this.userPreference = new UserPreference(getActivity());

        getDataQuran();
        ItemClickSupport.addTo(this.rcQuran).setOnItemClickListener(this);
        if (this.userPreference.getKEY_USER() != null) {
            if (this.userPreference.getKEY_USER().toString().equals("Admin")){
                this.btnAddQuran.setVisibility(View.VISIBLE);
                this.btnAddQuran.setOnClickListener(new OnClickListener() {
                    public void onClick(View paramAnonymousView) {
                        DialogTambahQuran.dataEditQuran = null;
                        DialogTambahQuran localDialogTambahQuran = DialogTambahQuran.newInstance();
                        localDialogTambahQuran.setCancelable(false);
                        localDialogTambahQuran.show(Quran.this.getActivity().getFragmentManager(), "dialog");
                    }
                });
            }
        }
        return view;
    }

    public void onItemClicked(RecyclerView paramRecyclerView, int paramInt, View paramView) {
        DetailQuran.dataQuran = new ModelQuran(listQuran.get(paramInt).getJus(), listQuran.get(paramInt).getSurah(),
                listQuran.get(paramInt).getFile(), listQuran.get(paramInt).getUrutan());
        startActivity(new Intent(getActivity(), DetailQuran.class));
        getActivity().finish();
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            Toast.makeText(Quran.this.getActivity(), paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
            if (paramAnonymousDataSnapshot.exists()) {
                Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator.hasNext()) {
                    ModelQuran localQuran = (ModelQuran) ((DataSnapshot) localIterator.next()).getValue(ModelQuran.class);
                    listQuran.add(new ModelQuran(localQuran.getJus(), localQuran.getSurah(), localQuran.getFile(), localQuran.getUrutan()));
                }

                Collections.sort(listQuran);

                rcQuran.setVisibility(View.VISIBLE);
                shimmerLayout2.setVisibility(View.GONE);
                shimmerLayout.setVisibility(View.GONE);
                shimmerLayout.stopShimmerAnimation();
                shimmerLayout2.stopShimmerAnimation();

                RecyclerQuran localRcQuran= new RecyclerQuran(Quran.this.listQuran);
                LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(Quran.this.getContext(), 1, false);
                Quran.this.rcQuran.setLayoutManager(localLinearLayoutManager);
                Quran.this.rcQuran.setNestedScrollingEnabled(false);
                Quran.this.rcQuran.setAdapter(localRcQuran);
            }
        }
    };

    private void getDataQuran() {
        FirebaseDatabase.getInstance().getReference("quran").addListenerForSingleValueEvent(this.valueEventListener);
    }
}