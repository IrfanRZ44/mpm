package com.exomatik.mpm.mpm.CustomDialog;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Activity.MainActivity;
import com.exomatik.mpm.mpm.Model.ModelBelajar;
import com.exomatik.mpm.mpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class DialogTambahKategoriBelajar extends DialogFragment {
    public static String namaPembelajaran;
    private ImageView btnClose;
    private RelativeLayout btnTambah, btnDelete;
    private EditText etNama;
    private ProgressDialog progressDialog = null;
    private TextView textBtnTambah;
    private View view;

    public static DialogTambahKategoriBelajar newInstance() {
        return new DialogTambahKategoriBelajar();
    }

    @RequiresApi(api = 23)
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.dialog_tambah_kategori_belajar, paramViewGroup, false);

        this.btnClose = ((ImageView) localView.findViewById(R.id.btn_close));
        this.btnTambah = ((RelativeLayout) localView.findViewById(R.id.btn_tambah));
        this.btnDelete = (RelativeLayout) localView.findViewById(R.id.btn_delete);
        this.etNama = ((EditText) localView.findViewById(R.id.et_nama));
        this.textBtnTambah = ((TextView) localView.findViewById(R.id.text_btn_tambah));
        this.view = localView.findViewById(android.R.id.content);

        cekData();

        this.btnTambah.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                progressDialog = new ProgressDialog(DialogTambahKategoriBelajar.this.getActivity());
                progressDialog.setTitle("Proses");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.show();
                String nama = etNama.getText().toString();

                if (nama.isEmpty()){
                    Toast.makeText(getContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (namaPembelajaran == null){
                        simpanData(nama);
                    } else {
                        hapusKegiatan();
                        simpanData(nama);
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClick(v);
            }
        });

        this.btnClose.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                namaPembelajaran = null;
                dismiss();
            }
        });
        return localView;
    }

    private void cekData() {
        if (namaPembelajaran != null){
            btnDelete.setVisibility(View.VISIBLE);
            etNama.setText(namaPembelajaran);
            textBtnTambah.setText("Hapus dan Buat");
        }
    }

    public void onDeleteClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                try {
                    hapusKegiatan();
                    dismiss();
                } catch (Exception e) {
                }
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                Toast.makeText(getActivity(), "Berhasil Menghapus", Toast.LENGTH_SHORT).show();
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

    private void hapusKegiatan() {
        DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().child("pembelajaran")
                .child(namaPembelajaran);
        db_node.removeValue();
        namaPembelajaran = null;
    }

    private void simpanData(String nama) {
        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();

        ArrayList<ModelBelajar> list = new ArrayList<ModelBelajar>();
        list.add(new ModelBelajar("Null 404", "", "", false));
        localDatabaseReference.child("pembelajaran").child(nama)
                .setValue(list)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> paramAnonymous2Task) {
                if (paramAnonymous2Task.isSuccessful()) {
                    namaPembelajaran = null;
                    Toast.makeText(getActivity(), "Berhasil Tambah Kategori Pembelajaran", Toast.LENGTH_SHORT).show();
                    MainActivity.request = 2;
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                    progressDialog.dismiss();
                    dismiss();
                    return;
                }
                DialogTambahKategoriBelajar.this.progressDialog.dismiss();
                Snackbar.make(DialogTambahKategoriBelajar.this.view, "Gagal Upload Data", Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}