package com.exomatik.mpm.mpm.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.exomatik.mpm.mpm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import io.fabric.sdk.android.Fabric;
import java.util.Iterator;

public class SplashScreen extends AppCompatActivity {
  private AVLoadingIndicatorView loadingIndicatorView;
  private TextView text;
  private boolean close = false;

  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    Fabric.with(this, new Crashlytics());
    setContentView(R.layout.splash_screen);

    loadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
    text = (TextView) findViewById(R.id.text);

    getDataMaintenance();
  }

  private void getDataMaintenance() {
    FirebaseDatabase.getInstance().getReference("maintenance").addListenerForSingleValueEvent(this.valueEventListener);
  }

  ValueEventListener valueEventListener = new ValueEventListener() {
    public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
      Toast.makeText(SplashScreen.this, paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
    }

    public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
      if (paramAnonymousDataSnapshot.exists()) {
        Iterator localIterator = paramAnonymousDataSnapshot.getChildren().iterator();
        while (localIterator.hasNext()) {
          String localDataString = (String) ((DataSnapshot) localIterator.next()).getValue(String.class);

//          if (localDataString.equals("aktif")){
//            new Handler().postDelayed(new Runnable()
//            {
//              public void run()
//              {
//                Intent localIntent = new Intent(SplashScreen.this, MainActivity.class);
//                SplashScreen.this.startActivity(localIntent);
//                SplashScreen.this.finish();
//              }
//            }, 2000L);
//          }
//          else if (localDataString.equals("maintenance")){
//            loadingIndicatorView.setVisibility(View.GONE);
//            text.setText("Mohon maaf, aplikasi sedang maintenance");
//            close = true;
//          }
//          else if (localDataString.equals("service")){
//            text.setText("Mohon maaf, aplikasi akan maintenance segera");
            new Handler().postDelayed(new Runnable()
            {
              public void run()
              {
                Intent localIntent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(localIntent);
                SplashScreen.this.finish();
              }
            }, 2000L);
//          }
        }
      }
    }
  };

  @Override
  public void onBackPressed() {
    if (close){
      finish();
    }
  }
}