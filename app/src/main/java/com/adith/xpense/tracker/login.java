package com.adith.xpense.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        mAuth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String email = bundle.getString("email");
            if (email != null) {
                EditText emailField = findViewById(R.id.email);
                emailField.setText(email);
            }
        }
    }

    public void Login(View view) {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        String t1 = email.getText().toString();
        String t2 = password.getText().toString();

        if (t1.isEmpty() || t2.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter both Email and Password!", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(t1, t2)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            password.setText("");
                            Toast.makeText(getApplicationContext(), "Logged In!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                            email.setText("");
                            password.setText("");
                        }
                    });
        }
    }

    public void signup(View view) {
        Intent i = new Intent(getApplicationContext(), signup.class);
        startActivity(i);
    }
}