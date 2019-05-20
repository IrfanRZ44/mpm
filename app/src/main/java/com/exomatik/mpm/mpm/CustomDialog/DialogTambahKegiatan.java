package com.exomatik.mpm.mpm.CustomDialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Activity.MainActivity;
import com.exomatik.mpm.mpm.Model.ModelKegiatan;
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
import java.util.Locale;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class DialogTambahKegiatan extends DialogFragment {
    public static ModelKegiatan dataEditKegiatan;
    private static int PICK_IMAGE = 100;
    private ImageView btnClose;
    private RelativeLayout btnTambah, btnDelete;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog datePickerDialog;
    private EditText etDesc;
    private EditText etNama;
    private EditText etTanggal;
    private EditText etTempat;
    private Uri imageUri = null;
    private ImageView imgBtnTambah;
    private ImageView imgKegiatan;
    private StorageReference mStorageRef;
    private ProgressDialog progressDialog = null;
    private TextView textBtnTambah;
    private View view;

    public static DialogTambahKegiatan newInstance() {
        return new DialogTambahKegiatan();
    }

    @RequiresApi(api = 23)
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.dialog_tambah_kegiatan, paramViewGroup, false);

        this.btnClose = ((ImageView) localView.findViewById(R.id.btn_close));
        this.btnTambah = ((RelativeLayout) localView.findViewById(R.id.btn_tambah));
        this.btnDelete = (RelativeLayout) localView.findViewById(R.id.btn_delete);
        this.imgKegiatan = ((ImageView) localView.findViewById(R.id.img_kegiatan));
        this.etNama = ((EditText) localView.findViewById(R.id.et_nama));
        this.etTanggal = ((EditText) localView.findViewById(R.id.et_tanggal));
        this.etTempat = ((EditText) localView.findViewById(R.id.et_tempat));
        this.etDesc = ((EditText) localView.findViewById(R.id.et_desc));
        this.imgBtnTambah = ((ImageView) localView.findViewById(R.id.img_btn_tambah));
        this.textBtnTambah = ((TextView) localView.findViewById(R.id.text_btn_tambah));
        this.view = localView.findViewById(android.R.id.content);

        this.mStorageRef = FirebaseStorage.getInstance().getReference();
        this.dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
//        this.etNama.addTextChangedListener(this.textWatcher);
//        this.etTanggal.addTextChangedListener(this.textWatcher);
//        this.etDesc.addTextChangedListener(this.textWatcher);
//        this.etTempat.addTextChangedListener(this.textWatcher);

        if (this.imageUri == null) {
            this.imgKegiatan.setImageResource(R.drawable.ic_add_green);
        }

        if (dataEditKegiatan != null) {
            etNama.setText(dataEditKegiatan.getNama());
            etDesc.setText(dataEditKegiatan.getDesc());
            etTanggal.setText(dataEditKegiatan.getTanggal());
            etTempat.setText(dataEditKegiatan.getTempat());

            Uri localUri = Uri.parse(dataEditKegiatan.getFoto());
            Picasso.with(getActivity()).load(localUri).into(imgKegiatan);

            textBtnTambah.setText("Simpan Perubahan");

            btnDelete.setVisibility(View.VISIBLE);

            btnDelete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClick(v);
                }
            });
        }

        this.imgKegiatan.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                DialogTambahKegiatan.this.foto();
            }
        });
        this.etTanggal.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                DialogTambahKegiatan.this.getDate();
            }
        });
        this.btnTambah.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                progressDialog = new ProgressDialog(DialogTambahKegiatan.this.getActivity());
                progressDialog.setMessage("Mohon Tunggu, silahkan hubungi developer jika berhasil upload data untuk mengirimkan notifikasi ke tiap user...");
                progressDialog.setTitle("Proses");
                progressDialog.setCancelable(false);
                progressDialog.show();
                uploadData();
            }
        });
        this.btnClose.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                DialogTambahKegiatan.this.dismiss();
            }
        });
        return localView;
    }

    public void onDeleteClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                try {
                    hapusKegiatan();
                } catch (Exception e) {
                }
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                Toast.makeText(getActivity(), "Berhasil Menghapus", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void hapusKegiatan() {
        DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().child("kegiatan")
                .child(dataEditKegiatan.getNama() + "_" + dataEditKegiatan.getTanggal());
        db_node.removeValue();
    }

    public void uploadData() {
        //jika kegiatan di edit maka data sebelumnya perlu dihapus
        if (dataEditKegiatan != null) {
            //hapus data sebelumnya
            hapusKegiatan();

            //memerika apakah admin menggunakan foto baru
            if (imageUri != null) {
                StorageReference fotoDelete = getInstance().getReferenceFromUrl(dataEditKegiatan.getFoto());
                fotoDelete.delete();

                this.mStorageRef.child("storage/" + this.imageUri.getLastPathSegment()).putFile(this.imageUri).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                    public void onSuccess(TaskSnapshot paramAnonymousTaskSnapshot) {
                        simpanData(paramAnonymousTaskSnapshot);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(@NonNull Exception paramAnonymousException) {
                        DialogTambahKegiatan.this.progressDialog.dismiss();
                        Toast.makeText(DialogTambahKegiatan.this.getActivity(), "errror " + paramAnonymousException.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            //admin menggunakan foto lama
            else {
                simpanData(null);
            }
        }
        //jika kegiatan tersebut adalah kegiatan baru, maka langsung upload foto dan simpan data
        else {
            this.mStorageRef.child("storage/" + this.imageUri.getLastPathSegment()).putFile(this.imageUri).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                public void onSuccess(TaskSnapshot paramAnonymousTaskSnapshot) {
                    simpanData(paramAnonymousTaskSnapshot);
                }
            }).addOnFailureListener(new OnFailureListener() {
                public void onFailure(@NonNull Exception paramAnonymousException) {
                    DialogTambahKegiatan.this.progressDialog.dismiss();
                    Toast.makeText(DialogTambahKegiatan.this.getActivity(), "errror " + paramAnonymousException.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void simpanData(TaskSnapshot uriUploaded) {
        String str1 = null;
        if (uriUploaded == null) {
            str1 = dataEditKegiatan.getFoto();
        } else {
            str1 = uriUploaded.getDownloadUrl().toString();
        }
        String str2 = DialogTambahKegiatan.this.etNama.getText().toString().trim();
        String str3 = DialogTambahKegiatan.this.etTanggal.getText().toString().trim();
        String str4 = DialogTambahKegiatan.this.etTempat.getText().toString().trim();
        String str5 = DialogTambahKegiatan.this.etDesc.getText().toString().trim();
        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
        localDatabaseReference.child("kegiatan").child(str2 + "_" + str3).child("tanggal").setValue(str3);
        localDatabaseReference.child("kegiatan").child(str2 + "_" + str3).child("tempat").setValue(str4);
        localDatabaseReference.child("kegiatan").child(str2 + "_" + str3).child("desc").setValue(str5);
        localDatabaseReference.child("kegiatan").child(str2 + "_" + str3).child("foto").setValue(str1);
        localDatabaseReference.child("kegiatan").child(str2 + "_" + str3).child("nama").setValue(str2).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> paramAnonymous2Task) {
                if (paramAnonymous2Task.isSuccessful()) {
                    DialogTambahKegiatan.this.progressDialog.dismiss();
                    Toast.makeText(DialogTambahKegiatan.this.getActivity(), "Berhasil Upload Data, silahkan hubungi developer untuk mengirimkan notifikasi ke tiap user", Toast.LENGTH_LONG).show();
                    DialogTambahKegiatan.this.startActivity(new Intent(DialogTambahKegiatan.this.getActivity(), MainActivity.class));
                    DialogTambahKegiatan.this.getActivity().finish();
                    return;
                }
                DialogTambahKegiatan.this.progressDialog.dismiss();
                Snackbar.make(DialogTambahKegiatan.this.view, "Gagal Upload Data", Snackbar.LENGTH_LONG).show();
            }
        });
    }

//    private TextWatcher textWatcher = new TextWatcher() {
//        public void afterTextChanged(Editable paramAnonymousEditable) {
//        }
//
//        public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
//        }
//
//        public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
//            DialogTambahKegiatan.this.cekForm();
//        }
//    };

//    private void cekForm() {
//        String str1 = this.etNama.getText().toString();
//        String str2 = this.etTanggal.getText().toString();
//        String str3 = this.etTempat.getText().toString();
//        String str4 = this.etDesc.getText().toString();
//        String gambar = imgKegiatan.getDrawable().toString();
//        if ((!str1.isEmpty()) && (!str2.isEmpty()) && (!str3.isEmpty())
//                && (!str4.isEmpty()) && (gambar.contains("drawable"))) {
//            this.imgBtnTambah.setImageResource(R.drawable.ic_add_green);
//            this.textBtnTambah.setTextColor(getResources().getColor(R.color.green1));
//            this.cekBtnTambah = true;
//        }
//        if (str1.isEmpty()) {
//            Toast.makeText(getActivity(), "kosong nama", Toast.LENGTH_SHORT).show();
//            this.imgBtnTambah.setImageResource(R.drawable.ic_add_gray2);
//            this.textBtnTambah.setTextColor(getResources().getColor(R.color.putihGelap2));
//            this.cekBtnTambah = false;
//        }
//        if (str2.isEmpty()) {
//            Toast.makeText(getActivity(), "kosong tanggal", Toast.LENGTH_SHORT).show();
//            this.imgBtnTambah.setImageResource(R.drawable.ic_add_gray2);
//            this.textBtnTambah.setTextColor(getResources().getColor(R.color.putihGelap2));
//            this.cekBtnTambah = false;
//        }
//        if (str3.isEmpty()) {
//            Toast.makeText(getActivity(), "kosong tempat", Toast.LENGTH_SHORT).show();
//            this.imgBtnTambah.setImageResource(R.drawable.ic_add_gray2);
//            this.textBtnTambah.setTextColor(getResources().getColor(R.color.putihGelap2));
//            this.cekBtnTambah = false;
//        }
//        if (str4.isEmpty()) {
//            Toast.makeText(getActivity(), "kosong desc", Toast.LENGTH_SHORT).show();
//            this.imgBtnTambah.setImageResource(R.drawable.ic_add_gray2);
//            this.textBtnTambah.setTextColor(getResources().getColor(R.color.putihGelap2));
//            this.cekBtnTambah = false;
//        }
//        if (gambar.contains("drawable")) {
//            Toast.makeText(getActivity(), "kosong gambar", Toast.LENGTH_SHORT).show();
//            this.imgBtnTambah.setImageResource(R.drawable.ic_add_gray2);
//            this.textBtnTambah.setTextColor(getResources().getColor(R.color.putihGelap2));
//            this.cekBtnTambah = false;
//        }
//    }

    private void foto() {
        this.progressDialog = new ProgressDialog(getActivity());
        this.progressDialog.setMessage("Mohon Tunggu...");
        this.progressDialog.setTitle("Proses");
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
        startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);
        this.progressDialog.dismiss();
    }

    private void getDate() {
        Calendar localCalendar = Calendar.getInstance();
        this.datePickerDialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {
            public void onDateSet(DatePicker paramAnonymousDatePicker, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
                Calendar localCalendar = Calendar.getInstance();
                localCalendar.set(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
                DialogTambahKegiatan.this.etTanggal.setText(DialogTambahKegiatan.this.dateFormatter.format(localCalendar.getTime()));
            }
        }, localCalendar.get(Calendar.YEAR)
                , localCalendar.get(Calendar.MONTH)
                , localCalendar.get(Calendar.DATE));
        this.datePickerDialog.show();
    }

    public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if ((paramInt2 == -1) && (paramInt1 == PICK_IMAGE)) {
            this.imageUri = paramIntent.getData();
            Picasso.with(getActivity()).load(this.imageUri).into(this.imgKegiatan);
//            cekForm();
        }
    }
}