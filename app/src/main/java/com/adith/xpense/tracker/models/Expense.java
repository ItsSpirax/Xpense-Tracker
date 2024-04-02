package com.adith.xpense.tracker.models;

import java.util.Date;

public class Expense {
    public String name, category;
    public float amount;
    public Date date;

    public Expense() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Expense(String name, String category, float amount, Date date) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }
}
