package com.exomatik.mpm.mpm.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Adapter.AdapterBelajar;
import com.exomatik.mpm.mpm.Adapter.ItemClickSupport;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Model.ModelBelajar;
import com.exomatik.mpm.mpm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ListPembelajaran extends AppCompatActivity {
    public static String kategoriPembelajaran;
    private TextView textNothing;
    private RecyclerView rcBelajar;
    private ImageView back, btnTambah;
    private UserPreference userPreference;
    private ArrayList<ModelBelajar> listBelajar = new ArrayList<ModelBelajar>();
    private AdapterBelajar adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pembelajaran);

        init();
        setAdapter();
        getDataBelajar();
        onClick();
    }

    private void init() {
        back = (ImageView) findViewById(R.id.back);
        btnTambah = (ImageView) findViewById(R.id.btnTambah);
        rcBelajar = (RecyclerView) findViewById(R.id.rcBelajar);
        textNothing = (TextView) findViewById(R.id.textNothing);

        userPreference = new UserPreference(this);

        if ((userPreference.getKEY_USER() != null) && (userPreference.getKEY_USER().toString().equals("Admin"))) {
            btnTambah.setVisibility(View.VISIBLE);
        }
    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kategoriPembelajaran = null;
                startActivity(new Intent(ListPembelajaran.this, MainActivity.class));
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuatPembelajaran.kategoriPembelajaran = kategoriPembelajaran;
                BuatPembelajaran.position = Integer.toString(listBelajar.size());
                BuatPembelajaran.dataBelajar = null;
                startActivity(new Intent(ListPembelajaran.this, BuatPembelajaran.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        kategoriPembelajaran = null;
        startActivity(new Intent(ListPembelajaran.this, MainActivity.class));
    }

    private void setAdapter() {
        RecyclerView.LayoutManager layoutManager;
        rcBelajar.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ListPembelajaran.this);
        rcBelajar.setLayoutManager(layoutManager);
        adapter = new AdapterBelajar(listBelajar, ListPembelajaran.this);
        rcBelajar.setAdapter(adapter);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            Toast.makeText(ListPembelajaran.this, paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ModelBelajar data = snapshot.getValue(ModelBelajar.class);

                    if (data.getNama().equals("Null 404")) {
                        Log.e("Data 2", "dd" + data.getNama());
                    }
                    else {
                        Log.e("Data", "dd" + data.getNama());
                        listBelajar.add(data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };

    private void getDataBelajar() {
        FirebaseDatabase.getInstance().getReference("pembelajaran")
                .child(kategoriPembelajaran)
                .addListenerForSingleValueEvent(valueEventListener);
    }
}
