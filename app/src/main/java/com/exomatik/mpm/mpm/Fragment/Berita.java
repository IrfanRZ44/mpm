package com.exomatik.mpm.mpm.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.CustomDialog.DialogEditBerita;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class Berita extends Fragment {
    private View view;
    private WebView webBerita;
    private UserPreference userPreference;
    private ImageView btnEdit;
    private ShimmerLayout shimmerLayout;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        this.view = paramLayoutInflater.inflate(R.layout.content_berita, paramViewGroup, false);

        webBerita = (WebView) view.findViewById(R.id.web_berita);
        btnEdit = (ImageView) view.findViewById(R.id.img_edit);
        shimmerLayout = (ShimmerLayout) view.findViewById(R.id.shimmer_load);

        shimmerLayout.startShimmerAnimation();

        userPreference = new UserPreference(getActivity());
        getUrlWeb();

        if (userPreference.getKEY_USER() != null) {
            if (userPreference.getKEY_USER().equals("Admin")) {
                btnEdit.setVisibility(View.VISIBLE);
            }
        }

        return this.view;
    }

    private void getUrlWeb() {
        FirebaseDatabase.getInstance().getReference("berita").addListenerForSingleValueEvent(this.valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            Toast.makeText(getActivity(), paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
            if (paramAnonymousDataSnapshot.exists()) {
                Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator.hasNext()) {
                    final String localModelString = (String) ((DataSnapshot) localIterator.next()).getValue(String.class);
                    webBerita.getSettings().setJavaScriptEnabled(true);
                    webBerita.loadUrl(localModelString);
                    webBerita.setWebViewClient(new WebViewClient());

                    webBerita.setVisibility(View.VISIBLE);
                    shimmerLayout.setVisibility(View.GONE);
                    shimmerLayout.stopShimmerAnimation();

                    btnEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogEditBerita.url = localModelString;
                            DialogEditBerita localBerita = DialogEditBerita.newInstance();
                            localBerita.setCancelable(false);
                            localBerita.show(getActivity().getFragmentManager(), "dialog");
                        }
                    });
                }
            }else {
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogEditBerita localBerita = DialogEditBerita.newInstance();
                        localBerita.setCancelable(false);
                        localBerita.show(getActivity().getFragmentManager(), "dialog");
                    }
                });
            }
        }
    };
}