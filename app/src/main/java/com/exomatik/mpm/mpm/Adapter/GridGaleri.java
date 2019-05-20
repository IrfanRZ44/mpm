package com.exomatik.mpm.mpm.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.exomatik.mpm.mpm.Model.ModelGaleri;
import com.exomatik.mpm.mpm.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;

public class GridGaleri
  extends BaseAdapter
{
  private Context context;
  private ArrayList<ModelGaleri> dataUser;
  
  public GridGaleri(Context paramContext, ArrayList<ModelGaleri> paramArrayList)
  {
    this.context = paramContext;
    this.dataUser = paramArrayList;
  }
  
  public int getCount()
  {
    return this.dataUser.size();
  }
  
  public Object getItem(int paramInt)
  {
    return null;
  }
  
  public long getItemId(int paramInt)
  {
    return 0L;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = View.inflate(this.context, R.layout.grid_angkatan, null);
//    ImageView localImageView = (ImageView)localView.findViewById(2131230843);
//    ((TextView)localView.findViewById(2131231005)).setVisibility(8);
    if (!((ModelGaleri)this.dataUser.get(paramInt)).getFoto().toString().equals("-"))
    {
      Uri localUri = Uri.parse(((ModelGaleri)this.dataUser.get(paramInt)).getFoto().toString());
//      Picasso.with(this.context).load(localUri).into(localImageView);
//      return localView;
    }
//    localImageView.setImageResource(2131165348);
    return localView;
  }
}