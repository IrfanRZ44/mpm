package com.exomatik.mpm.mpm.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Model.ModelBelajar;
import com.exomatik.mpm.mpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuatPembelajaran extends AppCompatActivity {
    public static String kategoriPembelajaran;
    public static String position;
    public static ModelBelajar dataBelajar;
    private ImageView back;
    private TextView tvToolbar;
    private EditText etNama, etIsi, etDesc;
    private RelativeLayout btnHapus;
    private Button btnSimpan;
    private ProgressDialog progressDialog = null;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pembelajaran);

        back = (ImageView) findViewById(R.id.back);
        tvToolbar = (TextView) findViewById(R.id.text_toolbar);
        etNama = (EditText) findViewById(R.id.et_nama_pembelajaran);
        etIsi = (EditText) findViewById(R.id.et_isi_pembelajaran);
        etDesc = (EditText) findViewById(R.id.et_desc_pembelajaran);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);
        btnHapus = (RelativeLayout) findViewById(R.id.btn_delete);

        view = findViewById(android.R.id.content);

        if (dataBelajar != null){
            etNama.setText(dataBelajar.getNama());
            etIsi.setText(dataBelajar.getIsi());
            etDesc.setText(dataBelajar.getDesc());
            btnHapus.setVisibility(View.VISIBLE);
            tvToolbar.setText("Edit Pembelajaran");

            btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClick(v);
                }
            });
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(BuatPembelajaran.this);
                progressDialog.setMessage("Mohon Tunggu...");
                progressDialog.setTitle("Proses");
                progressDialog.setCancelable(false);
                progressDialog.show();
                simpanPembelajaran(etNama.getText().toString(), etIsi.getText().toString(), etDesc.getText().toString());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPembelajaran.kategoriPembelajaran = kategoriPembelajaran;
                kategoriPembelajaran = null;
                position = null;
                dataBelajar = null;
                startActivity(new Intent(BuatPembelajaran.this, ListPembelajaran.class));
                finish();
            }
        });
    }

    public void onDeleteClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(BuatPembelajaran.this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                try {
                    hapusData();
                } catch (Exception e) {
                }
                ListPembelajaran.kategoriPembelajaran = kategoriPembelajaran;
                kategoriPembelajaran = null;
                position = null;
                dataBelajar = null;
                startActivity(new Intent(BuatPembelajaran.this, ListPembelajaran.class));
                finish();
                Toast.makeText(BuatPembelajaran.this, "Berhasil Menghapus", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void hapusData(){
        DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().child("pembelajaran")
                .child(kategoriPembelajaran)
                .child(position);
        db_node.removeValue();
    }

    private void simpanPembelajaran(String nama, String isi, String desc) {
        if (dataBelajar != null){
            hapusData();
        }

        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Integer pos = Integer.parseInt(position);
        pos = pos + 1;
        localDatabaseReference.child("pembelajaran").child(kategoriPembelajaran).child(Integer.toString(pos))
                .child("isi").setValue(isi);
        localDatabaseReference.child("pembelajaran").child(kategoriPembelajaran).child(Integer.toString(pos))
                .child("desc").setValue(desc);
        localDatabaseReference.child("pembelajaran").child(kategoriPembelajaran).child(Integer.toString(pos))
                .child("isExpandable").setValue(true);
        localDatabaseReference.child("pembelajaran").child(kategoriPembelajaran).child(Integer.toString(pos))
                .child("nama").setValue(nama).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> paramAnonymousTask) {
                if (paramAnonymousTask.isSuccessful()) {
                    progressDialog.dismiss();
                    ListPembelajaran.kategoriPembelajaran = kategoriPembelajaran;
                    kategoriPembelajaran = null;
                    position = null;
                    dataBelajar = null;
                    Toast.makeText(BuatPembelajaran.this, "Berhasil Simpan Data", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BuatPembelajaran.this, ListPembelajaran.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar.make(view, "Gagal Upload Data", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        ListPembelajaran.kategoriPembelajaran = kategoriPembelajaran;
        kategoriPembelajaran = null;
        position = null;
        dataBelajar = null;
        startActivity(new Intent(BuatPembelajaran.this, ListPembelajaran.class));
        finish();
    }
}
