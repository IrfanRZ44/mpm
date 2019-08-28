package com.exomatik.mpm.mpm.TambahKegiatan;

import android.content.Context;

import com.exomatik.mpm.mpm.Model.ModelKegiatan;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public class AddKegiatanPresenter implements AddKegiatanContract.Presenter, AddKegiatanContract.OnKegiatanDatabaseListener{
    private AddKegiatanContract.View mView;
    private AddKegiatanInteractor mAddKegiatanInteractor;

    public AddKegiatanPresenter(AddKegiatanContract.View view) {
        this.mView = view;
        mAddKegiatanInteractor = new AddKegiatanInteractor(this);
    }

    @Override
    public void addKegiatan(Context context, ModelKegiatan modelKegiatan) {
        mAddKegiatanInteractor.addKegiatanToDatabase(context, modelKegiatan);
    }

    @Override
    public void onSuccess(String message) {
        mView.onAddKegiatanSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mView.onAddKegiatanFailure(message);
    }

}
