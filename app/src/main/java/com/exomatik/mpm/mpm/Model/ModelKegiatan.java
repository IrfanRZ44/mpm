package com.exomatik.mpm.mpm.Model;

public class ModelKegiatan {
    String desc;
    String foto;
    String nama;
    String tanggal;
    String tempat;

    public ModelKegiatan() {
    }

    public ModelKegiatan(String nama, String tanggal, String tempat, String desc, String foto) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.tempat = tempat;
        this.desc = desc;
        this.foto = foto;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getFoto() {
        return this.foto;
    }

    public String getNama() {
        return this.nama;
    }

    public String getTanggal() {
        return this.tanggal;
    }

    public String getTempat() {
        return this.tempat;
    }

    public void setDesc(String paramString) {
        this.desc = paramString;
    }

    public void setFoto(String paramString) {
        this.foto = paramString;
    }

    public void setNama(String paramString) {
        this.nama = paramString;
    }

    public void setTanggal(String paramString) {
        this.tanggal = paramString;
    }

    public void setTempat(String paramString) {
        this.tempat = paramString;
    }
}
