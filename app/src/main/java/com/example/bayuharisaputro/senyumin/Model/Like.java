package com.example.bayuharisaputro.senyumin.Model;

public class Like {
    String id, idPost, nomorUser;

    public Like(String id, String idPost, String nomorUser) {
        this.id = id;
        this.idPost = idPost;
        this.nomorUser = nomorUser;
    }

    public Like() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getNomorUser() {
        return nomorUser;
    }

    public void setNomorUser(String nomorUser) {
        this.nomorUser = nomorUser;
    }
}
