package com.exomatik.mpm.mpm.CustomDialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Activity.MainActivity;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Model.ModelUser;
import com.exomatik.mpm.mpm.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class DialogAnggota
        extends DialogFragment {
    public static ModelUser dataOrang;
    Button buttonDelete;
    Button customDialog_Dismiss;
    Uri localUri;

    private View.OnClickListener customDialog_DismissOnClickListener = new View.OnClickListener() {
        public void onClick(View paramAnonymousView) {
            DialogAnggota.this.dismiss();
        }
    };
    ImageView imageView;
    TextView textAge;
    TextView textAlamat;
    TextView textJk;
    TextView textKontak;
    TextView textNama;
    UserPreference userPreference;

    public static DialogAnggota newInstance() {
        return new DialogAnggota();
    }

    @RequiresApi(api = 23)
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.dialog_anggota, paramViewGroup, false);
//        this.textNama = ((TextView) localView.findViewById(2131230991));
//        this.customDialog_Dismiss = ((Button) localView.findViewById(2131230798));
//        this.imageView = ((ImageView) localView.findViewById(2131230845));
//        this.textAlamat = ((TextView) localView.findViewById(2131230973));
//        this.textKontak = ((TextView) localView.findViewById(2131230988));
//        this.textAge = ((TextView) localView.findViewById(2131231001));
//        this.textJk = ((TextView) localView.findViewById(2131230983));
//        this.buttonDelete = ((Button) localView.findViewById(2131230797));
//        this.userPreference = new UserPreference(getContext());
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            this.buttonDelete.setVisibility(0);
//            this.textNama.setText(dataOrang.getNama());
//            this.textKontak.setText(dataOrang.getTelepon());
//            this.textAlamat.setText(dataOrang.getAlamat());
//            this.textAge.setText(Integer.toString(dataOrang.getUmur()));
//            this.textJk.setText(dataOrang.getJk());
//            if (!dataOrang.getFoto().toString().equals("-")) {
////                break label322;
//            }
//            this.imageView.setImageResource(2131165353);
//        }
//        for (; ; ) {
//            if ((this.userPreference.getKEY_USER() != null) && (this.userPreference.getKEY_USER().equals("Admin"))) {
//                this.buttonDelete.setVisibility(0);
//            }
//            this.buttonDelete.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View paramAnonymousView) {
//                    DialogAnggota.this.onDeleteClick(paramAnonymousView);
//                }
//            });
//            this.customDialog_Dismiss.setOnClickListener(this.customDialog_DismissOnClickListener);
//            return localView;
////            this.buttonDelete.setVisibility(8);
////            break;
////            label322:
////            localUri = Uri.parse(dataOrang.getFoto());
////            Picasso.with(getContext()).load(localUri).into(this.imageView);
//        }
        return localView;
    }
//
//    public void onDeleteClick(View paramView) {
//        AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
//        localBuilder.setTitle("Delete");
//        localBuilder.setMessage("Are you sure you want to delete?");
//        localBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @RequiresApi(api = 23)
//            public void onClick(final DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
//                Toast.makeText(DialogAnggota.this.getContext(), "Deleting", 0).show();
//                FirebaseDatabase.getInstance().getReference().child("anggota").child("angkatan_" + DialogAnggota.dataOrang.getAngkatan()).child("user_" + DialogAnggota.dataOrang.getUser()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    public void onSuccess(Void paramAnonymous2Void) {
//                        Toast.makeText(DialogAnggota.this.getContext(), "Succes Deleting", 0).show();
//                        paramAnonymousDialogInterface.dismiss();
//                        DialogAnggota.this.dismiss();
//                        DialogAnggota.this.startActivity(new Intent(DialogAnggota.this.getActivity(), MainActivity.class));
//                        DialogAnggota.this.getActivity().finish();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    public void onFailure(@NonNull Exception paramAnonymous2Exception) {
//                        Toast.makeText(DialogAnggota.this.getContext(), "Failed Deleting Photo", 0).show();
//                        paramAnonymousDialogInterface.dismiss();
//                    }
//                });
//                paramAnonymousDialogInterface.dismiss();
//            }
//        });
//        localBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
//                paramAnonymousDialogInterface.dismiss();
//            }
//        });
//        localBuilder.show();
//    }
}