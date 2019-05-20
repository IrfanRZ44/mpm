package com.exomatik.mpm.mpm.CustomDialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class DialogTambahGaleri
  extends DialogFragment
{
  private static int PICK_IMAGE = 100;
  public static String fotoUser;
  public static String username;
  private ImageView btnClose;
  private RelativeLayout btnTambah;
  private boolean cekBtnTambah = false;
  private EditText etDesc;
  private Uri imageUri = null;
  private ImageView imgBtnTambah;
  private ImageView imgGaleri;
  private StorageReference mStorageRef;
  private ProgressDialog progressDialog = null;
  private TextView textBtnTambah;
  private TextWatcher textWatcher = new TextWatcher()
  {
    public void afterTextChanged(Editable paramAnonymousEditable) {}
    
    public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    
    public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      DialogTambahGaleri.this.cekForm();
    }
  };
  ValueEventListener valueEventListener = new ValueEventListener()
  {
    public void onCancelled(DatabaseError paramAnonymousDatabaseError)
    {
      Toast.makeText(DialogTambahGaleri.this.getActivity(), paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
    }
    
    public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
    {
      ArrayList localArrayList = new ArrayList();
      if (paramAnonymousDataSnapshot.exists())
      {
        Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
        while (localIterator.hasNext())
        {
          ModelGaleri localModelGaleri = (ModelGaleri)((DataSnapshot)localIterator.next()).getValue(ModelGaleri.class);
          localArrayList.add(new ModelGaleri(localModelGaleri.getFoto(), localModelGaleri.getDesc(), localModelGaleri.getUser(), localModelGaleri.getTime(), localModelGaleri.getUserFoto()));
        }
      }
      DialogTambahGaleri.this.uploadData(localArrayList.size());
    }
  };
  private View view;
  
  private void cekForm()
  {
    String str = this.etDesc.getText().toString();
//    if ((!str.isEmpty()) && (this.imageUri != null))
//    {
//      this.imgBtnTambah.setImageResource(2131165319);
//      this.textBtnTambah.setTextColor(getResources().getColor(2131034182));
//      this.cekBtnTambah = true;
//    }
//    if (str.isEmpty())
//    {
//      this.imgBtnTambah.setImageResource(2131165318);
//      this.textBtnTambah.setTextColor(getResources().getColor(2131034214));
//      this.cekBtnTambah = false;
//    }
//    if (this.imageUri == null)
//    {
//      this.imgBtnTambah.setImageResource(2131165318);
//      this.textBtnTambah.setTextColor(getResources().getColor(2131034214));
//      this.cekBtnTambah = false;
//    }
  }
  
  private void foto()
  {
    this.progressDialog = new ProgressDialog(getActivity());
    this.progressDialog.setMessage("Mohon Tunggu...");
    this.progressDialog.setTitle("Proses");
    this.progressDialog.setCancelable(false);
    this.progressDialog.show();
    startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);
    this.progressDialog.dismiss();
  }
  
  private String getCurrentTime()
  {
    Calendar localCalendar = Calendar.getInstance();
    return new SimpleDateFormat("HH:mm:ss").format(localCalendar.getTime());
  }
  
  private void getDataGaleri()
  {
    FirebaseDatabase.getInstance().getReference("galeri").addListenerForSingleValueEvent(this.valueEventListener);
  }
  
  public static DialogTambahGaleri newInstance()
  {
    return new DialogTambahGaleri();
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == PICK_IMAGE))
    {
      this.imageUri = paramIntent.getData();
      Picasso.with(getActivity()).load(this.imageUri).into(this.imgGaleri);
      this.progressDialog.dismiss();
      cekForm();
    }
  }
  
  @RequiresApi(api=23)
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.dialog_tambah_galeri, paramViewGroup, false);
//    this.btnClose = ((ImageView)localView.findViewById(2131230761));
//    this.btnTambah = ((RelativeLayout)localView.findViewById(2131230767));
//    this.imgGaleri = ((ImageView)localView.findViewById(2131230846));
//    this.etDesc = ((EditText)localView.findViewById(2131230810));
//    this.imgBtnTambah = ((ImageView)localView.findViewById(2131230844));
//    this.textBtnTambah = ((TextView)localView.findViewById(2131230979));
//    this.view = localView.findViewById(16908290);
    this.mStorageRef = FirebaseStorage.getInstance().getReference();
    this.etDesc.addTextChangedListener(this.textWatcher);
    if (this.imageUri == null) {
//      this.imgGaleri.setImageResource(2131165319);
    }
    this.imgGaleri.setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DialogTambahGaleri.this.foto();
      }
    });
    this.btnTambah.setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (DialogTambahGaleri.this.cekBtnTambah)
        {
//          DialogTambahGaleri.access$202(DialogTambahGaleri.this, new ProgressDialog(DialogTambahGaleri.this.getActivity()));
          DialogTambahGaleri.this.progressDialog.setMessage("Mohon Tunggu...");
          DialogTambahGaleri.this.progressDialog.setTitle("Proses");
          DialogTambahGaleri.this.progressDialog.setCancelable(false);
          DialogTambahGaleri.this.progressDialog.show();
          DialogTambahGaleri.this.getDataGaleri();
          return;
        }
        Toast.makeText(DialogTambahGaleri.this.getActivity(), "Afwan, mohon melengkapi data dengan benar", Toast.LENGTH_LONG).show();
      }
    });
    this.btnClose.setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        DialogTambahGaleri.this.dismiss();
      }
    });
    return localView;
  }
  
  public void uploadData(final int paramInt)
  {
    this.mStorageRef.child("storage/" + this.imageUri.getLastPathSegment()).putFile(this.imageUri).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>()
    {
      public void onSuccess(TaskSnapshot paramAnonymousTaskSnapshot)
      {
        String str1 = paramAnonymousTaskSnapshot.getDownloadUrl().toString();
        Toast.makeText(DialogTambahGaleri.this.getActivity(), "Berhasil Upload Foto", Toast.LENGTH_LONG).show();
        String str2 = DialogTambahGaleri.this.etDesc.getText().toString().trim();
        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
        localDatabaseReference.child("galeri").child(DialogTambahGaleri.username + paramInt).child("time").setValue(DialogTambahGaleri.this.getCurrentTime());
        localDatabaseReference.child("galeri").child(DialogTambahGaleri.username + paramInt).child("userFoto").setValue(DialogTambahGaleri.fotoUser);
        localDatabaseReference.child("galeri").child(DialogTambahGaleri.username + paramInt).child("desc").setValue(str2);
        localDatabaseReference.child("galeri").child(DialogTambahGaleri.username + paramInt).child("foto").setValue(str1);
        localDatabaseReference.child("galeri").child(DialogTambahGaleri.username + paramInt).child("user").setValue(DialogTambahGaleri.username).addOnCompleteListener(new OnCompleteListener<Void>()
        {
          public void onComplete(@NonNull Task<Void> paramAnonymous2Task)
          {
            if (paramAnonymous2Task.isSuccessful())
            {
              DialogTambahGaleri.this.progressDialog.dismiss();
              Toast.makeText(DialogTambahGaleri.this.getActivity(), "Berhasil Upload Data", Toast.LENGTH_LONG).show();
              DialogTambahGaleri.this.startActivity(new Intent(DialogTambahGaleri.this.getActivity(), MainActivity.class));
              DialogTambahGaleri.this.getActivity().finish();
            }
            DialogTambahGaleri.this.progressDialog.dismiss();
            Snackbar.make(DialogTambahGaleri.this.view, "Gagal Upload Data", Snackbar.LENGTH_SHORT).show();
          }
        });
      }
    }).addOnFailureListener(new OnFailureListener()
    {
      public void onFailure(@NonNull Exception paramAnonymousException)
      {
        DialogTambahGaleri.this.progressDialog.dismiss();
        Toast.makeText(DialogTambahGaleri.this.getActivity(), "errror " + paramAnonymousException.getMessage().toString(), Toast.LENGTH_SHORT).show();
      }
    });
  }
}