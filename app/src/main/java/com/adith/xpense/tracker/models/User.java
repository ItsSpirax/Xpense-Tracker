package com.adith.xpense.tracker.models;

import java.util.ArrayList;

public class User {
    public String name, email;
    public ArrayList<Expense> expenses;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.expenses = new ArrayList<>();
    }
}
