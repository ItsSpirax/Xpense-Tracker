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

    public static void addUser(String userId, User user, OnCompleteListener listener) {
        mDatabase.child("users").child(userId).setValue(user).addOnCompleteListener(listener);
    }

    public static void getUser(String userId, OnCompleteListener listener) {
        mDatabase.child("users").child(userId).get().addOnCompleteListener(listener);
    }

    public static void addExpense(String userId, Expense expense, OnCompleteListener listener) {
        mDatabase.child("users").child(userId).child("expenses").child(mDatabase.push().getKey()).setValue(expense).addOnCompleteListener(listener);
    }

    public static void getExpenses(String userId, OnCompleteListener listener) {
        mDatabase.child("users").child(userId).child("expenses").get().addOnCompleteListener(listener);
    }

    public static void updateExpense(String userId, String expenseId, Expense expense, OnCompleteListener listener) {
        mDatabase.child("users").child(userId).child("expenses").child(expenseId).setValue(expense).addOnCompleteListener(listener);
    }

    public static void deleteExpense(String userId, String expenseId, OnCompleteListener listener) {
        mDatabase.child("users").child(userId).child("expenses").child(expenseId).removeValue().addOnCompleteListener(listener);
    }
}
