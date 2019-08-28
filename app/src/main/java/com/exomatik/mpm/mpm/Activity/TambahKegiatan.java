package com.exomatik.mpm.mpm.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Featured.DateDialog;
import com.exomatik.mpm.mpm.Model.ModelKegiatan;
import com.exomatik.mpm.mpm.R;
import com.exomatik.mpm.mpm.TambahKegiatan.AddKegiatanContract;
import com.exomatik.mpm.mpm.TambahKegiatan.AddKegiatanPresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class TambahKegiatan extends AppCompatActivity implements AddKegiatanContract.View {
    private ImageView back, imgFoto;
    private EditText etNama, etLokasi, etRincian;
    private RelativeLayout rlTanggal, rlFoto;
    private Button btnTambah;
    private ProgressDialog progressDialog;
    private TextView tvTanggal;
    private static int PICK_IMAGE = 100;
    private Uri imageUri;
    private AddKegiatanPresenter mAddKegiatanPresenter;
    FirebaseStorage storage;
    StorageReference storageRef, imageRef;
    UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kegiatan);

        back = (ImageView) findViewById(R.id.back);
        imgFoto = (ImageView) findViewById(R.id.img_foto);
        etNama = (EditText) findViewById(R.id.galeriNama);
        etLokasi = (EditText) findViewById(R.id.kegiatanLokasi);
        etRincian = (EditText) findViewById(R.id.kegiatanRincian);
        rlTanggal = (RelativeLayout) findViewById(R.id.kegiatanDate);
        rlFoto = (RelativeLayout) findViewById(R.id.rl_foto);
        btnTambah = (Button) findViewById(R.id.tambahKegiatan);
        tvTanggal = (TextView) findViewById(R.id.tanggal);

        //accessing the firebase storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAddKegiatanPresenter = new AddKegiatanPresenter(this);

        imgFoto.setVisibility(View.GONE);
        rlFoto.setVisibility(View.VISIBLE);

        rlFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahKegiatan.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambah(v);
            }
        });
        rlTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DateDialog(v, tvTanggal).show(TambahKegiatan.this.getFragmentManager().beginTransaction(), "DatePicker");
            }
        });
    }

    private void foto() {
        progressDialog = new ProgressDialog(TambahKegiatan.this);
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.setTitle("Proses");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imgFoto.setVisibility(View.VISIBLE);
            rlFoto.setVisibility(View.GONE);
            Picasso.with(this).load(imageUri).into(imgFoto);
        }
        progressDialog.dismiss();
    }

    private void tambah(View v) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setCancelable(false);

        String nama = etNama.getText().toString();
        String lokasi = etLokasi.getText().toString();
        String rincian = etRincian.getText().toString();
        String tanggal = tvTanggal.getText().toString();

        if ((nama.isEmpty()) || (lokasi.isEmpty()) || (rincian.isEmpty()) || (imgFoto.getVisibility() == View.GONE)
                || (tanggal.isEmpty()) || (tanggal.contains("Pilih"))) {
            Snackbar.make(v, "Mohon, untuk melengkapi data dengan valid", Snackbar.LENGTH_LONG).show();
            progressDialog.dismiss();
        } else {
            upload(v, nama, rincian, lokasi, tanggal, 0);
        }
    }

    public void upload(View view, final String nama, final String rincian, final String lokasi, final String tanggal, final int id) {
        imageRef = storageRef.child(imageUri.getLastPathSegment());
        uploadTask = imageRef.putFile(imageUri);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                //sets and increments value of progressbar
                progressDialog.incrementProgressBy((int) progress);

            }
        });

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(TambahKegiatan.this, "Error in uploading Image!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String uri = downloadUrl.toString();
                Toast.makeText(TambahKegiatan.this, "Success Upload Foto", Toast.LENGTH_SHORT).show();

                ModelKegiatan modelKegiatan = new ModelKegiatan(nama, rincian, lokasi, tanggal
                        , id, uri);
                mAddKegiatanPresenter.addKegiatan(TambahKegiatan.this, modelKegiatan);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TambahKegiatan.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAddKegiatanSuccess(String message) {
        Toast.makeText(this, "Berhasil Menyimpan Data ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(TambahKegiatan.this, MainActivity.class));
        finish();
        progressDialog.dismiss();
    }

    @Override
    public void onAddKegiatanFailure(String message) {
        Toast.makeText(this, "Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}
