package com.example.bayuharisaputro.senyumin.Model;

public class Post {
    String nomor;
    String judul;
    String tanggal;
    String namaPost;
    String like;
    int dislike;
    int report;


    public Post(String nomor, String judul, String tanggal, String namaPost, String like, int dislike, int report) {
        this.nomor = nomor;
        this.judul = judul;
        this.tanggal = tanggal;
        this.namaPost = namaPost;
        this.like = like;
        this.dislike = dislike;
        this.report = report;
    }

    public Post() {
    }

    public String getNamaPost() {
        return namaPost;
    }

    public void setNamaPost(String namaPost) {
        this.namaPost = namaPost;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }
}
