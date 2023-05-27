package com.example.projectakhirpam.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "abcmarket.db";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
        sqLiteDatabase.execSQL("create table buyers (" +
                "name text," +
                "address text," +
                "no_hp text," +
                "primary key(name)" +
                ");" +
                "");
        sqLiteDatabase.execSQL("create table product (" +
                "name_product text," +
                "price double," +
                "description," +
                "primary key(name_product)" +
                ");" +
                "");
        sqLiteDatabase.execSQL("create table shop (" +
                "name_product text," +
                "name text," +
                "discount int," +
                "amount int," +
                "total double," +
                "foreign key(name_product) references product (name_product)," +
                "foreign key(name) references buyers (name) " +
                ");" +
                "");

        sqLiteDatabase.execSQL("insert into product values (" +
                "'Orange'," +
                "6.40" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Green Color'," +
                "3.70" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Passion Fruit'," +
                "8.96" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Carrot'," +
                "2.48" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Cherry'," +
                "11.70" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Radish'," +
                "5.24" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Apple'," +
                "7.20" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Pepper'," +
                "8.96" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Grape'," +
                "12.50" +
                "); " +
                "");
    }

    public List<String> getAllCategorries(){
        List<String> categories = new ArrayList<String>();
        String selectQuery = "select * from product";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                categories.add(cursor.getString(0));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return categories;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
