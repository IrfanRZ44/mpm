package com.exomatik.mpm.mpm.TambahKegiatan;

import android.content.Context;
import android.support.annotation.NonNull;

import com.exomatik.mpm.mpm.Model.ModelKegiatan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public class AddKegiatanInteractor implements AddKegiatanContract.Interactor  {
    private AddKegiatanContract.OnKegiatanDatabaseListener mOnKegiatanDatabaseListener;
    private Context ctx;

    public AddKegiatanInteractor(AddKegiatanContract.OnKegiatanDatabaseListener onKegiatanDatabaseListener) {
        this.mOnKegiatanDatabaseListener = onKegiatanDatabaseListener;
    }

    @Override
    public void addKegiatanToDatabase(final Context context, ModelKegiatan modelKegiatan) {
        ctx = context;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ModelKegiatan data = new ModelKegiatan(modelKegiatan.namaKegiatan, modelKegiatan.rincianKegiatan
                , modelKegiatan.lokasiKegiatan, modelKegiatan.tanggalKegiatan, modelKegiatan.idKegiatan
                , modelKegiatan.gambarKegiatan
        );
        database.child("Kegiatan")
                .child(data.namaKegiatan)
                .setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mOnKegiatanDatabaseListener.onSuccess("Succes");
                        } else {
                            mOnKegiatanDatabaseListener.onFailure("Unable to add");
                        }
                    }
                });
    }
}
