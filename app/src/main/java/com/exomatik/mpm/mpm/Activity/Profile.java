package com.exomatik.mpm.mpm.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;

public class Profile
  extends AppCompatActivity
{
  private static int PICK_IMAGE = 100;
  private ImageView back;
  private Button btnSimpan;
  private CheckBox cbPass;
  private CircleImageView cvProfile;
  private EditText etAlamat;
  private EditText etNama;
  private EditText etPass;
  private EditText etPhone;
  private EditText etUmur;
  private EditText etUser;
  private Uri imageUri = null;
  private ArrayList<String> listJk = new ArrayList();
  private StorageReference mStorageRef;
  private ProgressDialog progressDialog = null;
  private Spinner spinnerJk;
//  private TextWatcher textWatcher = new TextWatcher()
//  {
//    public void afterTextChanged(Editable paramAnonymousEditable) {}
//
//    public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
//
//    public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
//    {
//      String str1 = Profile.this.etNama.getText().toString().trim();
//      String str2 = Profile.this.etUser.getText().toString().trim();
//      String str3 = Profile.this.etPass.getText().toString().trim();
//      String str4 = Profile.this.etAlamat.getText().toString().trim();
//      String str5 = Profile.this.etPhone.getText().toString().trim();
//      String str6 = Profile.this.etUmur.getText().toString().trim();
//      if (!str1.equals(Profile.this.userPreference.getKEY_NAME())) {
//        Profile.this.btnSimpan.setVisibility(0);
//      }
//      if (!str2.equals(Profile.this.userPreference.getKEY_USER())) {
//        Profile.this.btnSimpan.setVisibility(0);
//      }
//      if (!str3.equals(Profile.this.userPreference.getKEY_PASS())) {
//        Profile.this.btnSimpan.setVisibility(0);
//      }
//      if (!str4.equals(Profile.this.userPreference.getKEY_ALAMAT())) {
//        Profile.this.btnSimpan.setVisibility(0);
//      }
//      if (!str5.equals(Profile.this.userPreference.getKEY_PHONE())) {
//        Profile.this.btnSimpan.setVisibility(0);
//      }
//      if (!str6.equals(Integer.valueOf(Profile.this.userPreference.getKEY_AGE()))) {
//        Profile.this.btnSimpan.setVisibility(0);
//      }
//    }
//  };
//  private String uriFoto;
//  private UserPreference userPreference;
//  private View view;
//
//  private void foto()
//  {
//    this.progressDialog = new ProgressDialog(this);
//    this.progressDialog.setMessage("Mohon Tunggu...");
//    this.progressDialog.setTitle("Proses");
//    this.progressDialog.setCancelable(false);
//    this.progressDialog.show();
//    startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);
//    this.progressDialog.dismiss();
//  }
//
//  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
//  {
//    super.onActivityResult(paramInt1, paramInt2, paramIntent);
//    if ((paramInt2 == -1) && (paramInt1 == PICK_IMAGE))
//    {
//      this.imageUri = paramIntent.getData();
//      Picasso.with(this).load(this.imageUri).into(this.cvProfile);
//      this.progressDialog.dismiss();
//      this.btnSimpan.setVisibility(0);
//    }
//  }
  
  public void onBackPressed()
  {
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_profile);
//    this.back = ((ImageView)findViewById(2131230756));
//    this.btnSimpan = ((Button)findViewById(2131230766));
//    this.etNama = ((EditText)findViewById(2131230811));
//    this.etUser = ((EditText)findViewById(2131230819));
//    this.etPass = ((EditText)findViewById(2131230812));
//    this.etAlamat = ((EditText)findViewById(2131230808));
//    this.etPhone = ((EditText)findViewById(2131230816));
//    this.etUmur = ((EditText)findViewById(2131230818));
//    this.spinnerJk = ((Spinner)findViewById(2131230954));
//    this.cbPass = ((CheckBox)findViewById(2131230770));
//    this.cvProfile = ((CircleImageView)findViewById(2131230849));
//    this.view = findViewById(16908290);
//    this.userPreference = new UserPreference(this);
//    this.mStorageRef = FirebaseStorage.getInstance().getReference();
//    this.etNama.setText(this.userPreference.getKEY_NAME().toString());
//    this.etUser.setText(this.userPreference.getKEY_USER().toString());
//    this.etPass.setText(this.userPreference.getKEY_PASS().toString());
//    this.etAlamat.setText(this.userPreference.getKEY_ALAMAT().toString());
//    this.etPhone.setText(this.userPreference.getKEY_PHONE().toString());
//    this.etUmur.setText(Integer.toString(this.userPreference.getKEY_AGE()));
//    this.uriFoto = this.userPreference.getKEY_FOTO().toString();
//    if (!this.uriFoto.equals("-"))
//    {
//      Uri localUri = Uri.parse(this.uriFoto);
//      Picasso.with(this).load(localUri).into(this.cvProfile);
//      this.etNama.addTextChangedListener(this.textWatcher);
//      this.etUser.addTextChangedListener(this.textWatcher);
//      this.etPass.addTextChangedListener(this.textWatcher);
//      this.etAlamat.addTextChangedListener(this.textWatcher);
//      this.etPhone.addTextChangedListener(this.textWatcher);
//      this.etUmur.addTextChangedListener(this.textWatcher);
//      if (!this.userPreference.getKEY_JK()) {
////        break label561;
//      }
//      this.listJk.add("Laki-Laki");
//      this.listJk.add("Perempuan");
//    }
//    for (;;)
//    {
//      ArrayAdapter localArrayAdapter = new ArrayAdapter(this, 2131361869, this.listJk);
//      this.spinnerJk.setAdapter(localArrayAdapter);
//      this.btnSimpan.setTransformationMethod(null);
//      this.cvProfile.setOnClickListener(new OnClickListener()
//      {
//        public void onClick(View paramAnonymousView)
//        {
//          Profile.this.foto();
//        }
//      });
//      this.etUser.setOnClickListener(new OnClickListener()
//      {
//        public void onClick(View paramAnonymousView)
//        {
//          Toast.makeText(Profile.this, "Afwan, data ini tidak boleh di ubah", 0).show();
//        }
//      });
//      this.btnSimpan.setOnClickListener(new OnClickListener()
//      {
//        public void onClick(View paramAnonymousView)
//        {
//          String str1 = Profile.this.etNama.getText().toString();
//          String str2 = Profile.this.etUser.getText().toString();
//          String str3 = Profile.this.etPass.getText().toString();
//          String str4 = Profile.this.etPhone.getText().toString();
//          String str5 = Profile.this.etAlamat.getText().toString();
//          String str6 = Profile.this.etUmur.getText().toString();
//          if ((str1.isEmpty()) || (str2.isEmpty()) || (str3.isEmpty()) || (str4.isEmpty()) || (str5.isEmpty()) || (str6.isEmpty()) || (Profile.this.imageUri == null))
//          {
//            Toast.makeText(Profile.this, "Afwan, mohon lengkapi data dengan benar", 0).show();
//            return;
//          }
////          ProfileOrganisasi.access$802(ProfileOrganisasi.this, new ProgressDialog(ProfileOrganisasi.this));
//          Profile.this.progressDialog.setMessage("Mohon Tunggu...");
//          Profile.this.progressDialog.setTitle("Proses");
//          Profile.this.progressDialog.setCancelable(false);
//          Profile.this.progressDialog.show();
//          Profile.this.uploadData();
//        }
//      });
//      this.cbPass.setOnCheckedChangeListener(new OnCheckedChangeListener()
//      {
//        public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
//        {
//          if (paramAnonymousBoolean)
//          {
//            Profile.this.etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            return;
//          }
//          Profile.this.etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        }
//      });
//      this.back.setOnClickListener(new OnClickListener()
//      {
//        public void onClick(View paramAnonymousView)
//        {
//          Profile.this.startActivity(new Intent(Profile.this, MainActivity.class));
//          Profile.this.finish();
//        }
//      });
////      return;
////      this.cvProfile.setImageResource(2131165351);
////      break;
////      label561:
////      this.listJk.add("Perempuan");
////      this.listJk.add("Laki-Laki");
//    }
//  }
//
//  public void uploadData()
//  {
//    this.mStorageRef.child("storage/" + this.imageUri.getLastPathSegment()).putFile(this.imageUri).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>()
//    {
//      public void onSuccess(TaskSnapshot paramAnonymousTaskSnapshot)
//      {
//        final String str1 = paramAnonymousTaskSnapshot.getDownloadUrl().toString();
//        Toast.makeText(Profile.this, "Berhasil Upload Foto", 0).show();
//        final String str2 = Profile.this.etNama.getText().toString().trim();
//        final String str3 = Profile.this.etUser.getText().toString().trim();
//        final String str4 = Profile.this.etPass.getText().toString().trim();
//        final String str5 = Profile.this.etAlamat.getText().toString().trim();
//        final String str6 = Profile.this.etPhone.getText().toString().trim();
//        String str7 = Profile.this.etUmur.getText().toString().trim();
//        String str8 = Profile.this.spinnerJk.getSelectedItem().toString();
//        final int i = Profile.this.userPreference.getKEY_ANGKATAN();
//        final int j = Integer.parseInt(str7);
//        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
//        localDatabaseReference.child("anggota").child("angkatan_" + i).child("user_" + str3).child("foto").setValue(str1);
//        localDatabaseReference.child("anggota").child("angkatan_" + i).child("user_" + str3).child("nama").setValue(str2);
//        localDatabaseReference.child("anggota").child("angkatan_" + i).child("user_" + str3).child("umur").setValue(Integer.valueOf(j));
//        localDatabaseReference.child("anggota").child("angkatan_" + i).child("user_" + str3).child("pass").setValue(str4);
//        localDatabaseReference.child("anggota").child("angkatan_" + i).child("user_" + str3).child("alamat").setValue(str5);
//        localDatabaseReference.child("anggota").child("angkatan_" + i).child("user_" + str3).child("telepon").setValue(str6);
//        localDatabaseReference.child("anggota").child("angkatan_" + i).child("user_" + str3).child("jk").setValue(str8);
//        localDatabaseReference.child("anggota").child("angkatan_" + i).child("user_" + str3).child("user").setValue(str3).addOnCompleteListener(new OnCompleteListener<Void>()
//        {
//          public void onComplete(@NonNull Task<Void> paramAnonymous2Task)
//          {
//            if (paramAnonymous2Task.isSuccessful())
//            {
//              Profile.this.progressDialog.dismiss();
//              Profile.this.userPreference.setKEY_NAME(str2);
//              Profile.this.userPreference.setKEY_AGE(j);
//              Profile.this.userPreference.setKEY_PHONE(str6);
//              Profile.this.userPreference.setKEY_USER(str3);
//              Profile.this.userPreference.setKEY_ALAMAT(str5);
//              Profile.this.userPreference.setKEY_ANGKATAN(i);
//              Profile.this.userPreference.setKEY_PASS(str4);
//              Profile.this.userPreference.setKEY_FOTO(str1);
//              Toast.makeText(Profile.this, "Berhasil Upload Data", 0).show();
//              Profile.this.startActivity(new Intent(Profile.this, MainActivity.class));
//              Profile.this.finish();
//              return;
//            }
//            Profile.this.progressDialog.dismiss();
//            Snackbar.make(Profile.this.view, "Gagal Upload Data", 0).show();
//          }
//        });
//      }
//    }).addOnFailureListener(new OnFailureListener()
//    {
//      public void onFailure(@NonNull Exception paramAnonymousException)
//      {
//        Profile.this.progressDialog.dismiss();
//        Toast.makeText(Profile.this, "errror " + paramAnonymousException.getMessage().toString(), 0).show();
//      }
//    });
  }
}