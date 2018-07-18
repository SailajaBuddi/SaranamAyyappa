
package com.anvesh.saranamayyappa.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.anvesh.saranamayyappa.model.Contact;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String TAG = "DataBaseHelper";

    private static String DB_NAME = "ayyappa.sql";

    private static DataBaseHelper dataBaseHelper;
    private static SQLiteDatabase db;

    // Contacts Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_PH_NO = "PHONE_NUMBER";
    private static final String KEY_IMAGE_PATH = "IMAGE_PATH";

    //Table names
    private static final String TABLE_CONTACTS = "CONTACTS";

    //Create Tables
    private String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_PH_NO + " TEXT,"
            + KEY_IMAGE_PATH + " TEXT" + ")";


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 14);
    }

    public static synchronized DataBaseHelper getInstance(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(context);
        }
        openDB();
        return dataBaseHelper;
    }

    public static synchronized void openDB() throws SQLException {
        if (db == null) {
            db = dataBaseHelper.getWritableDatabase();
        } else if (!db.isOpen()) {
            db = dataBaseHelper.getWritableDatabase();
        }
    }

    public synchronized void closeDB() {
        if (db != null && db.isOpen())
            db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public boolean addAllContacts(ArrayList<Contact> contactArrayList) {
        if (null == contactArrayList) {
            return false;
        }
        for (Contact contact : contactArrayList) {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contact.getName());
            values.put(KEY_PH_NO, contact.getNumberWithCountryCode());
            values.put(KEY_IMAGE_PATH, contact.getContactImagePath());
            db.insert(TABLE_CONTACTS, null, values);
            Log.e("Contact>",""+contact.getNumberWithCountryCode());
            Log.e("DB>", "contacts added");
        }
        Log.e("DB>", "contacts added");
        return true;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        //Cursor cursor = db.rawQuery(selectQuery, null);
//        Cursor cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, KEY_NAME + " Collate NOCASE");
        Cursor cursor = db.query(true, TABLE_CONTACTS, null,
                null, null, KEY_PH_NO,
                null, KEY_NAME + " Collate NOCASE", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getString(1), cursor.getString(2), cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return contactList;
    }

    public void clearContacts() {
        db.delete(TABLE_CONTACTS, null, null);
        Log.d("DB>", "contacts cleared");
    }


}
