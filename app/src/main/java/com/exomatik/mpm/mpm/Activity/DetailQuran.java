package com.exomatik.mpm.mpm.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.CustomDialog.DialogTambahQuran;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Model.ModelQuran;
import com.exomatik.mpm.mpm.R;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class DetailQuran extends AppCompatActivity implements DownloadFile.Listener {
    public static ModelQuran dataQuran;
    private LinearLayout root;
    private RemotePDFViewPager remotePDFViewPager;
    private TextView textTitle;
    private ImageView btnBack, btnEdit;
    private PDFPagerAdapter adapter;
    private RelativeLayout rlCustoomToolbar;
    private UserPreference userPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quran);

        root = (LinearLayout) findViewById(R.id.remote_pdf_root);
        btnBack = (ImageView) findViewById(R.id.back);
        btnEdit = (ImageView) findViewById(R.id.img_edit);
        textTitle = (TextView) findViewById(R.id.text_title_bar);
        rlCustoomToolbar = (RelativeLayout) findViewById(R.id.customToolbar);

        userPreference = new UserPreference(this);

        textTitle.setText(dataQuran.getSurah());
        if (userPreference.getKEY_USER() != null){
            if (userPreference.getKEY_USER().equals("Admin")){
                btnEdit.setVisibility(View.VISIBLE);
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogTambahQuran.dataEditQuran = dataQuran;
                        DialogTambahQuran localDialogTambahQuran = DialogTambahQuran.newInstance();
                        localDialogTambahQuran.setCancelable(false);
                        localDialogTambahQuran.show(getFragmentManager(), "dialog");
                    }
                });
            }
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailQuran.this, MainActivity.class));
                finish();
            }
        });

        setPdf();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }
    }

    protected void setPdf() {
        final Context ctx = this;
        final DownloadFile.Listener listener = this;
        remotePDFViewPager = new RemotePDFViewPager(ctx, dataQuran.getFile(), listener);
    }

    public void updateLayout() {
        root.removeAllViewsInLayout();
        root.addView(rlCustoomToolbar,
                LinearLayout.LayoutParams.MATCH_PARENT, 150);
        root.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, "Gagal Membuka File", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }
}