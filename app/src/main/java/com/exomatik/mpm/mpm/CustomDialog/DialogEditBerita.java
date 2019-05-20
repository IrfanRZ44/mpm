package com.exomatik.mpm.mpm.CustomDialog;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Activity.EditStruktur;
import com.exomatik.mpm.mpm.Activity.MainActivity;
import com.exomatik.mpm.mpm.Model.ModelGaleri;
import com.exomatik.mpm.mpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class DialogEditBerita extends DialogFragment {
  public static String url;
  private EditText etUrl;
  private RelativeLayout btnSimpan;
  private ProgressDialog progressDialog = null;
  private TextView textBtnSimpan;
  private ImageView btnClose, imgBtnSimpan;
  private View view;

  public static DialogEditBerita newInstance()
  {
    return new DialogEditBerita();
  }
  
  @RequiresApi(api=23)
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.dialog_edit_berita, paramViewGroup, false);

    btnClose = (ImageView) localView.findViewById(R.id.btn_close);
    etUrl = (EditText) localView.findViewById(R.id.et_url);
    btnSimpan = (RelativeLayout) localView.findViewById(R.id.btn_simpan);
    textBtnSimpan = (TextView) localView.findViewById(R.id.text_btn_tambah);
    imgBtnSimpan = (ImageView) localView.findViewById(R.id.img_btn_tambah);

    view = localView.findViewById(android.R.id.content);

    etUrl.addTextChangedListener(tvUrl);
    etUrl.setText(url);

    btnSimpan.setOnClickListener(new OnClickListener() {
      public void onClick(View paramAnonymousView) {
        if (etUrl.getText() != null){
          progressDialog = new ProgressDialog(getActivity());
          progressDialog.setMessage("Mohon Tunggu...");
          progressDialog.setTitle("Proses");
          progressDialog.setCancelable(false);
          progressDialog.show();
          simpanUrl(etUrl.getText().toString());
        }else {
          Toast.makeText(getActivity(), "Mohon isi data dengan benar", Toast.LENGTH_SHORT).show();
        }
      }
    });
    btnClose.setOnClickListener(new OnClickListener() {
      public void onClick(View paramAnonymousView)
      {
        dismiss();
      }
    });

    return localView;
  }

  private TextWatcher tvUrl = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      if (etUrl.getText() != null){
        textBtnSimpan.setTextColor(getResources().getColor(R.color.green1));
        imgBtnSimpan.setImageResource(R.drawable.ic_add_green);
      }
      else {
        textBtnSimpan.setTextColor(getResources().getColor(R.color.putihGelap2));
        imgBtnSimpan.setImageResource(R.drawable.ic_add_gray2);
      }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
  };

  private void simpanUrl(String url) {
    DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();

    localDatabaseReference.child("berita").child("url").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
      public void onComplete(@NonNull Task<Void> paramAnonymousTask) {
        if (paramAnonymousTask.isSuccessful()) {
          progressDialog.dismiss();
          Toast.makeText(getActivity(), "Berhasil Simpan Data", Toast.LENGTH_LONG).show();
          startActivity(new Intent(getActivity(), MainActivity.class));
          getActivity().finish();
        }
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        progressDialog.dismiss();
        Snackbar.make(view, "Gagal Upload Data", Snackbar.LENGTH_LONG).show();
      }
    });
  }
}