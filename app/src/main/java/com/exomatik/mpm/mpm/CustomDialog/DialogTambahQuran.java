package com.exomatik.mpm.mpm.CustomDialog;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Activity.MainActivity;
import com.exomatik.mpm.mpm.Model.ModelQuran;
import com.exomatik.mpm.mpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;

import static android.app.Activity.RESULT_OK;
import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class DialogTambahQuran extends DialogFragment {
    public static ModelQuran dataEditQuran;
    private ImageView btnClose, imgButtonUpload;
    private View view;
    private RelativeLayout btnUpload, btnHapus;
    private TextView textFile, textButtonUpload;
    private EditText etSurah, etJus, etUrut;
    private Button btnFile;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private Uri pdfUri;
    private ProgressDialog progressDialog;

    public static DialogTambahQuran newInstance() {
        return new DialogTambahQuran();
    }

    @RequiresApi(api = 23)
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.dialog_tambah_quran, paramViewGroup, false);
        btnClose = ((ImageView) localView.findViewById(R.id.btn_close));
        btnFile = (Button) localView.findViewById(R.id.btn_file);
        etSurah = (EditText) localView.findViewById(R.id.et_surah);
        etUrut = (EditText) localView.findViewById(R.id.et_urut);
        etJus = (EditText) localView.findViewById(R.id.et_jus);
        textFile = (TextView) localView.findViewById(R.id.text_file);
        textButtonUpload = (TextView) localView.findViewById(R.id.text_btn_tambah);
        imgButtonUpload = (ImageView) localView.findViewById(R.id.img_btn_tambah);
        btnUpload = (RelativeLayout) localView.findViewById(R.id.btn_tambah);
        btnHapus = (RelativeLayout) localView.findViewById(R.id.btn_delete);

        view = localView.findViewById(android.R.id.content);

        if (dataEditQuran != null) {
            etUrut.setText(Integer.toString(dataEditQuran.getUrutan()));
            etJus.setText(dataEditQuran.getJus());
            etSurah.setText(dataEditQuran.getSurah());
            pdfUri = Uri.parse(dataEditQuran.getFile());
            textFile.setText(dataEditQuran.getFile());

            textButtonUpload.setText("Simpan Perubahan");
            textButtonUpload.setTextColor(getResources().getColor(R.color.green1));
            imgButtonUpload.setImageResource(R.drawable.ic_upload_green);
            btnHapus.setVisibility(View.VISIBLE);

            btnHapus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClick(v);
                }
            });
        }
        storage = getInstance();
        database = FirebaseDatabase.getInstance();

        btnFile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        btnUpload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfUri != null) {
                    uploadFile(pdfUri);
                } else {
                    Toast.makeText(getActivity(), "Select a file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClose.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                DialogTambahQuran.this.dismiss();
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
                    hapusFoto();
                    hapusData();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error : " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
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

    private void hapusData(){
        DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().child("quran")
                .child(dataEditQuran.getSurah());
        db_node.removeValue();
    }

    private void hapusFoto(){
        StorageReference filePdfDelete = getInstance().getReferenceFromUrl(dataEditQuran.getFile());
        filePdfDelete.delete();
    }

    private void uploadFile(final Uri pdfUri) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File");
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String file = System.currentTimeMillis() + "";
        final StorageReference storageReference = storage.getReference();

        //kalau data mau di edit,, maka data sebelumnya perlu dihapus dulu
        if (dataEditQuran != null) {
            hapusData();
            //kalau file yang mau di upload sama dengan data sebelumnya maka file pdf tidak perlu dihapus
            if (textFile.getText().toString().contains(dataEditQuran.getFile())) {
                simpanData(null);
            }
            //kalau file yang mau di upload tidak sama dengan file sebelumnya maka file pdf sebelumnya di hapus dulu
            else {
                hapusFoto();
                simpanFoto();
            }
        }
        //kalau data baru mau dibuat
        else {
            simpanFoto();
        }
    }

    private void simpanFoto(){
        final String file = System.currentTimeMillis() + "";
        final StorageReference storageReference = storage.getReference();
        storageReference.child("Uploads").child(file).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                    @Override
                    public void onSuccess(TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getDownloadUrl().toString();
                        simpanData(url);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "File Not Succesfully Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<TaskSnapshot>() {
            @Override
            public void onProgress(TaskSnapshot taskSnapshot) {
//                int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                progressDialog.setProgress(currentProgress);
                double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
//                progressDialog.setMessage(Integer.toString((int)progress) + " %");
                progressDialog.setProgress((int)progress);
                String progressText = taskSnapshot.getBytesTransferred()/1024+"KB/"+taskSnapshot.getTotalByteCount()/1024+"KB";
                progressDialog.setTitle(progressText);
            }
        });
    }

    private void simpanData(String url) {
        DatabaseReference reference = database.getReference();
        reference.child("quran")
                .child(etSurah.getText().toString())
                .child("urutan")
                .setValue(Integer.parseInt(etUrut.getText().toString()));
        reference.child("quran")
                .child(etSurah.getText().toString())
                .child("surah")
                .setValue(etSurah.getText().toString());
        reference.child("quran")
                .child(etSurah.getText().toString())
                .child("jus")
                .setValue(etJus.getText().toString());
        if (url == null){
            reference.child("quran")
                    .child(etSurah.getText().toString())
                    .child("file")
                    .setValue(textFile.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        DialogTambahQuran.this.dismiss();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getActivity(), "File Succesfully Uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "File Not Succesfully Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            reference.child("quran")
                    .child(etSurah.getText().toString())
                    .child("file")
                    .setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        DialogTambahQuran.this.dismiss();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getActivity(), "File Succesfully Uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "File Not Succesfully Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPdf();
        } else {
            Toast.makeText(getActivity(), "Please provide permission...", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            textFile.setText("File yang dipilih : " + data.getData().getLastPathSegment());
            textButtonUpload.setTextColor(getResources().getColor(R.color.green1));
            imgButtonUpload.setImageResource(R.drawable.ic_upload_green);
        } else {
            Toast.makeText(getActivity(), "Please, select a file", Toast.LENGTH_SHORT).show();
        }
    }
}