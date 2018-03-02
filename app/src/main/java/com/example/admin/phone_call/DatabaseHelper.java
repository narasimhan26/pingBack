package com.example.admin.phone_call;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2/27/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DB_Name = "LogDetails.db";
    public static String Table_Name = "Details";
    public static String Col_1 = "ID";
    public static String Col_2 = "Name";
    public static String Col_3 = "Number";
    public static String Col_4 = "ThumbNail";
    public static String Col_5 = "Status";

    public DatabaseHelper(Context context) {
        super(context, DB_Name, null, 1);
    }
    //create database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+Table_Name+" ("+Col_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+Col_2+" TEXT," +Col_3+" NUMBER,"+Col_4+" TEXT,"+Col_5+" TEXT,)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }
    //add a row
    public void addRow(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Col_2,contact.getName());
        values.put(Col_3,contact.getNumber());
        values.put(Col_4,contact.getThumbnail());
        values.put(Col_5,contact.getStatus());

        db.insert(Table_Name,null,values);
        db.close();
    }
    //retrive all contacts
    public Cursor getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + Table_Name + "ORDER BY DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Table_Name,null,null,null,null,null,DatabaseHelper.Col_1+ " DESC");


        return cursor;
    }
    //update contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Col_5, contact.getStatus());


        // updating row
        return db.update(Table_Name, values, Col_3 + " = ?",
                new String[] { String.valueOf(contact.getNumber()) });
    }

}
