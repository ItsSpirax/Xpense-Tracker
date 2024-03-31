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

import com.adith.xpense.tracker.models.User;
import com.adith.xpense.tracker.utils.FireBase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class signup extends AppCompatActivity {

    EditText name, email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FireBase.init();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View view) {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        String t1 = email.getText().toString();
        String t2 = password.getText().toString();
        String t3 = name.getText().toString();
        if (t1.isEmpty() || t2.isEmpty() || t3.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter all fields!", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(t1, t2)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FireBase.addUser(new User(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(), name.getText().toString(), email.getText().toString()), task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Account Created!", Toast.LENGTH_SHORT).show();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("email", t1);
                                    Intent i = new Intent(getApplicationContext(), login.class);
                                    i.putExtras(bundle);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Firebase Error!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            if (Objects.equals(Objects.requireNonNull(task.getException()).getMessage(), "The email address is already in use by another account.")) {
                                email.setError("Email already in use!");
                                email.setText("");
                                password.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "Password should be at least 6 characters!", Toast.LENGTH_SHORT).show();
                                password.setText("");
                            }
                        }
                    });
        }
    }

    public void login(View view) {
        Intent i = new Intent(getApplicationContext(), login.class);
        startActivity(i);
    }
}