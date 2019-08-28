package com.exomatik.mpm.mpm.CustomDialog;

import android.app.DialogFragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.mpm.mpm.Model.ModelImage;
import com.exomatik.mpm.mpm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class CustomDialogKegiatan extends DialogFragment {
    TextView customDialog_TextView;
    Button customDialog_Dismiss;
    ImageView imageView;
    public static ArrayList<ModelImage> image;
    public static int posisiImgKegiatan;

    public static CustomDialogKegiatan newInstance() {

        return new CustomDialogKegiatan();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_kegiatan, container, false);

        customDialog_TextView = (TextView) dialogView.findViewById(R.id.text_nama);
        customDialog_Dismiss = (Button) dialogView.findViewById(R.id.dialog_dismiss);
        imageView = (ImageView) dialogView.findViewById(R.id.img_dialog_fragment);

        customDialog_TextView.setText(image.get(posisiImgKegiatan).nama);
//        imageView.setImageResource(image.get(posisiImgKegiatan).image);
        Uri uri = Uri.parse(image.get(posisiImgKegiatan).image);
        Picasso.with(getContext()).load(uri).into(imageView);

        customDialog_Dismiss.setOnClickListener(customDialog_DismissOnClickListener);

        return dialogView;
    }


    private Button.OnClickListener customDialog_DismissOnClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            dismiss();
        }
    };

}
