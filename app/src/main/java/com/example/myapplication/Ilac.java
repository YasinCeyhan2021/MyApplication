package com.example.myapplication;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Ilac {

    private Context context;
    AdapterIlac ilacAdapter;
    List<ModelIlac> allMedicines = new ArrayList<>();

    public Ilac(Context context, AdapterIlac ilacAdapter, List<ModelIlac> ilacList) {
        this.context = context;
        this.ilacAdapter = ilacAdapter;
        this.allMedicines = ilacList;
    }
    public void ilacYukleme(){
        try {
            // assets klasöründen JSON dosyasını okuma
            AssetManager assetManager = context.getAssets();
            InputStreamReader reader = new InputStreamReader(assetManager.open("ilaclar.json"));

            // JSON'u Java nesnelerine dönüştürme
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ModelIlac>>() {}.getType();
            List<ModelIlac> medicines = gson.fromJson(reader, listType);

            allMedicines.clear();
            allMedicines.addAll(medicines);
            filter("");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filter(String text) {
        List<ModelIlac> filtered = new ArrayList<>();
        if (text.isEmpty()) {
            // Başlangıçta ilaçlar görünmesin
            ilacAdapter.updateList(filtered);
        } else {
            for (ModelIlac ilac : allMedicines) {
                if (ilac.getIsim().toLowerCase().startsWith(text.toLowerCase())) {
                    filtered.add(ilac);
                }
            }
            // Arama yapıldıkça filtreli ilaçları göster
            ilacAdapter.updateList(filtered);
        }
    }
}
