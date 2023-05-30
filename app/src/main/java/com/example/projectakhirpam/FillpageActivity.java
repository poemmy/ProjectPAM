package com.example.projectakhirpam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.projectakhirpam.helper.DataBaseHelper;

public class FillpageActivity extends AppCompatActivity  {

    String sName, sAddress, sPhone, sProduct, sPrice;

    int iAmount, iDiscount;

    double iTotal, dTotal;

    protected Cursor cursor;

    DataBaseHelper dataBaseHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fillpage);

        dataBaseHelper = new DataBaseHelper(this);

        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("select * from buyers, product, shop where buyers.name = shop.name " +
                "AND product.name_product = shop.name_product " +
                "AND buyers.name = '" + getIntent().getStringExtra("name") + "'",null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            sName = cursor.getString(0);
            sAddress = cursor.getString(1);
            sPhone = cursor.getString(2);
            sProduct = cursor.getString(3);
            sPrice = cursor.getString(4);
            iDiscount = cursor.getInt(8);
            iAmount = cursor.getInt(9);
            dTotal = cursor.getDouble(10);
        }


        TextView tvName = findViewById(R.id.hslNama);
        TextView tvAddress = findViewById(R.id.hslAlamat);
        TextView tvPhone = findViewById(R.id.HTelp);
        TextView tvProduct = findViewById(R.id.hslProduk);

        TextView tvPrice = findViewById(R.id.hslHarga);
        TextView tvAmount = findViewById(R.id.hslJumlah);
        TextView tvDiscount = findViewById(R.id.hslPromo);
        TextView tvTotal = findViewById(R.id.hslTotal);

        tvName.setText("     " + sName);
        tvAddress.setText("     " + sAddress);
        tvPhone.setText("     " + sPhone);
        tvProduct.setText("     " + sProduct);
        tvPrice.setText("      $ " + sPrice);
        tvAmount.setText("     " + iAmount);
        tvDiscount.setText("    " + iDiscount + "%");
        iTotal = dTotal;
        tvTotal.setText("      $ " + iTotal);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}