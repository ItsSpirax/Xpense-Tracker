package com.adith.xpense.tracker;
import com.adith.xpense.tracker.models.*;
import com.adith.xpense.tracker.utils.FireBase;
import com.google.firebase.database.DataSnapshot;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class expenseEntry extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FireBase.init();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_entry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Get bundle with user id
        Bundle bundle = getIntent().getExtras();
        String userId = bundle.getString("userId");
        spinner = findViewById(R.id.spinner);
    }
}