package com.example.projectakhirpam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectakhirpam.helper.DataBaseHelper;

public class DetailProductActivity extends AppCompatActivity {

    protected Cursor cursor;

    String jProduct, jPrice, jImage, jDesc;

    DataBaseHelper dataBaseHelper;
    @SuppressLint({"DiscouragedApi", "SetTextI18n"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_sayur);

        Bundle accept = getIntent().getExtras();

        dataBaseHelper = new DataBaseHelper(this);

        String name_product = accept.getString("name_product");

        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("select * from product where name_product = '" + name_product + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            jProduct = cursor.getString(0);
            jPrice = cursor.getString(1);
            jDesc = cursor.getString(2);
        }

        switch (jProduct) {
            case "Orange":
                jImage = "orange";
                break;
            case "Green Color":
                jImage = "green_colar";
                break;
            case "Passion Fruit":
                jImage = "passion_fruit";
                break;
            case "Carrot":
                jImage = "carrot";
                break;
            case "Cherry":
                jImage = "cherry";
                break;
            case "Radish":
                jImage = "radish";
                break;
            case "Apple":
                jImage = "apple";
                break;
            case "Pepper":
                jImage = "pepper";
                break;
            case "Grape":
                jImage = "grape";
                break;

        }

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView ivImage = findViewById(R.id.img_product);
        TextView tvProduct = findViewById(R.id.nm_product);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tvPrice = findViewById(R.id.price_product);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tvDesc = findViewById(R.id.desc_product);

        int imageResource = getResources().getIdentifier(jImage, "drawable", getPackageName());
        ivImage.setImageResource(imageResource);
        tvProduct.setText(jProduct);
        tvPrice.setText("$" + jPrice);
        tvDesc.setText(jDesc);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
