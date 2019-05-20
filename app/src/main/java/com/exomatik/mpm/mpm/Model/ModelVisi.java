package com.exomatik.mpm.mpm.Model;

/**
 * Created by IrfanRZ on 18/05/2019.
 */

public class ModelVisi {
    String visi;
    String titleVisi;
    boolean isExpandable;

    public ModelVisi() {
    }

    public ModelVisi(String visi, String titleVisi, boolean isExpandable) {
        this.visi = visi;
        this.titleVisi = titleVisi;
        this.isExpandable = isExpandable;
    }

    public String getVisi() {
        return visi;
    }

    public void setVisi(String visi) {
        this.visi = visi;
    }

    public String getTitleVisi() {
        return titleVisi;
    }

    public void setTitleVisi(String titleVisi) {
        this.titleVisi = titleVisi;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
