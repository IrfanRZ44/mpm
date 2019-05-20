package com.exomatik.mpm.mpm.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Model.ModelStruktur;
import com.exomatik.mpm.mpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditVisi extends AppCompatActivity {
    public static String visi, misi;
    private EditText etVisi, etMisi;
    private Button btnSimpan;
    private ProgressDialog progressDialog = null;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_visi);

        etVisi = (EditText) findViewById(R.id.et_visi);
        etMisi = (EditText) findViewById(R.id.et_misi);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);
        back = (ImageView) findViewById(R.id.back);

        etVisi.setText(visi);
        etMisi.setText(misi);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditVisi.this, MainActivity.class));
                finish();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(EditVisi.this);
                progressDialog.setMessage("Mohon Tunggu...");
                progressDialog.setTitle("Proses");
                progressDialog.setCancelable(false);
                progressDialog.show();
                simpanAnggota(etVisi.getText().toString(), etMisi.getText().toString());
            }
        });
    }

    private void simpanAnggota(String visi, String misi)
    {
        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
        localDatabaseReference.child("visi").child("visi").child("visi").setValue(visi);
        localDatabaseReference.child("visi").child("visi").child("titleVisi").setValue("visi");
        localDatabaseReference.child("visi").child("misi").child("titleVisi").setValue("misi");
        localDatabaseReference.child("visi").child("misi").child("visi").setValue(misi).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            public void onComplete(@NonNull Task<Void> paramAnonymousTask)
            {
                if (paramAnonymousTask.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(EditVisi.this, "Berhasil Simpan Data", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditVisi.this, MainActivity.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(EditVisi.this, "Gagal Simpan Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditVisi.this, MainActivity.class));
        finish();
    }
}
