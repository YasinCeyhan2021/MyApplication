package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class AdapterBaslangicVeSure extends RecyclerView.Adapter<AdapterBaslangicVeSure.ViewHolder> {
    private List<ModelBaslangicVeSure> baslangicVeSureList;
    private OnItemClickListener onItemClickListener;

    public AdapterBaslangicVeSure(List<ModelBaslangicVeSure> baslangicVeSureList, OnItemClickListener onItemClickListener) {
        this.baslangicVeSureList = baslangicVeSureList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baslangic_ve_sure, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelBaslangicVeSure model = baslangicVeSureList.get(position);
        holder.txtBaslangicTarihi.setText(model.getBaslangicTarihi());
        holder.txtSure.setText(model.getSure());
        holder.txtFrequency.setText(model.getFrequency());

        // Düzenleme butonuna tıklandığında
        holder.btnEdit.setOnClickListener(v -> onItemClickListener.onEditClick(position));
    }

    @Override
    public int getItemCount() {
        return baslangicVeSureList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBaslangicTarihi, txtSure, txtFrequency;
        ImageView btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            txtBaslangicTarihi = itemView.findViewById(R.id.txtBaslangicTarihi);
            txtSure = itemView.findViewById(R.id.txtSure);
            txtFrequency = itemView.findViewById(R.id.txtFrequency);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

    // Düzenleme butonuna tıklama olayını tanımlıyoruz
    public interface OnItemClickListener {
        void onEditClick(int position);
    }
}
