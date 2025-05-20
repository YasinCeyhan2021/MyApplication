package com.example.myapplication;

public class ModelZamanDoz {
    private String zaman;
    private String doz;

    public ModelZamanDoz() {
        // Firestore için boş constructor
    }

    public ModelZamanDoz(String zaman, String doz) {
        this.zaman = zaman;
        this.doz = doz;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }

    public String getDoz() {
        return doz;
    }

    public void setDoz(String doz) {
        this.doz = doz;
    }
}
