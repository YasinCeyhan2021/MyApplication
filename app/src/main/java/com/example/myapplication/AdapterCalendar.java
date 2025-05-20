package com.example.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterCalendar extends RecyclerView.Adapter<AdapterCalendar.CalendarViewHolder> {

    private List<ModelCalendar> calendarList;
    private int selectedPosition = -1;
    private OnDateSelectedListener listener;

    public interface OnDateSelectedListener {
        void onDateSelected(String dateString);
    }

    public AdapterCalendar(List<ModelCalendar> calendarList, OnDateSelectedListener listener) {
        this.calendarList = calendarList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        ModelCalendar model = calendarList.get(position);

        holder.textDay.setText(String.valueOf(model.getDay()));
        holder.textWeekday.setText(model.getWeekday());

        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.bg_calendar_selected);
            holder.textDay.setTextColor(Color.WHITE);
            holder.textWeekday.setTextColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.bg_calendar_unselected);
            holder.textDay.setTextColor(Color.BLACK);
            holder.textWeekday.setTextColor(Color.BLACK);
        }

        holder.itemView.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION) {
                selectedPosition = currentPos;
                notifyDataSetChanged();
                listener.onDateSelected(calendarList.get(currentPos).getDateString());
            }
        });
    }


    @Override
    public int getItemCount() {
        return calendarList.size();
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView textDay, textWeekday;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            textDay = itemView.findViewById(R.id.text_day);
            textWeekday = itemView.findViewById(R.id.text_weekday);
        }
    }
}
