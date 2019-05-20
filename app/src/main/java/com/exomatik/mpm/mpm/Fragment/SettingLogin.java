package com.exomatik.mpm.mpm.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.exomatik.mpm.mpm.Activity.MainActivity;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Model.ModelUser;
import com.exomatik.mpm.mpm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Iterator;

public class SettingLogin
  extends Fragment
{
  private RelativeLayout btnLogin;
  private CheckBox cbPass;
  private EditText etPass;
  private EditText etUser;
  private UserPreference mUserPreferences;
  private String pass;
  private ProgressDialog progressDialog = null;
  private TextView text;
  private String user;
//  ValueEventListener valueEventListener = new ValueEventListener()
//  {
//    public void onCancelled(DatabaseError paramAnonymousDatabaseError)
//    {
//      Toast.makeText(SettingLogin.this.getActivity(), paramAnonymousDatabaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
//    }
//
//    public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
//    {
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
//      int i = 0;
//      int j = 0;
//      if (j < localArrayList.size())
//      {
//        if ((SettingLogin.this.user.equals(((ModelUser)localArrayList.get(j)).getUser())) && (SettingLogin.this.pass.equals(((ModelUser)localArrayList.get(j)).getPass())))
//        {
//          i = 1;
//          SettingLogin.this.mUserPreferences.setKEY_NAME(((ModelUser)localArrayList.get(j)).getNama());
//          SettingLogin.this.mUserPreferences.setKEY_AGE(((ModelUser)localArrayList.get(j)).getUmur());
//          SettingLogin.this.mUserPreferences.setKEY_PHONE(((ModelUser)localArrayList.get(j)).getTelepon());
//          SettingLogin.this.mUserPreferences.setKEY_USER(((ModelUser)localArrayList.get(j)).getUser());
//          SettingLogin.this.mUserPreferences.setKEY_ALAMAT(((ModelUser)localArrayList.get(j)).getAlamat());
//          SettingLogin.this.mUserPreferences.setKEY_ANGKATAN(((ModelUser)localArrayList.get(j)).getAngkatan());
//          SettingLogin.this.mUserPreferences.setKEY_PASS(((ModelUser)localArrayList.get(j)).getPass());
//          SettingLogin.this.mUserPreferences.setKEY_FOTO(((ModelUser)localArrayList.get(j)).getFoto());
//          if (((ModelUser) localArrayList.get(j)).getJk().equals("Laki-Laki")) {
//            SettingLogin.this.mUserPreferences.setKEY_JK(true);
//          }
//          else {
//            SettingLogin.this.mUserPreferences.setKEY_JK(false);
//          }
//        }
//          Toast.makeText(SettingLogin.this.getActivity(), "Selamat datang " + ((ModelUser)localArrayList.get(j)).getNama(), Toast.LENGTH_LONG).show();
//          SettingLogin.this.startActivity(new Intent(SettingLogin.this.getActivity(), MainActivity.class));
//          SettingLogin.this.getActivity().finish();
//          j++;
//      }
//      if (i == 0) {
//        Toast.makeText(SettingLogin.this.getActivity(), "Maaf, User tidak ditemukan", Toast.LENGTH_LONG).show();
//      }
//      SettingLogin.this.progressDialog.dismiss();
//    }
//  };
  private View view;
  
  @Nullable
  public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle)
  {
    this.view = paramLayoutInflater.inflate(R.layout.content_setting, paramViewGroup, false);
    this.btnLogin = ((RelativeLayout)this.view.findViewById(R.id.rl_login));
    this.etUser = ((EditText)this.view.findViewById(R.id.et_username));
    this.etPass = ((EditText)this.view.findViewById(R.id.et_password));
    this.text = ((TextView)this.view.findViewById(R.id.text));
    this.cbPass = ((CheckBox)this.view.findViewById(R.id.cb_pass));
    this.mUserPreferences = new UserPreference(getContext());
    this.cbPass.setOnCheckedChangeListener(new OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          SettingLogin.this.etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
          return;
        }
        SettingLogin.this.etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
      }
    });
    this.btnLogin.setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        progressDialog = new ProgressDialog(SettingLogin.this.getActivity());
        SettingLogin.this.progressDialog.setMessage("Mohon Tunggu...");
        SettingLogin.this.progressDialog.setTitle("Proses");
        SettingLogin.this.progressDialog.setCancelable(false);
        SettingLogin.this.progressDialog.show();
        user = etUser.getText().toString();
        pass = etPass.getText().toString();
        if ((SettingLogin.this.user.equals("Islam")) && (SettingLogin.this.pass.equals("Islam")))
        {
          SettingLogin.this.mUserPreferences.setKEY_NAME("Admin");
          SettingLogin.this.mUserPreferences.setKEY_AGE(22);
          SettingLogin.this.mUserPreferences.setKEY_PHONE("08");
          SettingLogin.this.mUserPreferences.setKEY_USER("Admin");
          SettingLogin.this.mUserPreferences.setKEY_ANGKATAN(1);
          SettingLogin.this.mUserPreferences.setKEY_PASS("Islam");
          SettingLogin.this.mUserPreferences.setKEY_FOTO("-");
          SettingLogin.this.mUserPreferences.setKEY_JK(true);
          SettingLogin.this.progressDialog.dismiss();
          Toast.makeText(SettingLogin.this.getActivity(), "Selamat datang Admin", Toast.LENGTH_LONG).show();
          SettingLogin.this.startActivity(new Intent(SettingLogin.this.getActivity(), MainActivity.class));
          SettingLogin.this.getActivity().finish();
        }
        else {
          Toast.makeText(SettingLogin.this.getActivity(), "Maaf, User tidak ditemukan", Toast.LENGTH_LONG).show();
        }
//        FirebaseDatabase.getInstance().getReference("anggota").addListenerForSingleValueEvent(SettingLogin.this.valueEventListener);
      }
    });
    return this.view;
  }
}
