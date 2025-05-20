package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterIlac extends RecyclerView.Adapter<AdapterIlac.IlacViewHolder>{

    private List<ModelIlac> ilacList;
    private OnItemClickListener listener;

    public AdapterIlac(List<ModelIlac> ilacList, OnItemClickListener listener) {
        this.ilacList = ilacList;
        this.listener = listener;
    }

    // ViewHolder sınıfı - static değil
    class IlacViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        ImageView arrowRightIcon;

        public IlacViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.ilacIsmi);
            arrowRightIcon = itemView.findViewById(R.id.arrowRightIcon);

            // Tıklama olayını set et
            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(ilacList.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public IlacViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ilac_duzeni, parent, false);
        return new IlacViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IlacViewHolder holder, int position) {
        ModelIlac ilac = ilacList.get(position);
        holder.nameText.setText(ilac.getIsim());
    }

    @Override
    public int getItemCount() {
        return ilacList.size();
    }


    public void updateList(List<ModelIlac> newList) {
        ilacList = newList; // Listeyi güncelle
        notifyDataSetChanged(); // RecyclerView'e yeni veriyi bildir
    }
    public interface OnItemClickListener {
        void onItemClick(ModelIlac ilac);  // Tıklanan item'ı alır
    }

}
