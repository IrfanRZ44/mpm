package com.exomatik.mpm.mpm.Model;

public class ModelUser
{
  String alamat;
  int angkatan;
  String foto;
  String jk;
  String nama;
  String pass;
  String telepon;
  int umur;
  String user;
  
  public ModelUser() {}
  
  public ModelUser(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt1, int paramInt2, String paramString7)
  {
    this.user = paramString1;
    this.nama = paramString2;
    this.pass = paramString3;
    this.alamat = paramString4;
    this.telepon = paramString5;
    this.jk = paramString6;
    this.angkatan = paramInt1;
    this.umur = paramInt2;
    this.foto = paramString7;
  }
  
  public String getAlamat()
  {
    return this.alamat;
  }
  
  public int getAngkatan()
  {
    return this.angkatan;
  }
  
  public String getFoto()
  {
    return this.foto;
  }
  
  public String getJk()
  {
    return this.jk;
  }
  
  public String getNama()
  {
    return this.nama;
  }
  
  public String getPass()
  {
    return this.pass;
  }
  
  public String getTelepon()
  {
    return this.telepon;
  }
  
  public int getUmur()
  {
    return this.umur;
  }
  
  public String getUser()
  {
    return this.user;
  }
  
  public void setAlamat(String paramString)
  {
    this.alamat = paramString;
  }
  
  public void setAngkatan(int paramInt)
  {
    this.angkatan = paramInt;
  }
  
  public void setFoto(String paramString)
  {
    this.foto = paramString;
  }
  
  public void setJk(String paramString)
  {
    this.jk = paramString;
  }
  
  public void setNama(String paramString)
  {
    this.nama = paramString;
  }
  
  public void setPass(String paramString)
  {
    this.pass = paramString;
  }
  
  public void setTelepon(String paramString)
  {
    this.telepon = paramString;
  }
  
  public void setUmur(int paramInt)
  {
    this.umur = paramInt;
  }
  
  public void setUser(String paramString)
  {
    this.user = paramString;
  }
}
