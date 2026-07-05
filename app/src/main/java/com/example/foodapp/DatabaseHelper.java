package com.example.foodapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FoodApp.db";
    private static final int DATABASE_VERSION = 5;

    // Users table
    public static final String TABLE_USERS = "users";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    public static final String COL_MOBILE = "mobile";
    public static final String COL_PASSWORD = "password";

    // Orders table
    public static final String TABLE_ORDERS = "orders";
    public static final String COL_ORDER_ID = "order_id";
    public static final String COL_ORDER_DATE = "order_date";
    public static final String COL_ORDER_TOTAL = "order_total";
    public static final String COL_ORDER_ITEMS = "order_items";
    public static final String COL_ORDER_ADDRESS = "order_address";
    public static final String COL_ORDER_PAYMENT = "order_payment";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, mobile TEXT, password TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_ORDERS + " (order_id INTEGER PRIMARY KEY AUTOINCREMENT, order_date TEXT, order_total TEXT, order_items TEXT, " + COL_ORDER_ADDRESS + " TEXT, " + COL_ORDER_PAYMENT + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public boolean insertUser(String name, String email, String mobile, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_MOBILE, mobile);
        contentValues.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_EMAIL + "=? AND " + COL_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_EMAIL + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Orders logic
    public void insertOrder(String date, String total, String items, String address, String payment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ORDER_DATE, date);
        contentValues.put(COL_ORDER_TOTAL, total);
        contentValues.put(COL_ORDER_ITEMS, items);
        contentValues.put(COL_ORDER_ADDRESS, address);
        contentValues.put(COL_ORDER_PAYMENT, payment);
        db.insert(TABLE_ORDERS, null, contentValues);
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS + " ORDER BY " + COL_ORDER_ID + " DESC", null);
    }

    public Cursor getOrderById(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS + " WHERE " + COL_ORDER_ID + " = ?", new String[]{String.valueOf(orderId)});
    }

    public int getLastOrderId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + COL_ORDER_ID + ") FROM " + TABLE_ORDERS, null);
        int id = 0;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public void clearAllOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, null, null);
        db.close();
    }

    public boolean deleteOrder(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ORDERS, COL_ORDER_ID + "=?", new String[]{String.valueOf(orderId)});
        return result > 0;
    }

    public Cursor getUserData(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_EMAIL + " = ?", new String[]{email});
    }

    public boolean updateProfile(String email, String name, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_MOBILE, mobile);
        int result = db.update(TABLE_USERS, contentValues, COL_EMAIL + " = ?", new String[]{email});
        return result > 0;
    }

    public boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PASSWORD, newPassword);
        int result = db.update(TABLE_USERS, contentValues, COL_EMAIL + " = ?", new String[]{email});
        return result > 0;
    }
}
