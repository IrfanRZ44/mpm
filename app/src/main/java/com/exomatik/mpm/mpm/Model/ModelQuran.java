package com.exomatik.mpm.mpm.Model;

import android.support.annotation.NonNull;

/**
 * Created by IrfanRZ on 18/05/2019.
 */

public class ModelQuran implements Comparable{
    String jus, surah, file;
    int urutan;

    public ModelQuran() {
    }

    public ModelQuran(String jus, String surah, String file, int urutan) {
        this.jus = jus;
        this.surah = surah;
        this.file = file;
        this.urutan = urutan;
    }

    public String getJus() {
        return jus;
    }

    public void setJus(String jus) {
        this.jus = jus;
    }

    public String getSurah() {
        return surah;
    }

    public void setSurah(String surah) {
        this.surah = surah;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getUrutan() {
        return urutan;
    }

    public void setUrutan(int urutan) {
        this.urutan = urutan;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        int compareTo=((ModelQuran)o).getUrutan();
        //        /* For Ascending order*/
//        return this.urutan-compareTo;
//
//        /* For Descending order do like this */
//        //return compareage-this.rating;
        return this.urutan-compareTo;
    }
}
