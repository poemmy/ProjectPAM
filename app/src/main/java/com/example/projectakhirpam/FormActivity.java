package com.example.projectakhirpam;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectakhirpam.helper.DataBaseHelper;

import java.util.List;

public class FormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText dataName, dataAddress, dataPhone, dataAmount;

    RadioGroup discount;

    RadioButton prWeekDay, prWeekEnd;

    Button btn_done;

    String sName, sAddress, sPhone, sProduct, sAmount;

    double dDiscount, dTotal, iPrice;

    int iAmount, iDiscount;

    private Spinner spinner;

    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        dataBaseHelper = new DataBaseHelper(this);
        spinner = findViewById(R.id.spinnerForm);
        btn_done = findViewById(R.id.doneForm);
        dataName = findViewById(R.id.dataName);
        dataAddress = findViewById(R.id.dataAddress);
        dataPhone = findViewById(R.id.dataPhone);
        discount = findViewById(R.id.promoGroup);
        prWeekDay = findViewById(R.id.prWeekDay);
        prWeekEnd = findViewById(R.id.prWeekEnd);
        dataAmount = findViewById(R.id.dataAmount);

        spinner.setOnItemSelectedListener(this);

        loadSpinnerData();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sName = dataName.getText().toString();
                sAddress = dataAddress.getText().toString();
                sPhone = dataPhone.getText().toString();
                sAmount = dataAmount.getText().toString();

                if (sName.isEmpty() || sAddress.isEmpty() || sPhone.isEmpty() || sAmount.isEmpty()){
                    Toast.makeText(FormActivity.this, "(*) tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

                if (prWeekDay.isChecked()){
                    dDiscount = 0.05;
                } else if (prWeekEnd.isChecked()) {
                    dDiscount = 0.15;
                }

                switch (sProduct) {
                    case "Orange":
                        iPrice = 6.40;
                        break;
                    case "Green Color":
                        iPrice = 3.70;
                        break;
                    case "Passion Fruit":
                        iPrice = 8.98;
                        break;
                    case "Carrot":
                        iPrice = 2.48;
                        break;
                    case "Cherry":
                        iPrice = 11.70;
                        break;
                    case "Radish":
                        iPrice = 5.24;
                        break;
                    case "Apple":
                        iPrice = 7.20;
                        break;
                    case "Pepper":
                        iPrice = 8.96;
                        break;
                    case "Grape":
                        iPrice = 12.50;
                        break;
                }

                iAmount = Integer.parseInt(sAmount);
                iDiscount = (int) (dDiscount * 100);
                dTotal = (iPrice * iAmount) - (iPrice * iAmount * dDiscount);

                SQLiteDatabase databaseH = dataBaseHelper.getWritableDatabase();
                databaseH.execSQL("INSERT INTO buyers (name, address, no_hp) VALUES ('" +
                        sName + "','" +
                        sAddress + "','" +
                        sPhone + "');");

                databaseH.execSQL("INSERT INTO shop (name_product, name, discount , amount, total) VALUES ('" +
                        sProduct + "','" +
                        sName + "','" +
                        iDiscount + "','" +
                        iAmount + "','" +
                        dTotal + "');");
                BuyerActivity.m.RefreshList();
                finish();

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadSpinnerData() {
        DataBaseHelper dbh = new DataBaseHelper(getApplicationContext());
        List<String> categories = dbh.getAllCategorries();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sProduct = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}