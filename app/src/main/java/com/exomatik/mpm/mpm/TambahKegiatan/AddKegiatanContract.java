package com.exomatik.mpm.mpm.TambahKegiatan;

import android.content.Context;

import com.exomatik.mpm.mpm.Model.ModelKegiatan;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public interface AddKegiatanContract {
    interface View {
        void onAddKegiatanSuccess(String message);

        void onAddKegiatanFailure(String message);
    }

    interface Presenter {
        void addKegiatan(Context context, ModelKegiatan modelKegiatan);
    }

    interface Interactor {
        void addKegiatanToDatabase(Context context, ModelKegiatan modelKegiatan);
    }

    interface OnKegiatanDatabaseListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
