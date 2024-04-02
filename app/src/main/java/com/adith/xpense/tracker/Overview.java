package com.adith.xpense.tracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adith.xpense.tracker.models.Expense;
import com.adith.xpense.tracker.utils.FireBase;
import com.google.firebase.database.DataSnapshot;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Overview extends AppCompatActivity {
    PieChart pieChart;
    BarChart BarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_overview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle bundle = getIntent().getExtras();
        String userId = bundle.getString("userId");

        DateFormat dateFormat = new SimpleDateFormat("MMMM");

        pieChart = findViewById(R.id.piechart);
        BarChart = findViewById(R.id.barchart);

        FireBase.getExpenses(userId, task -> {
            if (task.isSuccessful()) {
                float bills = 0, clothes = 0, food = 0, groceries = 0, travel = 0, others = 0;
                HashMap monthExpense = new HashMap();

                DataSnapshot snapshot = (DataSnapshot) task.getResult();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Expense expense = child.getValue(Expense.class);
                    String month = dateFormat.format(expense.date);
                    if (monthExpense.containsKey(month)) {
                        monthExpense.put(month, (float) monthExpense.get(month) + expense.amount);
                    } else {
                        monthExpense.put(month, expense.amount);
                    }
                    switch (expense.category) {
                        case "Bills":
                            bills += expense.amount;
                            break;
                        case "Clothes":
                            clothes += expense.amount;
                            break;
                        case "Food":
                            food += expense.amount;
                            break;
                        case "Groceries":
                            groceries += expense.amount;
                            break;
                        case "Travel":
                            travel += expense.amount;
                            break;
                        case "Others":
                            others += expense.amount;
                            break;
                    }
                }
                pieChart.addPieSlice(new PieModel("Bills", bills, getResources().getColor(R.color.Bills)));
                pieChart.addPieSlice(new PieModel("Clothes", clothes, getResources().getColor(R.color.Clothes)));
                pieChart.addPieSlice(new PieModel("Food", food, getResources().getColor(R.color.Food)));
                pieChart.addPieSlice(new PieModel("Groceries", groceries, getResources().getColor(R.color.Groceries)));
                pieChart.addPieSlice(new PieModel("Travel", travel, getResources().getColor(R.color.Travel)));
                pieChart.addPieSlice(new PieModel("Others", others, getResources().getColor(R.color.Others)));
                pieChart.startAnimation();

                for (String month : new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}) {
                    if (monthExpense.containsKey(month)) {
                        BarChart.addBar(new BarModel(month, (Float) monthExpense.get(month), getResources().getColor(R.color.Primary)));
                    }
                }
                BarChart.startAnimation();
            }
        });
    }
}