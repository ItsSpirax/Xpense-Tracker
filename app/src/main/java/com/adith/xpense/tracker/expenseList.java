package com.adith.xpense.tracker;
import com.adith.xpense.tracker.models.*;
import com.adith.xpense.tracker.utils.FireBase;
import com.adith.xpense.tracker.utils.expenseAdapter;
import com.google.firebase.database.DataSnapshot;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class expenseList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FireBase.init();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundle = getIntent().getExtras();
        String userId = bundle.getString("userId");
        FireBase.getExpensse(userId, task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = (DataSnapshot) task.getResult();
                RecyclerView rv = findViewById(R.id.expenseList);
                ArrayList<ExpensesRecyler> expenses = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Expense expense = child.getValue(Expense.class);
                    assert expense != null;
                    // Check category and pass the image accordingly
                    int image = 0;
                    switch (expense.category) {
                        case "Food":
                            image = R.drawable.food;
                            break;
                        case "Clothes":
                            image = R.drawable.clothes;
                            break;
                        case "Travel":
                            image = R.drawable.travel;
                            break;
                        case "Groceries":
                            image = R.drawable.groceries;
                            break;
                        case "Bills":
                            image = R.drawable.bills;
                            break;
                        case "Others":
                            image = R.drawable.others;
                            break;
                    }

                    expenses.add(new ExpensesRecyler(expense.name, "₹" + String.valueOf(expense.amount), expense.date, image));
                }
                expenseAdapter adapter = new expenseAdapter(expenses);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(this));
            }
        });





    }
}