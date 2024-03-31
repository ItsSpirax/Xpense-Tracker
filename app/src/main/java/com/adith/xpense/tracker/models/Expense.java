package com.adith.xpense.tracker.models;

public class Expense {
    public String id;
    public String title;
    public String category;
    public String amount;
    public String date;

    public Expense() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Expense(String id, String title, String category, String amount, String date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }
}
