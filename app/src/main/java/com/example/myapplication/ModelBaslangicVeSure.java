package com.example.myapplication;

public class ModelBaslangicVeSure {
    private String baslangicTarihi;  // Başlangıç tarihi
    private String sure;             // Süre, String olarak kalabilir
    private String frequency;        // Frekans (sıklık), String olarak kalabilir

    // Constructor
    public ModelBaslangicVeSure(String baslangicTarihi, String sure, String frequency) {
        this.baslangicTarihi = baslangicTarihi;
        this.sure = sure;
        this.frequency = frequency;
    }

    // Getter ve Setter'lar
    public String getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(String baslangicTarihi) {
        this.baslangicTarihi = baslangicTarihi;
    }

    public String getSure() {
        return sure;
    }

    public void setSure(String sure) {
        this.sure = sure;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
