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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectakhirpam.helper.DataBaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductActivity extends AppCompatActivity {

    TextView fname;
    List<String> productList;
    RecyclerView recyclerView;
    SearchView search;
    Menu menu;
    protected Cursor cursor;
    DataBaseHelper dbcenter;
    public static CategoryProductActivity ct;
    ProductAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_product_activity);

        ct = this;
        dbcenter = new DataBaseHelper(this);
        fname = findViewById(R.id.Display);

        productList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        SearchView searchView = findViewById(R.id.searchView);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("users").child(currentUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    fname.setText(user.fn);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String productName = productList.get(position);
                // Navigasi ke DetailProductActivity dengan mengirimkan nama produk yang dipilih
                Intent intent = new Intent(CategoryProductActivity.this, DetailProductActivity.class);
                intent.putExtra("name_product", productName);
                startActivity(intent);
            }
        });

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
        SQLiteDatabase sqLiteDatabase = dbcenter.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM product", null);
        productList.clear();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("name_product"));
            productList.add(productName);
            cursor.moveToNext();
        }
        cursor.close();

        adapter.notifyDataSetChanged();
    }

    public void filterList(String query) {
        SQLiteDatabase sqLiteDatabase = dbcenter.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT name_product FROM product WHERE name_product LIKE ?", new String[]{"%" + query + "%"});
        productList.clear();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("name_product"));
            productList.add(productName);
            cursor.moveToNext();
        }
        cursor.close();

        adapter.notifyDataSetChanged();
    }
}
