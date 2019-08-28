package com.exomatik.mpm.mpm.Model;

/**
 * Created by IrfanRZ on 15/11/2018.
 */

public class ModelImage {
    public String image;
    public int idKegiatan;
    public String nama;
    public String namaKegiatan;

    public ModelImage() {
    }

    public ModelImage(String image, String nama, int idKegiatan, String namaKegiatan) {
        this.image = image;
        this.idKegiatan = idKegiatan;
        this.nama = nama;
        this.namaKegiatan = namaKegiatan;
    }
}
