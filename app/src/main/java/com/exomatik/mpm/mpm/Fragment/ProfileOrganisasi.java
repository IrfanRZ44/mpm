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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Activity.EditStruktur;
import com.exomatik.mpm.mpm.Activity.EditVisi;
import com.exomatik.mpm.mpm.Activity.MainActivity;
import com.exomatik.mpm.mpm.Adapter.MyAdapter;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Model.ModelStruktur;
import com.exomatik.mpm.mpm.Model.ModelVisi;
import com.exomatik.mpm.mpm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class ProfileOrganisasi extends Fragment {
    private TextView bendahara, bpo, dpo, sekertaris, anggotaHumas, anggotaPubdok, anggotaMinat;
    private RelativeLayout btnEditStruktur;
    private RelativeLayout btnLogout;
    private UserPreference mUserPreferences;
    private RecyclerView rcVisi;
    private ImageView btnEditVisi;
    private ArrayList<ModelVisi> listVisi = new ArrayList<ModelVisi>();
    RecyclerView.LayoutManager layoutManager;
    private ShimmerLayout shimmerLoad, shimmerLoad2;
    private ScrollView svStruktur;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        this.view = paramLayoutInflater.inflate(R.layout.content_profile, paramViewGroup, false);
        this.btnLogout = ((RelativeLayout) this.view.findViewById(R.id.btn_logout));
        this.btnEditStruktur = ((RelativeLayout) this.view.findViewById(R.id.btn_edit));
        this.dpo = ((TextView) this.view.findViewById(R.id.text_ketua2_dpo));
        this.bpo = ((TextView) this.view.findViewById(R.id.text_ketua2));
        this.sekertaris = ((TextView) this.view.findViewById(R.id.text_sekertaris2));
        this.bendahara = ((TextView) this.view.findViewById(R.id.text_bendahara2));
        this.anggotaHumas = ((TextView) this.view.findViewById(R.id.text_anggota_humas2));
        this.anggotaPubdok = ((TextView) this.view.findViewById(R.id.text_anggota_publik2));
        this.anggotaMinat = ((TextView) this.view.findViewById(R.id.text_anggota_minat2));
        this.rcVisi = (RecyclerView) this.view.findViewById(R.id.rc_visi);
        this.btnEditVisi = (ImageView) this.view.findViewById(R.id.img_edit);
        shimmerLoad = (ShimmerLayout) view.findViewById(R.id.shimmer_load);
        shimmerLoad2 = (ShimmerLayout) view.findViewById(R.id.shimmer_load2);
        svStruktur = (ScrollView) view.findViewById(R.id.sv_struktur);

        shimmerLoad.startShimmerAnimation();
        shimmerLoad2.startShimmerAnimation();
        rcVisi.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rcVisi.setLayoutManager(layoutManager);

        getDataStruktur();
        getDataVisi();
        this.mUserPreferences = new UserPreference(getContext());

        this.btnLogout.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                mUserPreferences.setKEY_NAME(null);
                mUserPreferences.setKEY_AGE(0);
                mUserPreferences.setKEY_PHONE(null);
                mUserPreferences.setKEY_USER(null);
                mUserPreferences.setKEY_JK(true);

                mUserPreferences = null;
                Toast.makeText(ProfileOrganisasi.this.getActivity(), "Berhasil Logout", Toast.LENGTH_LONG).show();
                ProfileOrganisasi.this.startActivity(new Intent(ProfileOrganisasi.this.getActivity(), MainActivity.class));
                ProfileOrganisasi.this.getActivity().finish();
            }
        });

        return this.view;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            Toast.makeText(ProfileOrganisasi.this.getActivity(), paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
            if (paramAnonymousDataSnapshot.exists()) {
                final ModelStruktur localModelStruktur = (ModelStruktur) paramAnonymousDataSnapshot.getValue(ModelStruktur.class);

                dpo.setText(localModelStruktur.getDpo().toString());
                bpo.setText(localModelStruktur.getBpo().toString());
                sekertaris.setText(localModelStruktur.getSekertaris().toString());
                bendahara.setText(localModelStruktur.getBendahara().toString());
                anggotaHumas.setText(localModelStruktur.getAnggotaHumas().toString());
                anggotaMinat.setText(localModelStruktur.getAnggotaMinat().toString());
                anggotaPubdok.setText(localModelStruktur.getAnggotaPubdok().toString());

                shimmerLoad2.setVisibility(View.GONE);
                shimmerLoad2.stopShimmerAnimation();
                svStruktur.setVisibility(View.VISIBLE);

                btnEditStruktur.setOnClickListener(new OnClickListener() {
                    public void onClick(View paramAnonymousView) {
                        EditStruktur.dataStruktur = new ModelStruktur(localModelStruktur.getBendahara(), localModelStruktur.getBpo()
                                , localModelStruktur.getDpo(), localModelStruktur.getSekertaris(), localModelStruktur.getAnggotaHumas()
                                , localModelStruktur.getAnggotaPubdok(), localModelStruktur.getAnggotaMinat());
                        startActivity(new Intent(getActivity(), EditStruktur.class));
                        getActivity().finish();
                    }
                });
            }
            else {
                dpo.setText("-");
                bpo.setText("-");
                bendahara.setText("-");
                sekertaris.setText("-");
                anggotaHumas.setText("-");
                anggotaPubdok.setText("-");
                anggotaMinat.setText("-");
                btnEditStruktur.setOnClickListener(new OnClickListener() {
                    public void onClick(View paramAnonymousView) {
                        startActivity(new Intent(getActivity(), EditStruktur.class));
                        getActivity().finish();
                    }
                });
            }
            if (mUserPreferences.getKEY_USER() != null) {
                if (mUserPreferences.getKEY_USER().toString().equals("Admin")) {
                    btnEditStruktur.setVisibility(View.VISIBLE);
                    btnEditVisi.setVisibility(View.VISIBLE);
                    btnLogout.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    ValueEventListener valueEventListener2 = new ValueEventListener() {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            Toast.makeText(ProfileOrganisasi.this.getActivity(), paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
            if (paramAnonymousDataSnapshot.exists()) {
                Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator.hasNext()) {
                    ModelVisi localModelVisi = (ModelVisi) ((DataSnapshot) localIterator.next()).getValue(ModelVisi.class);
                    listVisi.add(new ModelVisi(localModelVisi.getVisi(), localModelVisi.getTitleVisi(), true));
                }

                rcVisi.setVisibility(View.VISIBLE);
                shimmerLoad.setVisibility(View.GONE);
                shimmerLoad.stopShimmerAnimation();
                MyAdapter adapter = new MyAdapter(listVisi);
                rcVisi.setAdapter(adapter);

                btnEditVisi.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int a = 0; a < listVisi.size(); a++){
                            if (listVisi.get(a).getTitleVisi().toString().equals("visi")){
                                EditVisi.visi = listVisi.get(a).getVisi();
                            }
                            if (listVisi.get(a).getTitleVisi().toString().equals("misi")){
                                EditVisi.misi = listVisi.get(a).getVisi();
                            }
                        }
                        startActivity(new Intent(getActivity(), EditVisi.class ));
                        getActivity().finish();
                    }
                });
            }
            else {
                btnEditVisi.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), EditVisi.class ));
                        getActivity().finish();
                    }
                });
            }
        }
    };
    private View view;

    private void getDataStruktur() {
        FirebaseDatabase.getInstance().getReference("struktur").addListenerForSingleValueEvent(this.valueEventListener);
    }

    private void getDataVisi() {
        FirebaseDatabase.getInstance().getReference("visi").addListenerForSingleValueEvent(this.valueEventListener2);
    }
}
