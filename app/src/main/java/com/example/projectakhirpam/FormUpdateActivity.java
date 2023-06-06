package com.example.projectakhirpam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectakhirpam.helper.DataBaseHelper;

public class FormUpdateActivity extends AppCompatActivity {

    EditText dataName, dataAddress, dataPhone, dataAmount;
    Spinner spinnerProduct;
    Button btnUpdate;

    String buyerName;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_update);

        dataBaseHelper = new DataBaseHelper(this);

        dataName = findViewById(R.id.dataNameUpdate);
        dataAddress = findViewById(R.id.dataAddressUpdate);
        dataPhone = findViewById(R.id.dataPhoneUpdate);
        spinnerProduct = findViewById(R.id.spinnerFormUpdate);
        dataAmount = findViewById(R.id.dataAmountUpdate);
        btnUpdate = findViewById(R.id.updateForm);

        buyerName = getIntent().getStringExtra("name");

        loadData();
        loadProductSpinner();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sName = dataName.getText().toString();
                String sAddress = dataAddress.getText().toString();
                String sPhone = dataPhone.getText().toString();
                String sProduct = spinnerProduct.getSelectedItem().toString();
                int sAmount = Integer.parseInt(dataAmount.getText().toString());

                if (sName.isEmpty() || sAddress.isEmpty() || sPhone.isEmpty()) {
                    Toast.makeText(FormUpdateActivity.this, "(*) tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
                database.execSQL("UPDATE buyers SET name = '" + sName + "', address = '" + sAddress + "', no_hp = '" + sPhone + "' WHERE name = '" + buyerName + "'");
                database.execSQL("UPDATE shop SET name = '" + sName + "', amount = " + sAmount + " WHERE name = '" + buyerName + "' AND name_product = '" + sProduct + "'");

                Toast.makeText(FormUpdateActivity.this, "Data pembeli berhasil diperbarui", Toast.LENGTH_SHORT).show();

                BuyerActivity.m.RefreshList();
                finish();
            }
        });
    }

    private void loadData() {
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
        String query = "SELECT * FROM buyers WHERE name = '" + buyerName + "'";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            String address = cursor.getString(1);
            String phone = cursor.getString(2);

            dataName.setText(name);
            dataAddress.setText(address);
            dataPhone.setText(phone);
        }

        cursor.close();
    }

    private void loadProductSpinner() {
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
        String query = "SELECT name_product FROM shop WHERE name = '" + buyerName + "'";
        Cursor cursor = database.rawQuery(query, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(adapter);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("name_product"));
                adapter.add(productName);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
