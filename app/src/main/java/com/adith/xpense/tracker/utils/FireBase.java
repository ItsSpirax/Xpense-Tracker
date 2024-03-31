package com.adith.xpense.tracker.utils;

import com.adith.xpense.tracker.models.Expense;
import com.adith.xpense.tracker.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBase {
    private static DatabaseReference mDatabase;

    public static void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static void addUser(User user, OnCompleteListener listener) {
        mDatabase.child("users").child(user.id).setValue(user).addOnCompleteListener(listener);
    }

    public static void getUser(String id, OnCompleteListener listener) {
        mDatabase.child("users").child(id).get().addOnCompleteListener(listener);
    }

    public static void addExpense(Expense expense, OnCompleteListener listener) {
        mDatabase.child("expenses").child(expense.id).setValue(expense).addOnCompleteListener(listener);
    }

    public static void getExpense(String id, OnCompleteListener listener) {
        mDatabase.child("expenses").child(id).get().addOnCompleteListener(listener);
    }

    public static void updateExpense(Expense expense, OnCompleteListener listener) {
        mDatabase.child("expenses").child(expense.id).setValue(expense).addOnCompleteListener(listener);
    }

    public static void deleteExpense(Expense expense, OnCompleteListener listener) {
        mDatabase.child("expenses").child(expense.id).removeValue().addOnCompleteListener(listener);
    }
}
