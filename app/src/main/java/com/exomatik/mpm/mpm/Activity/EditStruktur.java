package com.exomatik.mpm.mpm.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Model.ModelStruktur;
import com.exomatik.mpm.mpm.Model.ModelUser;
import com.exomatik.mpm.mpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class EditStruktur extends AppCompatActivity {
    private Button btnSimpan;
    public static ModelStruktur dataStruktur = new ModelStruktur();
    private ImageView imgBack;
    private ProgressDialog progressDialog = null;
    private EditText spinnerBendahara;
    private EditText spinnerBpo;
    private EditText spinnerDpo;
    private EditText spinnerSekertaris;
    private EditText spinnerHumas;
    private EditText spinnerPubdok;
    private EditText spinnerMinat;
    private View view;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_edit_struktur);
        this.imgBack = ((ImageView) findViewById(R.id.back));
        this.btnSimpan = ((Button) findViewById(R.id.btn_simpan));
        this.spinnerDpo = ((EditText) findViewById(R.id.spinner_dpo));
        this.spinnerBpo = ((EditText) findViewById(R.id.spinner_bpo));
        this.spinnerSekertaris = ((EditText) findViewById(R.id.spinner_sekertaris));
        this.spinnerBendahara = ((EditText) findViewById(R.id.spinner_bendahara));
        this.spinnerHumas = ((EditText) findViewById(R.id.spinner_humas));
        this.spinnerPubdok = ((EditText) findViewById(R.id.spinner_pubdok));
        this.spinnerMinat = ((EditText) findViewById(R.id.spinner_minat));
        this.view = findViewById(android.R.id.content);

        if (dataStruktur != null){
            spinnerDpo.setText(dataStruktur.getDpo());
            spinnerBpo.setText(dataStruktur.getBpo());
            spinnerSekertaris.setText(dataStruktur.getSekertaris());
            spinnerBendahara.setText(dataStruktur.getBendahara());
            spinnerMinat.setText(dataStruktur.getAnggotaMinat());
            spinnerHumas.setText(dataStruktur.getAnggotaHumas());
            spinnerPubdok.setText(dataStruktur.getAnggotaPubdok());
        }

        this.btnSimpan.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                EditStruktur.this.progressDialog = new ProgressDialog(EditStruktur.this);
                EditStruktur.this.progressDialog.setMessage("Mohon Tunggu...");
                EditStruktur.this.progressDialog.setTitle("Proses");
                EditStruktur.this.progressDialog.setCancelable(false);
                EditStruktur.this.progressDialog.show();
                EditStruktur.this.simpanAnggota(spinnerDpo.getText().toString(), spinnerBpo.getText().toString(),
                        spinnerSekertaris.getText().toString(), spinnerBendahara.getText().toString(), spinnerHumas.getText().toString(),
                        spinnerPubdok.getText().toString(), spinnerMinat.getText().toString());
            }
        });
        this.imgBack.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                EditStruktur.this.startActivity(new Intent(EditStruktur.this, MainActivity.class));
                EditStruktur.this.finish();
            }
        });
    }

    private void simpanAnggota(String dpo, String bpo, String sekertaris, String bendahara, String humas,
                               String pubdok, String minat) {
        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
        localDatabaseReference.child("struktur").child("dpo").setValue(dpo);
        localDatabaseReference.child("struktur").child("bendahara").setValue(bendahara);
        localDatabaseReference.child("struktur").child("sekertaris").setValue(sekertaris);
        localDatabaseReference.child("struktur").child("anggotaHumas").setValue(humas);
        localDatabaseReference.child("struktur").child("anggotaPubdok").setValue(pubdok);
        localDatabaseReference.child("struktur").child("anggotaMinat").setValue(minat);
        localDatabaseReference.child("struktur").child("bpo").setValue(bpo).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> paramAnonymousTask) {
                if (paramAnonymousTask.isSuccessful()) {
                    EditStruktur.this.progressDialog.dismiss();
                    Toast.makeText(EditStruktur.this, "Berhasil Simpan Data", Toast.LENGTH_LONG).show();
                    EditStruktur.this.startActivity(new Intent(EditStruktur.this, MainActivity.class));
                    EditStruktur.this.finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                EditStruktur.this.progressDialog.dismiss();
                Snackbar.make(EditStruktur.this.view, "Gagal Upload Data", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
