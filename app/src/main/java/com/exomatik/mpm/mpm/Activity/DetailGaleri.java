package com.exomatik.mpm.mpm.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.exomatik.mpm.mpm.Model.ModelGaleri;
import com.exomatik.mpm.mpm.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailGaleri
  extends AppCompatActivity
{
  public static ModelGaleri detailGaleri;
  private ImageView back;
  private ImageView imgGaleri;
  private CircleImageView imgUser;
  private TextView textDesc;
  private TextView textTime;
  private TextView textUser1;
  private TextView textUser2;
  
  public void onBackPressed()
  {
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_detail_galeri);
//    this.back = ((ImageView)findViewById(2131230756));
//    this.imgUser = ((CircleImageView)findViewById(2131230788));
//    this.imgGaleri = ((ImageView)findViewById(2131230846));
//    this.textUser1 = ((TextView)findViewById(2131231003));
//    this.textUser2 = ((TextView)findViewById(2131231004));
//    this.textDesc = ((TextView)findViewById(2131230980));
//    this.textTime = ((TextView)findViewById(2131230998));
    this.textUser1.setText(detailGaleri.getUser().toString());
    this.textUser2.setText(detailGaleri.getUser().toString());
    this.textTime.setText(detailGaleri.getTime().toString());
    this.textDesc.setText(detailGaleri.getDesc().toString());
    if (!detailGaleri.getFoto().toString().equals("-"))
    {
      Uri localUri2 = Uri.parse(detailGaleri.getFoto().toString());
      Picasso.with(this).load(localUri2).into(this.imgGaleri);
      if (detailGaleri.getUserFoto().toString().equals("-")) {
//        break label284;
      }
      Uri localUri1 = Uri.parse(detailGaleri.getUserFoto().toString());
      Picasso.with(this).load(localUri1).into(this.imgUser);
    }
    for (;;)
    {
      this.back.setOnClickListener(new OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          DetailGaleri.this.startActivity(new Intent(DetailGaleri.this, MainActivity.class));
          DetailGaleri.this.finish();
        }
      });
      return;
//      this.imgGaleri.setImageResource(2131165345);
//      break;
//      label284:
//      this.imgUser.setImageResource(2131165345);
    }
  }
}