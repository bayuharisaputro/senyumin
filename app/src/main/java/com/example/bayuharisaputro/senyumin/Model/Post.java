package com.example.bayuharisaputro.senyumin.Model;

public class Post {
    String nomor;
    String judul;
    String tanggal;
    String namaPost;
    int like;
    String id;
    int dislike;
    int report;

    public Post(String nomor, String judul, String tanggal, String namaPost, int like, String id, int dislike, int report) {
        this.nomor = nomor;
        this.judul = judul;
        this.tanggal = tanggal;
        this.namaPost = namaPost;
        this.like = like;
        this.id = id;
        this.dislike = dislike;
        this.report = report;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
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
