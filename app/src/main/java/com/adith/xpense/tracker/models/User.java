package com.adith.xpense.tracker.models;

public class User {
    public String id;
    public String name;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
    }
}
