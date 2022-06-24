package com.dpex.segundata.Handler;

/**
 * Created by USER on 11/22/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dpex.segundata.Object.CartDBObject;

import java.util.ArrayList;
import java.util.List;



public class CartDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CartDB";
    Context mycontext ;
    // Contacts table name
    private static final String TABLE_SMS = "carttbl";

    // Contacts Table Columns names
    //SETTINGS
    private static final String KEY_ID = "id";
 
    private static final String KEY_NETWORKTYPE = "networkprice";
    private static final String KEY_SERVICETYPE = "serviceprice";
    private static final String KEY_PRICE= "price";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_CODE = "code";
    private static final String KEY_UNIT= "unit";
    private static final String KEY_BUNDLEID= "bundleid";
    private static final String KEY_DELETE= "deletex";

    public CartDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mycontext = context ;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NETWORKTYPE + " TEXT,"
                + KEY_SERVICETYPE + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_MOBILE + " TEXT,"
                + KEY_CODE + " TEXT,"
                + KEY_UNIT + " TEXT,"
                + KEY_BUNDLEID + " TEXT,"
                + KEY_DELETE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);





    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */



    // Adding new contact
    public void addContact(CartDBObject contact) {
        SQLiteOpenHelper helper = new CartDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NETWORKTYPE, contact.getNetworkType());
        values.put(KEY_SERVICETYPE, contact.getServiceType());
        values.put(KEY_PRICE, contact.getPrice());
        values.put(KEY_MOBILE, contact.getMobile());
        values.put(KEY_CODE, contact.getCode());
        values.put(KEY_UNIT, contact.getUnit());
        values.put(KEY_BUNDLEID, contact.getBundleID());
        values.put(KEY_DELETE, contact.getDelete());
        // Inserting Row
        db.insert(TABLE_SMS, null, values);
        db.close(); // Closing database connection



    }

    // Getting single contact
    CartDBObject getContact(int id) {
        SQLiteOpenHelper helper = new CartDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getReadableDatabase();
        CartDBObject contact = null ;
        Cursor cursor = null ;
        try {
            cursor  = db.query(TABLE_SMS, new String[]{KEY_ID,
                            KEY_NETWORKTYPE,
                            KEY_SERVICETYPE,
                    KEY_PRICE,
                    KEY_MOBILE,
                            KEY_CODE,
                            KEY_UNIT,
                            KEY_BUNDLEID,
                    KEY_DELETE}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            contact = new CartDBObject(Integer.parseInt(cursor.getString(0)),
                  cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));

        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<CartDBObject> getAllContacts() {

        List<CartDBObject> contactList = new ArrayList<CartDBObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SMS ;
        SQLiteOpenHelper helper = new CartDatabaseHandler(mycontext);
        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor cursor = null ;
        try {
            cursor  = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    CartDBObject contact = new CartDBObject();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setNetworkType(cursor.getString(1));
                    contact.setServiceType(cursor.getString(2));
                    contact.setPrice(cursor.getString(3));
                    contact.setMobile(cursor.getString(4));
                    contact.setCode(cursor.getString(5));
                    contact.setUnit(cursor.getString(6));
                    contact.setBundleID(cursor.getString(7));
                    contact.setDelete(cursor.getString(8));

                    // Adding contact to list
                    contactList.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(CartDBObject contact) {
        SQLiteOpenHelper helper = new CartDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NETWORKTYPE, contact.getNetworkType());
        values.put(KEY_SERVICETYPE, contact.getServiceType());
        values.put(KEY_PRICE, contact.getPrice());
        values.put(KEY_MOBILE, contact.getMobile());
        values.put(KEY_CODE, contact.getCode());
        values.put(KEY_UNIT, contact.getUnit());
        values.put(KEY_BUNDLEID, contact.getBundleID());
        values.put(KEY_DELETE, contact.getDelete());
        // updating row
        return db.update(TABLE_SMS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
    }



    public void delete(CartDBObject contact) {
        SQLiteOpenHelper helper = new CartDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_SMS, KEY_DELETE + " = ?",
                new String[]{String.valueOf(contact.getDelete())});
        db.close();
    }

    public void deleteuserid(CartDBObject contact) {
        SQLiteOpenHelper helper = new CartDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_SMS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }

}