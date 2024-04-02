package com.adith.xpense.tracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adith.xpense.tracker.models.Expense;
import com.adith.xpense.tracker.models.ExpensesRecyler;
import com.adith.xpense.tracker.utils.FireBase;
import com.adith.xpense.tracker.utils.expenseAdapter;
import com.google.firebase.database.DataSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class listExpense extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FireBase.init();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YY");

        Bundle bundle = getIntent().getExtras();
        String userId = bundle.getString("userId");

        FireBase.getExpenses(userId, task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = (DataSnapshot) task.getResult();
                RecyclerView rv = findViewById(R.id.expenseList);
                ArrayList<ExpensesRecyler> unsortedexpenses = new ArrayList<>();
                ArrayList<ExpensesRecyler> expenses = new ArrayList<>();

                for (DataSnapshot child : snapshot.getChildren()) {
                    Expense expense = child.getValue(Expense.class);
                    assert expense != null;
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
                    unsortedexpenses.add(new ExpensesRecyler(expense.name, "₹" + expense.amount, dateFormat.format(expense.date), image));
                }

                for (int i = 0; i < unsortedexpenses.size(); i++) {
                    int min = i;
                    for (int j = i + 1; j < unsortedexpenses.size(); j++) {
                        String d1 = unsortedexpenses.get(j).date;
                        String d2 = unsortedexpenses.get(min).date;
                        if (Integer.parseInt(d1.substring(6) + d1.substring(3, 5) + d1.substring(0, 2)) < Integer.parseInt(d2.substring(6) + d2.substring(3, 5) + d2.substring(0, 2))) {
                            min = j;
                        }
                    }
                    ExpensesRecyler temp = unsortedexpenses.get(i);
                    unsortedexpenses.set(i, unsortedexpenses.get(min));
                    unsortedexpenses.set(min, temp);
                }

                for (int i = 0; i < unsortedexpenses.size(); i++) {
                    expenses.add(unsortedexpenses.get(unsortedexpenses.size() - i - 1));
                }

                expenseAdapter adapter = new expenseAdapter(expenses);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(this));
            }
        });


    }
}