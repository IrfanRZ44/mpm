package com.exomatik.mpm.mpm.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.exomatik.mpm.mpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahAnggota
  extends AppCompatActivity
{
  private ImageView btnBack;
  private RelativeLayout btnTambah;
  private CheckBox cbPass;
  private ProgressDialog progressDialog = null;
  private RadioGroup rgJk;
  private EditText textAlamat;
  private EditText textAngkatan;
  private EditText textNama;
  private EditText textPass;
  private EditText textTelepon;
  private EditText textUmur;
  private EditText textUser;
  private View view;
  
  private void tambahAnggota(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
    int i = Integer.parseInt(paramString2);
    int j = Integer.parseInt(paramString8);
    localDatabaseReference.child("anggota").child("angkatan_" + paramString2).child("user_" + paramString3).child("foto").setValue("-");
    localDatabaseReference.child("anggota").child("angkatan_" + paramString2).child("user_" + paramString3).child("nama").setValue(paramString1);
    localDatabaseReference.child("anggota").child("angkatan_" + paramString2).child("user_" + paramString3).child("angkatan").setValue(Integer.valueOf(i));
    localDatabaseReference.child("anggota").child("angkatan_" + paramString2).child("user_" + paramString3).child("umur").setValue(Integer.valueOf(j));
    localDatabaseReference.child("anggota").child("angkatan_" + paramString2).child("user_" + paramString3).child("pass").setValue(paramString4);
    localDatabaseReference.child("anggota").child("angkatan_" + paramString2).child("user_" + paramString3).child("alamat").setValue(paramString5);
    localDatabaseReference.child("anggota").child("angkatan_" + paramString2).child("user_" + paramString3).child("telepon").setValue(paramString6);
    localDatabaseReference.child("anggota").child("angkatan_" + paramString2).child("user_" + paramString3).child("jk").setValue(paramString7);
    localDatabaseReference.child("anggota").child("angkatan_" + paramString2).child("user_" + paramString3).child("user").setValue(paramString3).addOnCompleteListener(new OnCompleteListener<Void>()
    {
      public void onComplete(@NonNull Task<Void> paramAnonymousTask)
      {
        if (paramAnonymousTask.isSuccessful())
        {
          TambahAnggota.this.progressDialog.dismiss();
          Toast.makeText(TambahAnggota.this, "Berhasil Upload Data", 0).show();
          TambahAnggota.this.startActivity(new Intent(TambahAnggota.this, MainActivity.class));
          TambahAnggota.this.finish();
          return;
        }
        TambahAnggota.this.progressDialog.dismiss();
        Snackbar.make(TambahAnggota.this.view, "Gagal Upload Data", 0).show();
      }
    });
  }
  
  public void onBackPressed()
  {
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_tambah_anggota);
//    this.btnTambah = ((RelativeLayout)findViewById(2131230914));
//    this.btnBack = ((ImageView)findViewById(2131230756));
//    this.textNama = ((EditText)findViewById(2131230811));
//    this.textAngkatan = ((EditText)findViewById(2131230809));
//    this.textUser = ((EditText)findViewById(2131230820));
//    this.textPass = ((EditText)findViewById(2131230813));
//    this.textAlamat = ((EditText)findViewById(2131230808));
//    this.textTelepon = ((EditText)findViewById(2131230814));
//    this.textUmur = ((EditText)findViewById(2131230818));
//    this.rgJk = ((RadioGroup)findViewById(2131230910));
//    this.cbPass = ((CheckBox)findViewById(2131230770));
//    this.view = findViewById(16908290);
    cbPass.setOnCheckedChangeListener(new OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          TambahAnggota.this.textPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
          return;
        }
        TambahAnggota.this.textPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
      }
    });
//    this.btnBack.setOnClickListener(new OnClickListener()
//    {
//      public void onClick(View paramAnonymousView)
//      {
//        TambahAnggota.this.startActivity(new Intent(TambahAnggota.this, MainActivity.class));
//        TambahAnggota.this.finish();
//      }
//    });
//    this.btnTambah.setOnClickListener(new OnClickListener()
//    {
//      public void onClick(View paramAnonymousView)
//      {
////        TambahAnggota.access$102(TambahAnggota.this, new ProgressDialog(TambahAnggota.this));
//        TambahAnggota.this.progressDialog.setMessage("Mohon Tunggu...");
//        TambahAnggota.this.progressDialog.setTitle("Proses");
//        TambahAnggota.this.progressDialog.setCancelable(false);
//        TambahAnggota.this.progressDialog.show();
//        String str1 = TambahAnggota.this.textNama.getText().toString();
//        String str2 = TambahAnggota.this.textAngkatan.getText().toString();
//        String str3 = TambahAnggota.this.textUser.getText().toString();
//        String str4 = TambahAnggota.this.textPass.getText().toString();
//        String str5 = TambahAnggota.this.textAlamat.getText().toString();
//        String str6 = TambahAnggota.this.textTelepon.getText().toString();
//        String str7 = TambahAnggota.this.textUmur.getText().toString();
//        int i = TambahAnggota.this.rgJk.getCheckedRadioButtonId();
//        RadioButton localRadioButton = (RadioButton)TambahAnggota.this.findViewById(i);
//        Toast.makeText(TambahAnggota.this, "radio " + localRadioButton.getText().toString(), 0).show();
//        if ((str1.isEmpty()) || (str2.isEmpty()) || (str3.isEmpty()) || (str4.isEmpty()) || (str5.isEmpty()) || (str6.isEmpty()) || (i == -1))
//        {
//          TambahAnggota.this.progressDialog.dismiss();
//          Toast.makeText(TambahAnggota.this, "Mohon maaf, data harus Valid", 0).show();
//          return;
//        }
//        TambahAnggota.this.tambahAnggota(str1, str2, str3, str4, str5, str6, localRadioButton.getText().toString(), str7);
//      }
//    });
  }
}
