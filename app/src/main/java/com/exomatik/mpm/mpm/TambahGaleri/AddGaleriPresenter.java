package com.exomatik.mpm.mpm.TambahGaleri;

import android.content.Context;

import com.exomatik.mpm.mpm.Model.ModelImage;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public class AddGaleriPresenter implements AddGaleriContract.Presenter, AddGaleriContract.OnGaleriDatabaseListener{
    private AddGaleriContract.View mView;
    private AddGaleriInteractor mAddGaleriInteractor;

    public AddGaleriPresenter(AddGaleriContract.View view) {
        this.mView = view;
        mAddGaleriInteractor = new AddGaleriInteractor(this);
    }

    @Override
    public void addGaleri(Context context, ModelImage modelImage) {
        mAddGaleriInteractor.addGaleriToDatabase(context, modelImage);
    }

    @Override
    public void onSuccess(String message) {
        mView.onAddGaleriSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mView.onAddGaleriFailure(message);
    }

}
