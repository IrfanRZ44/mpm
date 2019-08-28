package com.exomatik.mpm.mpm.Model;

/**
 * Created by IrfanRZ on 15/11/2018.
 */

public class ModelKegiatan {
    public String namaKegiatan;
    public String rincianKegiatan;
    public String lokasiKegiatan;
    public String tanggalKegiatan;
    public int idKegiatan;
    public String gambarKegiatan;

    public ModelKegiatan() {
    }

    public ModelKegiatan(String namaKegiatan, String rincianKegiatan, String lokasiKegiatan, String tanggalKegiatan, int idKegiatan, String gambarKegiatan) {
        this.namaKegiatan = namaKegiatan;
        this.idKegiatan = idKegiatan;
        this.gambarKegiatan = gambarKegiatan;
        this.rincianKegiatan = rincianKegiatan;
        this.lokasiKegiatan = lokasiKegiatan;
        this.tanggalKegiatan = tanggalKegiatan;
    }
}
