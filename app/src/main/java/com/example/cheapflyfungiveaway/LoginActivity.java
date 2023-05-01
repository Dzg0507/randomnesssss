package com.example.cheapflyfungiveaway;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Button createAccountButton1;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        createAccountButton1 = findViewById(R.id.createAccountButton1);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // TODO: Add code to validate the username and password

            // Launch the GameActivity
            Intent intent = new Intent(LoginActivity.this, GameSelectionActivity.class);
            startActivity(intent);
        });

        createAccountButton1.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });

        // Create the accounts table
        db.execSQL("CREATE TABLE IF NOT EXISTS accounts (username TEXT PRIMARY KEY, password TEXT)");
    }
}
