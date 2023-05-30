package com.example.projectakhirpam;

import static android.R.layout.simple_list_item_1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectakhirpam.helper.DataBaseHelper;

public class BuyerActivity extends AppCompatActivity {

    String[] list;
    int[] id;
    ListView listView;
    Menu menu;
    protected Cursor cursor;
    DataBaseHelper dbcenter;
    @SuppressLint("StaticFieldLeak")
    public static BuyerActivity m;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_activity);

        Button add = findViewById(R.id.addPembeli);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(BuyerActivity.this, FormActivity.class);
                startActivity(p);
            }
        });

        m = this;
        dbcenter = new DataBaseHelper(this);

        RefreshList();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM buyers", null);
        list = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            list[i] = cursor.getString(0);
        }

        listView = findViewById(R.id.listView1);
        listView.setAdapter(new ArrayAdapter(this, simple_list_item_1, list));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = list[arg2];
                final CharSequence[] dialogitem = {"Check Data", "Delete Data", "Confirm Payment"};
                AlertDialog.Builder builder = new AlertDialog.Builder(BuyerActivity.this);
                builder.setTitle("Select");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: {
                                Intent i = new Intent(BuyerActivity.this, FillpageActivity.class);
                                i.putExtra("name", selection);
                                startActivity(i);
                                break;
                            }
                            case 1: {
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("DELETE FROM buyers where name = '" + selection + "'");
                                db.execSQL("DELETE FROM shop where name = '" + selection + "'");
                                RefreshList();
                                break;
                            }
                            case 2: {
                                Intent cp = new Intent(BuyerActivity.this, PaymentActivity.class);
                                startActivity(cp);
                                break;
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) listView.getAdapter()).notifyDataSetInvalidated();

    }
}