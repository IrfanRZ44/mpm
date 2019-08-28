package com.exomatik.mpm.mpm.TambahGaleri;

import android.content.Context;

import com.exomatik.mpm.mpm.Model.ModelImage;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public interface AddGaleriContract {
    interface View {
        void onAddGaleriSuccess(String message);

        void onAddGaleriFailure(String message);
    }

    interface Presenter {
        void addGaleri(Context context, ModelImage modelImage);
    }

    interface Interactor {
        void addGaleriToDatabase(Context context, ModelImage modelImage);
    }

    interface OnGaleriDatabaseListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
