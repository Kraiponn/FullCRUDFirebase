package com.ksntechnology.fullcrudfirebase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class ProductDBHelper extends SQLiteOpenHelper {
    private static ProductDBHelper productDBHelper;
    private static final String DB_NAME = "PRODUCTS";
    private static int DB_VERSION = 1;


    private ProductDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized ProductDBHelper getInstance(Context context) {
        if (productDBHelper == null) {
            productDBHelper = new ProductDBHelper(context.getApplicationContext());
        }

        return productDBHelper;
    }

    public ProductDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
