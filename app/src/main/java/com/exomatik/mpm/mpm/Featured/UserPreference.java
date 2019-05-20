package com.exomatik.mpm.mpm.Featured;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserPreference
{
  private String KEY_AGE = "age";
  private String KEY_ALAMAT = "alamat";
  private String KEY_ANGKATAN = "angkatan";
  private String KEY_FOTO = "foto";
  private String KEY_JK = "jk";
  private String KEY_NAME = "name";
  private String KEY_PASS = "pass";
  private String KEY_PHONE = "phone";
  private String KEY_USER = "user";
  private SharedPreferences preferences;
  
  public UserPreference(Context paramContext)
  {
    this.preferences = paramContext.getSharedPreferences("UserPref", 0);
  }
  
  public int getKEY_AGE()
  {
    return this.preferences.getInt(this.KEY_AGE, 0);
  }
  
  public String getKEY_ALAMAT()
  {
    return this.preferences.getString(this.KEY_ALAMAT, null);
  }
  
  public int getKEY_ANGKATAN()
  {
    return this.preferences.getInt(this.KEY_ANGKATAN, 0);
  }
  
  public String getKEY_FOTO()
  {
    return this.preferences.getString(this.KEY_FOTO, null);
  }
  
  public boolean getKEY_JK()
  {
    return this.preferences.getBoolean(this.KEY_JK, false);
  }
  
  public String getKEY_NAME()
  {
    return this.preferences.getString(this.KEY_NAME, null);
  }
  
  public String getKEY_PASS()
  {
    return this.preferences.getString(this.KEY_PASS, null);
  }
  
  public String getKEY_PHONE()
  {
    return this.preferences.getString(this.KEY_PHONE, null);
  }
  
  public String getKEY_USER()
  {
    return this.preferences.getString(this.KEY_USER, null);
  }
  
  public void setKEY_AGE(int paramInt)
  {
    Editor localEditor = this.preferences.edit();
    localEditor.putInt(this.KEY_AGE, paramInt);
    localEditor.apply();
  }
  
  public void setKEY_ALAMAT(String paramString)
  {
    Editor localEditor = this.preferences.edit();
    localEditor.putString(this.KEY_ALAMAT, paramString);
    localEditor.apply();
  }
  
  public void setKEY_ANGKATAN(int paramInt)
  {
    Editor localEditor = this.preferences.edit();
    localEditor.putInt(this.KEY_ANGKATAN, paramInt);
    localEditor.apply();
  }
  
  public void setKEY_FOTO(String paramString)
  {
    Editor localEditor = this.preferences.edit();
    localEditor.putString(this.KEY_FOTO, paramString);
    localEditor.apply();
  }
  
  public void setKEY_JK(boolean paramBoolean)
  {
    Editor localEditor = this.preferences.edit();
    localEditor.putBoolean(this.KEY_JK, paramBoolean);
    localEditor.apply();
  }
  
  public void setKEY_NAME(String paramString)
  {
    Editor localEditor = this.preferences.edit();
    localEditor.putString(this.KEY_NAME, paramString);
    localEditor.apply();
  }
  
  public void setKEY_PASS(String paramString)
  {
    Editor localEditor = this.preferences.edit();
    localEditor.putString(this.KEY_PASS, paramString);
    localEditor.apply();
  }
  
  public void setKEY_PHONE(String paramString)
  {
    Editor localEditor = this.preferences.edit();
    localEditor.putString(this.KEY_PHONE, paramString);
    localEditor.apply();
  }
  
  public void setKEY_USER(String paramString)
  {
    Editor localEditor = this.preferences.edit();
    localEditor.putString(this.KEY_USER, paramString);
    localEditor.apply();
  }
}
