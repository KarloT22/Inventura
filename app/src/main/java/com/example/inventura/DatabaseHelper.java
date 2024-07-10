package com.example.inventura;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "InventoryDB";
    private static final int DATABASE_VERSION = 1;

    // Table names and column names
    public static final String TABLE_DATA_SET1 = "dataSet1";
    public static final String TABLE_DATA_SET2 = "dataSet2";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_QUANTITY = "quantity";

    // SQL statements to create tables
    private static final String CREATE_TABLE_DATA_SET1 = "CREATE TABLE " + TABLE_DATA_SET1 + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ITEM + " TEXT UNIQUE)";

    private static final String CREATE_TABLE_DATA_SET2 = "CREATE TABLE " + TABLE_DATA_SET2 + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ITEM + " TEXT UNIQUE," +
            COLUMN_QUANTITY + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DATA_SET1);
        db.execSQL(CREATE_TABLE_DATA_SET2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA_SET1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA_SET2);

        // Create tables again
        onCreate(db);
    }

    // Insert item into dataSet1 table
    public long insertItemDataSet1(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM, item);
        long id = db.insert(TABLE_DATA_SET1, null, values);
        db.close();
        return id;
    }

    // Insert item into dataSet2 table with quantity
    public long insertItemDataSet2(String item, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM, item);
        values.put(COLUMN_QUANTITY, quantity);
        long id = db.insert(TABLE_DATA_SET2, null, values);
        db.close();
        return id;
    }

    // Get all items from dataSet1 or dataSet2 table
    public ArrayList<String> getAllItems(String tableName) {
        ArrayList<String> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ITEM};

        Cursor cursor = db.query(tableName, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String item = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM));
                itemList.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return itemList;
    }

    // Delete item from dataSet1 or dataSet2 table
    public void deleteItem(String tableName, String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, COLUMN_ITEM + "=?", new String[]{item});
        db.close();
    }
}