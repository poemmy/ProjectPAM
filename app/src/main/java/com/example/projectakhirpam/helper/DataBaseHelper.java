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
                "description text," +
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
                "6.40," +
                "'Oranges are a type of low calories, " +
                "highly nutritious citrus fruits, " +
                "as apart of healthful and varied diet and oranges contribute to strong and clear skin.'" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Green Color'," +
                "3.70," +
                "'Green Collar are a very good source of vitamins and minerals and also can protect the " +
                "body from several respiratory viral infections such as flu and colds. Its vitamin C can " +
                "also help relieve sinus inflammation and reduce the risk of asthma.'"+
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Passion Fruit'," +
                "8.96," +
                "'The content in passion fruit in related research contains many potent phytochemicals to " +
                "kill cancer cells in the body, these phytochemicals include polyphenols, carotenoins and " +
                "various other beneficial compounds.Sep 18, 2013'" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Carrot'," +
                "2.48," +
                "'Carrots are a good source of several vitamins and minerals, particularly vitamin A, biotin, vitamin K1, potassium and vitamin B6. " +
                "In addition, carrots are also rich in beta-carotene. That is why it is not surprising that the benefits of carrots can increase " +
                "the body is immunity'" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Cherry'," +
                "11.70," +
                "'Cherries are rich in antioxidants which play an important role in helping to slow down the aging process and combat oxidative stress. " +
                "Cherries also contain polyphenolic flavonoid compounds known as anthocyanin glycosides which can help prevent blood clots'" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Radish'," +
                "5.24," +
                "'Radish is a type of vegetable that is rich in fiber and vitamins. This content plays an important role in maintaining digestive health. " +
                "In addition, radishes also have ingredients that can help prevent constipation and increase the efficiency of absorption of nutrients in the intestine.'" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Apple'," +
                "7.20," +
                "'Apples are a healthy food that contains almost no fat and are cholesterol free. In addition, it contains high levels of antioxidants, namely plant compounds, " +
                "such as flavonoids, fiber, water, vitamins and minerals. This one fruit does contain sugar and carbohydrates.'" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Pepper'," +
                "8.96," +
                "'Pepper contains antioxidants that are quite high. This substance is known to be able to ward off free radicals that damage healthy cells in the body. " +
                "Damage to these cells can increase the risk of premature aging, heart disease, and cancer'" +
                "); " +
                "");
        sqLiteDatabase.execSQL("insert into product values (" +
                "'Grape'," +
                "12.50," +
                "'Grapes are one of the fruits with high levels of copper and vitamin K. Both of these nutrients have important functions in the body related to energy production and blood clotting. " +
                "Some of the vitamins are also very good for supporting the growth and development of children.'" +
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
