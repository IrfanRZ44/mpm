package com.exomatik.mpm.mpm.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Featured.SaveImageHelper;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Manifest;
import com.exomatik.mpm.mpm.Model.ModelKegiatan;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.exomatik.mpm.mpm.R;

import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class DetailKegiatan extends AppCompatActivity {
    public static ModelKegiatan detailKegiatan;
    private ImageView back, btnDownload;
    private PhotoView imgKegiatan;
    private TextView textDesc, textNama, textTanggal, textTempat;
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private UserPreference userPreference;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_detail_kegiatan);

        this.textNama = ((TextView) findViewById(R.id.isi_nama));
        this.textTempat = ((TextView) findViewById(R.id.isi_tempat));
        this.textTanggal = ((TextView) findViewById(R.id.isi_tanggal));
        this.textDesc = ((TextView) findViewById(R.id.isi_desc));
        this.back = ((ImageView) findViewById(R.id.back));
        this.imgKegiatan = ((PhotoView) findViewById(R.id.img_kegiatan));
        this.btnDownload = (ImageView) findViewById(R.id.btn_download);

        userPreference = new UserPreference(this);
        this.textNama.setText(detailKegiatan.getNama().toString());
        this.textTempat.setText(detailKegiatan.getTempat().toString());
        this.textTanggal.setText(detailKegiatan.getTanggal().toString());
        this.textDesc.setText(detailKegiatan.getDesc().toString());

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }

        if ((!detailKegiatan.getFoto().toString().equals("-")) || (!detailKegiatan.getFoto().toString().isEmpty())) {
            Uri localUri = Uri.parse(detailKegiatan.getFoto().toString());
            Picasso.with(this).load(localUri).into(this.imgKegiatan);
        }
        else {
            this.imgKegiatan.setImageResource(R.drawable.mpm);
        }

        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                DetailKegiatan.this.startActivity(new Intent(DetailKegiatan.this, MainActivity.class));
                DetailKegiatan.this.finish();
            }
        });

        btnDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(DetailKegiatan.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(DetailKegiatan.this, "You should grant permission", Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSION_REQUEST_CODE);
                    return;
                }
                else {
                    AlertDialog dialog = new SpotsDialog(DetailKegiatan.this);
                    dialog.show();
                    dialog.setMessage("Downloading...");
                    String fileName = detailKegiatan.getNama().toString() + ".jpg";
                    Picasso.with(getBaseContext()).load(detailKegiatan.getFoto())
                            .into(new SaveImageHelper(getBaseContext(),
                                    dialog,
                                    getApplicationContext().getContentResolver(),
                                    fileName,
                                    "Image Description"));
                    Toast.makeText(DetailKegiatan.this, "Foto sudah di simpan di folder Pictures  dengan nama : " + fileName, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_REQUEST_CODE :
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}