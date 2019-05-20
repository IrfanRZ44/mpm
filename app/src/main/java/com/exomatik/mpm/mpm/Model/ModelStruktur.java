package com.exomatik.mpm.mpm.Model;

public class ModelStruktur
{
  String bendahara;
  String bpo;
  String dpo;
  String sekertaris;
  String anggotaHumas;
  String anggotaPubdok;
  String anggotaMinat;
  
  public ModelStruktur() {}

  public ModelStruktur(String bendahara, String bpo, String dpo, String sekertaris, String anggotaHumas, String anggotaPubdok, String anggotaMinat) {
    this.bendahara = bendahara;
    this.bpo = bpo;
    this.dpo = dpo;
    this.sekertaris = sekertaris;
    this.anggotaHumas = anggotaHumas;
    this.anggotaPubdok = anggotaPubdok;
    this.anggotaMinat = anggotaMinat;
  }

  public String getBendahara() {
    return bendahara;
  }

  public void setBendahara(String bendahara) {
    this.bendahara = bendahara;
  }

  public String getBpo() {
    return bpo;
  }

  public void setBpo(String bpo) {
    this.bpo = bpo;
  }

  public String getDpo() {
    return dpo;
  }

  public void setDpo(String dpo) {
    this.dpo = dpo;
  }

  public String getSekertaris() {
    return sekertaris;
  }

  public void setSekertaris(String sekertaris) {
    this.sekertaris = sekertaris;
  }

  public String getAnggotaHumas() {
    return anggotaHumas;
  }

  public void setAnggotaHumas(String anggotaHumas) {
    this.anggotaHumas = anggotaHumas;
  }

  public String getAnggotaPubdok() {
    return anggotaPubdok;
  }

  public void setAnggotaPubdok(String anggotaPubdok) {
    this.anggotaPubdok = anggotaPubdok;
  }

  public String getAnggotaMinat() {
    return anggotaMinat;
  }

  public void setAnggotaMinat(String anggotaMinat) {
    this.anggotaMinat = anggotaMinat;
  }
}
