package com.exomatik.mpm.mpm.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.exomatik.mpm.mpm.Model.ModelUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;

public class GridAngkatan extends BaseAdapter {
  @Override
  public int getCount() {
    return 0;
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    return null;
  }
//  private Context context;
//  private ArrayList<ModelUser> dataUser;
//
//  public GridAngkatan(Context paramContext, ArrayList<ModelUser> paramArrayList)
//  {
//    this.context = paramContext;
//    this.dataUser = paramArrayList;
//  }
//
//  public int getCount()
//  {
//    return this.dataUser.size();
//  }
//
//  public Object getItem(int paramInt)
//  {
//    return null;
//  }
//
//  public long getItemId(int paramInt)
//  {
//    return 0L;
//  }
//
//  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
//  {
//    View localView = View.inflate(this.context, 2131361847, null);
//    ImageView localImageView = (ImageView)localView.findViewById(2131230843);
//    TextView localTextView = (TextView)localView.findViewById(2131231005);
//    if (!((ModelUser)this.dataUser.get(paramInt)).getFoto().toString().equals("-"))
//    {
//      Uri localUri = Uri.parse(((ModelUser)this.dataUser.get(paramInt)).getFoto().toString());
//      Picasso.with(this.context).load(localUri).into(localImageView);
//    }
//    for (;;)
//    {
//      localTextView.setText(((ModelUser)this.dataUser.get(paramInt)).getNama().toString());
//      return localView;
////      localImageView.setImageResource(2131165348);
//    }
//  }
}