package com.adith.xpense.tracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adith.xpense.tracker.models.Expense;
import com.adith.xpense.tracker.utils.FireBase;

import java.util.Calendar;
import java.util.Date;

public class addExpense extends AppCompatActivity {

    Spinner spinner;
    EditText name, amount, date;
    String userId;
    Date dt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FireBase.init();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("userId");

        spinner = findViewById(R.id.category);
        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        date = findViewById(R.id.date);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void saveExpense(View view) {
        String category = (String) spinner.getSelectedItem();
        String nm = name.getText().toString();
        String amt = amount.getText().toString();

        if (nm.isEmpty() || amt.isEmpty() || date.getText().toString().isEmpty() || category.equals("Select")) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Expense expense = new Expense(nm, category, Integer.parseInt(amt), dt);

            FireBase.addExpense(userId, expense, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Expense added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to add expense", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void pickDate(View v) {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                addExpense.this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    date.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear + 1) + "/" + year1);
                    dt = new Date(year1, monthOfYear, dayOfMonth);
                },
                year, month, day);
        datePickerDialog.show();
    }
}