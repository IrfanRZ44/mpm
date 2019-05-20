package com.exomatik.mpm.mpm.Model;

/**
 * Created by IrfanRZ on 20/05/2019.
 */

public class ModelBelajar {
    String nama, isi, desc;
    boolean isExpandable;

    public ModelBelajar() {
    }

    public ModelBelajar(String nama, String isi, String desc, boolean isExpandable) {
        this.nama = nama;
        this.isi = isi;
        this.desc = desc;
        this.isExpandable = isExpandable;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
