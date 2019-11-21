package com.example.ruzbeh.moneymanager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class RecordsManager extends SQLiteOpenHelper {
    static String DATABASE_NAME = "Records", TABLE_NAME = "records", ID = "id", NAME = "name", PRICE =
            "price", DATE = "date", KIND = "kind", DESCRIBE = "describe";
    static Integer DATABASE_VERSION = 1;
    List<Record> records;

    public RecordsManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
                + " text ," + PRICE + " text ," + DATE + " text ," + KIND + " text," + DESCRIBE + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS records");
        onCreate(db);
    }

    public void addRecord(Record record) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(ID, getDatabaseSize());
        values.put(NAME, record.name);
        values.put(PRICE, record.price);
        values.put(DATE, record.date);
        values.put(KIND, record.kind);
        values.put(DESCRIBE, record.describe);
        record.id = getDatabaseSize();
        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public void removeRecord(Integer id) {
        Log.e("REMOVE", "id");
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, ID + "= " + id, null);
        database.close();
    }

    public void editRecord(Integer idPrevious, Record recordEdited) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, recordEdited.name);
        values.put(PRICE, recordEdited.price);
        values.put(DATE, recordEdited.date);
        values.put(KIND, recordEdited.kind);
        values.put(DESCRIBE, recordEdited.describe);
        Log.e("NAME", idPrevious.toString());
        database.update(TABLE_NAME, values, ID + "= " + idPrevious, null);
        database.close();
    }

    public List<Record> getRecords() {
        records = new LinkedList();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        Record record;
        if (cursor.moveToFirst()) {
            do {
                record = new Record(null, null, null, null, null);
                record.id = cursor.getInt(0);
                record.name = cursor.getString(1);
                record.price = cursor.getString(2);
                record.date = cursor.getString(3);
                record.kind = cursor.getString(4);
                record.describe = cursor.getString(5);
                records.add(record);
            } while (cursor.moveToNext());
        }
        return records;
    }


    public int getDatabaseSize() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_NAME, null);
        int count = cursor.getCount();
        return count;
    }

    public List<Record> searchData(String gen, String searched) {
        records = new LinkedList();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor;
        switch (gen) {
            case "title":
                cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + " LIKE '%" + searched + "%'", null);
                break;
            case "price":
                cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + PRICE + " = " + searched, null);
                break;
            case "date":
                cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + DATE + " LIKE '%" + searched + "%'", null);
                break;
            case "transaction":
                cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KIND + " LIKE '%" + searched + "%'", null);
                break;
            case "description":
                cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + DESCRIBE + " LIKE '%" + searched + "%'", null);
                break;
            default:
                cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + " LIKE '%" + searched + "%'", null);
        }
        Record record;
        if (cursor.moveToFirst()) {
            do {
                record = new Record(null, null, null, null, null);
                record.id = cursor.getInt(0);
                record.name = cursor.getString(1);
                record.price = cursor.getString(2);
                record.date = cursor.getString(3);
                record.kind = cursor.getString(4);
                record.describe = cursor.getString(5);
                records.add(record);
            } while (cursor.moveToNext());
        }
        return records;
    }
}
