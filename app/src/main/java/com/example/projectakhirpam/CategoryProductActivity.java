package com.example.projectakhirpam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectakhirpam.helper.DataBaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CategoryProductActivity extends AppCompatActivity {

    TextView fname;

    String[] list;

    ListView listView;

    Menu menu;

    protected Cursor cursor;

    DataBaseHelper dbcenter;

    @SuppressLint("StaticFieldLeak")
    public static CategoryProductActivity ct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_product_activity);

        ct = this;
        dbcenter = new DataBaseHelper(this);
        fname = findViewById(R.id.nama);

        RefreshList();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void RefreshList() {
        SQLiteDatabase sqLiteDatabase = dbcenter.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM product", null);
        list = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            list[i] = cursor.getString(0);
        }
        listView = findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, list));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = list[arg2];
                Intent i = new Intent(CategoryProductActivity.this, DetailProductActivity.class);
                i.putExtra("name", selection);
                startActivity(i);

            }
        });
    }
}
