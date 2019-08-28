package com.exomatik.mpm.mpm.Activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Adapter.AdapterGridKegiatan;
import com.exomatik.mpm.mpm.CustomDialog.CustomDialogKegiatan;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Model.ModelImage;
import com.exomatik.mpm.mpm.Model.ModelKegiatan;
import com.exomatik.mpm.mpm.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class GaleriKegiatan extends AppCompatActivity {
    private TextView textKegiatan, textPenjelasan, textTanggal, textLokasi;
    private GridView gridKegiatan;
    private ArrayList<ModelImage> dataImage;
    private ImageView back;
    private RelativeLayout btnTambahGaleri;
    public static ModelKegiatan dataKegiatan;
    private ShimmerLayout shimmerLoad, shimmerLoad2;
    private FirebaseAnalytics firebaseAnalytics;
    private UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeri_kegiatan);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        textKegiatan = (TextView) findViewById(R.id.text_title_kegiatan);
        textPenjelasan = (TextView) findViewById(R.id.text_penjelasan);
        textTanggal = (TextView) findViewById(R.id.text_isi_tanggal);
        textLokasi = (TextView) findViewById(R.id.text_isi_tempat);
        gridKegiatan = (GridView) findViewById(R.id.grid_kegiatan);
        back = (ImageView) findViewById(R.id.back);
        btnTambahGaleri = (RelativeLayout) findViewById(R.id.btn_tambah_galeri);
        shimmerLoad = (ShimmerLayout) findViewById(R.id.shimmer_text);
        shimmerLoad2 = (ShimmerLayout) findViewById(R.id.shimmer_text2);
        shimmerLoad.startShimmerAnimation();
        shimmerLoad2.startShimmerAnimation();

        userPreference = new UserPreference(GaleriKegiatan.this);

        if ((userPreference.getKEY_USER() != null) && (userPreference.getKEY_USER().toString().equals("Admin"))) {
            btnTambahGaleri.setVisibility(View.VISIBLE);
        }

        getDataImage();
        setData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnTambahGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahGaleri.tambahGaleriKegiatan = new ModelKegiatan(dataKegiatan.namaKegiatan, dataKegiatan.rincianKegiatan
                        , dataKegiatan.lokasiKegiatan, dataKegiatan.tanggalKegiatan, dataKegiatan.idKegiatan, dataKegiatan.gambarKegiatan
                );
                startActivity(new Intent(GaleriKegiatan.this, TambahGaleri.class));

            }
        });
    }

    private void setData() {
        textKegiatan.setText(dataKegiatan.namaKegiatan);
        textLokasi.setText(dataKegiatan.lokasiKegiatan);
        textTanggal.setText(dataKegiatan.tanggalKegiatan);
        textPenjelasan.setText(dataKegiatan.rincianKegiatan);

    }

    private void getDataImage(){
        FirebaseDatabase.getInstance().getReference().child("Galeri").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            }
            public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
                dataImage = new ArrayList<ModelImage>();
                Iterator localIterator1 = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator1.hasNext()) {
                    DataSnapshot localDataSnapshot = (DataSnapshot) localIterator1.next();
                    Iterator local = localDataSnapshot.getChildren().iterator();
                    while (local.hasNext()){
                        DataSnapshot dataDS = (DataSnapshot) local.next();
                        ModelImage data = (ModelImage) ((DataSnapshot) dataDS).getValue(ModelImage.class);
                        if (data.namaKegiatan.equals(dataKegiatan.namaKegiatan)) {
                            dataImage.add(new ModelImage(data.image, data.nama, data.idKegiatan, data.namaKegiatan
                            ));
                        }
                    }
                }
                setGridImage(dataImage);
            }
        });
    }

    private void setGridImage(final ArrayList<ModelImage> data) {
        shimmerLoad.stopShimmerAnimation();
        shimmerLoad2.stopShimmerAnimation();
        shimmerLoad.setVisibility(View.GONE);
        shimmerLoad2.setVisibility(View.GONE);
        gridKegiatan.setAdapter(new AdapterGridKegiatan(GaleriKegiatan.this, data));

        gridKegiatan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomDialogKegiatan.image = data;
                CustomDialogKegiatan.posisiImgKegiatan = position;
                DialogFragment newFragment = CustomDialogKegiatan
                        .newInstance();

                newFragment.setCancelable(false);
                newFragment.show(GaleriKegiatan.this.getFragmentManager(), "dialog");
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
