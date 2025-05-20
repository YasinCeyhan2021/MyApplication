package com.example.myapplication;

public class ModelCalendar {
    private int day;
    private String weekday;
    private String dateString; // "2025-05-12" gibi

    public ModelCalendar(int day, String weekday, String dateString) {
        this.day = day;
        this.weekday = weekday;
        this.dateString = dateString;
    }

    public int getDay() { return day; }
    public String getWeekday() { return weekday; }
    public String getDateString() { return dateString; }
}
