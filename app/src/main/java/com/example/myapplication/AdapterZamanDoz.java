package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class AdapterZamanDoz extends RecyclerView.Adapter<AdapterZamanDoz.ZamanDozViewHolder> {

    private List<ModelZamanDoz> zamanDozList;
    private Context context;

    public AdapterZamanDoz(List<ModelZamanDoz> zamanDozList) {
        this.zamanDozList = zamanDozList;
    }

    @NonNull
    @Override
    public ZamanDozViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zaman_doz, parent, false);
        context = parent.getContext();
        return new ZamanDozViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ZamanDozViewHolder holder, int position) {
        ModelZamanDoz zamanDoz = zamanDozList.get(position);

        holder.txtTime.setText(zamanDoz.getZaman());
        holder.txtDose.setText(zamanDoz.getDoz());

        // Silme butonuna tıklama işlemi
        holder.btnDelete.setOnClickListener(v -> {
            // Zaman ve doz listeden silinir
            zamanDozList.remove(position);
            notifyItemRemoved(position);

            // AlarmKurActivity'deki btnNext butonunu tekrar kontrol et
            if (context instanceof AlarmKurActivity) {
                AlarmKurActivity activity = (AlarmKurActivity) context;
                activity.checkNextButtonState(); // Bu metodu çağırıyoruz
            }
        });
    }

    @Override
    public int getItemCount() {
        return zamanDozList.size();
    }

    public static class ZamanDozViewHolder extends RecyclerView.ViewHolder {
        TextView txtTime, txtDose;
        ImageView btnDelete;

        public ZamanDozViewHolder(View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDose = itemView.findViewById(R.id.txtDose);
            btnDelete = itemView.findViewById(R.id.btnDelete); // Silme butonu
        }
    }
}
