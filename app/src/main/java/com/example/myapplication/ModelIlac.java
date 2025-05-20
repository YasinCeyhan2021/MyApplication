package com.example.myapplication;

public class ModelIlac {
    private String isim;
    private String tur;
    private String aciklama;
    private String kullanim;
    private String kullanim_sikligi;
    private String recete_durumu;

    // Getter ve Setter metodları
    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getKullanim() {
        return kullanim;
    }

    public void setKullanim(String kullanim) {
        this.kullanim = kullanim;
    }

    public String getKullanim_sikligi() {
        return kullanim_sikligi;
    }

    public void setKullanim_sikligi(String kullanim_sikligi) {
        this.kullanim_sikligi = kullanim_sikligi;
    }

    public String getRecete_durumu() {
        return recete_durumu;
    }

    public void setRecete_durumu(String recete_durumu) {
        this.recete_durumu = recete_durumu;
    }

}
