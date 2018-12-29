package com.example.bayuharisaputro.senyumin.Model;

public class Komen {
    String id, urlpost, komentar, nomorPengomentar,tgl;

    public Komen(String id, String urlpost, String komentar, String nomorPengomentar, String tgl) {
        this.id = id;
        this.urlpost = urlpost;
        this.komentar = komentar;
        this.nomorPengomentar = nomorPengomentar;
        this.tgl = tgl;
    }

    public Komen() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlpost() {
        return urlpost;
    }

    public void setUrlpost(String urlpost) {
        this.urlpost = urlpost;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getNomorPengomentar() {
        return nomorPengomentar;
    }

    public void setNomorPengomentar(String nomorPengomentar) {
        this.nomorPengomentar = nomorPengomentar;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }
}
