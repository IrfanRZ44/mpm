package com.exomatik.mpm.mpm.Model;

public class ModelGaleri
{
  String desc;
  String foto;
  String time;
  String user;
  String userFoto;
  
  public ModelGaleri() {}
  
  public ModelGaleri(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.foto = paramString1;
    this.desc = paramString2;
    this.user = paramString3;
    this.time = paramString4;
    this.userFoto = paramString5;
  }
  
  public String getDesc()
  {
    return this.desc;
  }
  
  public String getFoto()
  {
    return this.foto;
  }
  
  public String getTime()
  {
    return this.time;
  }
  
  public String getUser()
  {
    return this.user;
  }
  
  public String getUserFoto()
  {
    return this.userFoto;
  }
  
  public void setDesc(String paramString)
  {
    this.desc = paramString;
  }
  
  public void setFoto(String paramString)
  {
    this.foto = paramString;
  }
  
  public void setTime(String paramString)
  {
    this.time = paramString;
  }
  
  public void setUser(String paramString)
  {
    this.user = paramString;
  }
  
  public void setUserFoto(String paramString)
  {
    this.userFoto = paramString;
  }
}