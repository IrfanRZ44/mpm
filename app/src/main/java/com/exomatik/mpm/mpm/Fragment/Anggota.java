package com.exomatik.mpm.mpm.Fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import com.exomatik.mpm.mpm.Adapter.GridAngkatan;
import com.exomatik.mpm.mpm.CustomDialog.DialogAnggota;
import com.exomatik.mpm.mpm.Model.ModelUser;
import com.exomatik.mpm.mpm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Iterator;

public class Anggota extends Fragment {
//  private ArrayList<String> dataAnggota = new ArrayList();
//  private GridView gridAngkatan;
//  private Spinner spinnerAnggota;
//  ValueEventListener valueEventListener = new ValueEventListener()
//  {
//    public void onCancelled(DatabaseError paramAnonymousDatabaseError)
//    {
//      Toast.makeText(Anggota.this.getActivity(), paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
//    }
//
//    public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
//    {
//      new ArrayList();
//      ArrayList localArrayList = new ArrayList();
//      if (paramAnonymousDataSnapshot.exists())
//      {
//        Iterator localIterator1 = paramAnonymousDataSnapshot.getChildren().iterator();
//        while (localIterator1.hasNext())
//        {
//          Iterator localIterator2 = ((DataSnapshot)localIterator1.next()).getChildren().iterator();
//          while (localIterator2.hasNext())
//          {
//            ModelUser localModelUser = (ModelUser)((DataSnapshot)localIterator2.next()).getValue(ModelUser.class);
//            localArrayList.add(new ModelUser(localModelUser.getUser(), localModelUser.getNama(), localModelUser.getPass(), localModelUser.getAlamat(), localModelUser.getTelepon(), localModelUser.getJk(), localModelUser.getAngkatan(), localModelUser.getUmur(), localModelUser.getFoto()));
//          }
//        }
//      }
//      Anggota.this.setSpinner(localArrayList);
//    }
//  };
//  private View view;
//
//  private void getDataUser()
//  {
//    FirebaseDatabase.getInstance().getReference("anggota").addListenerForSingleValueEvent(this.valueEventListener);
//  }
//
//  private void setGridAngkatan(ArrayList<ModelUser> paramArrayList)
//  {
//    final ArrayList localArrayList = new ArrayList();
//    if (!this.spinnerAnggota.getSelectedItem().toString().equals("-"))
//    {
//      int i = Integer.parseInt(this.spinnerAnggota.getSelectedItem().toString().replaceAll("Angkatan ", ""));
//      for (int j = 0; j < paramArrayList.size(); j++) {
//        if (((ModelUser)paramArrayList.get(j)).getAngkatan() == i) {
//          localArrayList.add(new ModelUser(((ModelUser)paramArrayList.get(j)).getUser(), ((ModelUser)paramArrayList.get(j)).getNama(), ((ModelUser)paramArrayList.get(j)).getPass(), ((ModelUser)paramArrayList.get(j)).getAlamat(), ((ModelUser)paramArrayList.get(j)).getTelepon(), ((ModelUser)paramArrayList.get(j)).getJk(), ((ModelUser)paramArrayList.get(j)).getAngkatan(), ((ModelUser)paramArrayList.get(j)).getUmur(), ((ModelUser)paramArrayList.get(j)).getFoto()));
//        }
//      }
//      this.gridAngkatan.setAdapter(new GridAngkatan(getActivity(), localArrayList));
//      this.gridAngkatan.setOnItemClickListener(new OnItemClickListener()
//      {
//        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
//        {
//          DialogAnggota.dataOrang = new ModelUser(((ModelUser)localArrayList.get(paramAnonymousInt)).getUser(), ((ModelUser)localArrayList.get(paramAnonymousInt)).getNama(), ((ModelUser)localArrayList.get(paramAnonymousInt)).getPass(), ((ModelUser)localArrayList.get(paramAnonymousInt)).getAlamat(), ((ModelUser)localArrayList.get(paramAnonymousInt)).getTelepon(), ((ModelUser)localArrayList.get(paramAnonymousInt)).getJk(), ((ModelUser)localArrayList.get(paramAnonymousInt)).getAngkatan(), ((ModelUser)localArrayList.get(paramAnonymousInt)).getUmur(), ((ModelUser)localArrayList.get(paramAnonymousInt)).getFoto());
//          DialogAnggota localDialogAnggota = DialogAnggota.newInstance();
//          localDialogAnggota.setCancelable(false);
//          localDialogAnggota.show(Anggota.this.getActivity().getFragmentManager(), "dialog");
//        }
//      });
//    }
//  }
//
//  private void setSpinner(final ArrayList<ModelUser> paramArrayList)
//  {
//    this.dataAnggota.removeAll(this.dataAnggota);
//    this.dataAnggota.add("-");
//    for (int i = 0; i < paramArrayList.size(); i++)
//    {
//      int j = 0;
//      int k = 0;
//      if (k < this.dataAnggota.size())
//      {
//        if (this.dataAnggota.size() == 1)
//        {
//          j = 1;
//          this.dataAnggota.add("Angkatan " + Integer.toString(((ModelUser)paramArrayList.get(i)).getAngkatan()));
//        }
//        if (((String)this.dataAnggota.get(k)).toString().equals("-")) {}
//        for (;;)
//        {
//          k++;
//          break;
////          if (((ModelUser)paramArrayList.get(i)).getAngkatan() == Integer.parseInt(((String)this.dataAnggota.get(k)).toString().replaceAll("Angkatan ", ""))) {
////            j = 1;
////          }
//        }
//      }
//      if (j == 0) {
//        this.dataAnggota.add("Angkatan " + Integer.toString(((ModelUser)paramArrayList.get(i)).getAngkatan()));
//      }
//    }
//    ArrayAdapter localArrayAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_text_putih, this.dataAnggota);
//    this.spinnerAnggota.setAdapter(localArrayAdapter);
//    this.spinnerAnggota.setOnItemSelectedListener(new OnItemSelectedListener()
//    {
//      public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
//      {
//        Anggota.this.setGridAngkatan(paramArrayList);
//      }
//
//      public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {}
//    });
//  }
  
  @Nullable
  public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle)
  {
    View view = paramLayoutInflater.inflate(R.layout.content_anggota, paramViewGroup, false);
//    this.spinnerAnggota = ((Spinner)this.view.findViewById(R.id.spinner_angkatan));
//    this.gridAngkatan = ((GridView)this.view.findViewById(R.id.gv_angkatan));
//    getDataUser();
    return view;
  }
}