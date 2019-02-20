package com.example.ankur.caronsale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.activity.LoginActivity;
import com.example.ankur.caronsale.helper.SQLiteHandler;
import com.example.ankur.caronsale.helper.SessionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtLoginClick;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLoginClick = (TextView) findViewById(R.id.txtLoginClick);

        SpannableString content = new SpannableString("Conitnue to Login/SignUp");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txtLoginClick.setText(content);
        txtLoginClick.setOnClickListener(this);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        Intent loginActivity = new Intent(this,LoginActivity.class);
        startActivity(loginActivity);
    }
}
