package com.adith.xpense.tracker.models;

public class Expense {
    public String name, category;
    public float amount;
    public String date;

    public Expense() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Expense(String title, String category, float amount, String date) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }
}
