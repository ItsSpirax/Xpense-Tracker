package com.adith.xpense.tracker.models;

public class ExpensesRecyler {
    public String id, name, amount, date;
    public int image;

    public ExpensesRecyler(String id, String name, String amount, String date, int image) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.image = image;
    }
}