package com.example.projectakhirpam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button category, shop, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        logout = findViewById(R.id.btn_logout);
        category = findViewById(R.id.btn_ctg);
        shop = findViewById(R.id.btn_shop);

        logout.setOnClickListener(this);
        category.setOnClickListener(this);
        shop.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(category.getId() == view.getId()){
            Intent a = new Intent(MainActivity.this, CategoryProductActivity.class);
            startActivity(a);

        } else if (shop.getId() == view.getId()) {
            Intent b = new Intent(MainActivity.this, BuyerActivity.class );
            startActivity(b);

        } else if (logout.getId() == view.getId()) {
            logoutUser();
        }
    }

    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Intent logout = new Intent(this, LoginActivity.class);
        startActivity(logout);
        finish();
    }




}