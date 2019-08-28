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

import com.exomatik.mpm.mpm.Model.ModelImage;
import com.exomatik.mpm.mpm.Model.ModelKegiatan;
import com.exomatik.mpm.mpm.R;
import com.exomatik.mpm.mpm.TambahGaleri.AddGaleriContract;
import com.exomatik.mpm.mpm.TambahGaleri.AddGaleriPresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class TambahGaleri extends AppCompatActivity implements AddGaleriContract.View {
    public static ModelKegiatan tambahGaleriKegiatan;
    private ImageView back, imgFoto;
    private TextView textGaleri;
    private RelativeLayout rlFoto;
    private Button btnTambah;
    private ProgressDialog progressDialog;
    private static int PICK_IMAGE = 100;
    private Uri imageUri;
    private AddGaleriPresenter mAddGaleriPresenter;
    private EditText etNama;
    FirebaseStorage storage;
    StorageReference storageRef, imageRef;
    UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_galeri);

        back = (ImageView) findViewById(R.id.back);
        textGaleri = (TextView) findViewById(R.id.text_galeri);
        rlFoto = (RelativeLayout) findViewById(R.id.rl_foto);
        btnTambah = (Button) findViewById(R.id.tambahGaleri);
        imgFoto = (ImageView) findViewById(R.id.img_foto);
        etNama = (EditText) findViewById(R.id.galeriNama);

        textGaleri.setText("Tambah " + tambahGaleriKegiatan.namaKegiatan);
        //accessing the firebase storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAddGaleriPresenter = new AddGaleriPresenter(this);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambah(v);
            }
        });
        rlFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TambahGaleri.this, GaleriKegiatan.class));
                finish();
            }
        });
    }
    private void foto() {
        progressDialog = new ProgressDialog(TambahGaleri.this);
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.setTitle("Proses");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    private void tambah(View v) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setCancelable(false);

        String nama = etNama.getText().toString();

        if ((nama.isEmpty()) || (imgFoto.getVisibility() == View.GONE)) {
            Snackbar.make(v, "Mohon, untuk melengkapi data dengan valid", Snackbar.LENGTH_LONG).show();
            progressDialog.dismiss();
        } else {
//            upload(v, nama, alamat, kontak, skill);
            upload(v, nama);
        }
    }
    public void upload(View view, final String nama) {
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
                Toast.makeText(TambahGaleri.this, "Error in uploading Image!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String uri = downloadUrl.toString();
                Toast.makeText(TambahGaleri.this, "Success Upload Foto", Toast.LENGTH_SHORT).show();

                ModelImage modelImage = new ModelImage(uri,nama, tambahGaleriKegiatan.idKegiatan, tambahGaleriKegiatan.namaKegiatan);
                mAddGaleriPresenter.addGaleri(TambahGaleri.this, modelImage);
            }
        });
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
    @Override
    public void onBackPressed() {
        startActivity(new Intent(TambahGaleri.this, GaleriKegiatan.class));
        finish();
    }

    @Override
    public void onAddGaleriSuccess(String message) {
        Toast.makeText(this, "Berhasil Menyimpan Data ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(TambahGaleri.this, GaleriKegiatan.class));
        finish();
        progressDialog.dismiss();
    }

    @Override
    public void onAddGaleriFailure(String message) {
        Toast.makeText(this, "Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}
