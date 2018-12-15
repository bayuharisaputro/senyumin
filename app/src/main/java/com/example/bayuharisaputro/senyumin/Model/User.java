package com.example.bayuharisaputro.senyumin.Model;

public class User {
    String nomor;
    String nama;
    String tentang;
    String id;

    public User(String nomor, String nama, String tentang, String id) {
        this.nomor = nomor;
        this.nama = nama;
        this.tentang = tentang;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User() {
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTentang() {
        return tentang;
    }

    public void setTentang(String tentang) {
        this.tentang = tentang;
    }
}
