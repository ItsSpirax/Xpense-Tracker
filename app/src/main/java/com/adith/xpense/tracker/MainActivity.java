package com.adith.xpense.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adith.xpense.tracker.utils.FireBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView name;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);

        FireBase.init();

        if (mAuth.getCurrentUser() != null) {
            onResume();
        } else {
            Toast.makeText(this, "Please login to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FireBase.getUser(mAuth.getCurrentUser().getUid(), task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = (DataSnapshot) task.getResult();
                String user = snapshot.child("name").getValue(String.class);
                if (user != null) {
                    name.setText(user);

                    FireBase.getExpenses(mAuth.getCurrentUser().getUid(), task1 -> {
                        if (task1.isSuccessful()) {
                            DataSnapshot snapshot1 = (DataSnapshot) task1.getResult();
                            int total = 0;
                            int monthlyTotal = 0;
                            int avg = 0;

                            for (DataSnapshot child : snapshot1.getChildren()) {
                                total += child.child("amount").getValue(Integer.class);

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(child.child("date").getValue(Date.class));
                                if (calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)) {
                                    monthlyTotal += child.child("amount").getValue(Integer.class);
                                }
                            }
                            avg = total / (int) snapshot1.getChildrenCount();

                            String totalStr = "₹" + String.format("%,d", total);
                            String monthlyStr = "₹" + String.format("%,d", monthlyTotal);
                            String avgStr = "₹" + String.format("%,d", avg);

                            TextView totalView = findViewById(R.id.total);
                            TextView monthlyView = findViewById(R.id.monthly);
                            TextView avgView = findViewById(R.id.average);

                            totalView.setText(totalStr);
                            monthlyView.setText(monthlyStr);
                            avgView.setText(avgStr);
                        }
                    });
                } else {
                    Toast.makeText(this, "Please login to continue", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, login.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void viewExpenses(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", mAuth.getCurrentUser().getUid());
        Intent intent = new Intent(this, listExpense.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void addExpense(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", mAuth.getCurrentUser().getUid());
        Intent intent = new Intent(this, addExpense.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void logout(View view) {
        mAuth.signOut();
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}