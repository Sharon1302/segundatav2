package com.dpex.segundata.Handler;

/**
 * Created by USER on 11/22/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dpex.segundata.Object.BundleDBObject;

import java.util.ArrayList;
import java.util.List;



public class BundleDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "BundleDB";
    Context mycontext ;
    // Contacts table name
    private static final String TABLE_SMS = "bundletbl";

    // Contacts Table Columns names
    //SETTINGS
    private static final String KEY_ID = "id";
 
    private static final String KEY_BUNDLEID = "bundleid";
    private static final String KEY_BUNDLENAME = "bundlename";
    private static final String KEY_PRICE= "price";
    private static final String KEY_DISCOUNT= "discount";
    private static final String KEY_COMPANYID = "companyid";
    private static final String KEY_BUNDLETYPE = "bundletype";
    private static final String KEY_BUNDLESUBTYPE= "bundlesubtype";
    private static final String KEY_DELETE= "deletex";

    public BundleDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mycontext = context ;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_BUNDLEID + " INTEGER,"
                + KEY_BUNDLENAME + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_DISCOUNT+ " TEXT,"
                + KEY_COMPANYID + " TEXT,"
                + KEY_BUNDLETYPE + " TEXT,"
                + KEY_BUNDLESUBTYPE + " TEXT,"
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
    public void addContact(BundleDBObject contact) {
        SQLiteOpenHelper helper = new BundleDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BUNDLEID, contact.getBundleID());
        values.put(KEY_BUNDLENAME, contact.getBundleName());
        values.put(KEY_PRICE, contact.getPrice());
        values.put(KEY_DISCOUNT, contact.getDiscount());
        values.put(KEY_COMPANYID, contact.getCompanyID());
        values.put(KEY_BUNDLETYPE, contact.getBundleType());
        values.put(KEY_BUNDLESUBTYPE, contact.getBundleSubType());
        values.put(KEY_DELETE, contact.getDelete());
        // Inserting Row
        db.insert(TABLE_SMS, null, values);
        db.close(); // Closing database connection



    }

    // Getting single contact
    BundleDBObject getContact(int id) {
        SQLiteOpenHelper helper = new BundleDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getReadableDatabase();
        BundleDBObject contact = null ;
        Cursor cursor = null ;
        try {
            cursor  = db.query(TABLE_SMS, new String[]{KEY_ID,
                            KEY_BUNDLEID,
                            KEY_BUNDLENAME,
                    KEY_PRICE,
                            KEY_DISCOUNT,
                    KEY_COMPANYID,
                            KEY_BUNDLETYPE,
                            KEY_BUNDLESUBTYPE,
                    KEY_DELETE}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            contact = new BundleDBObject(Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));

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
    public List<BundleDBObject> getdataContacts() {

        List<BundleDBObject> contactList = new ArrayList<BundleDBObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SMS +" WHERE "+KEY_BUNDLETYPE+" = 'data'" ;
        SQLiteOpenHelper helper = new BundleDatabaseHandler(mycontext);
        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor cursor = null ;
        try {
            cursor  = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    BundleDBObject contact = new BundleDBObject();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setBundleID(cursor.getInt(1));
                    contact.setBundleName(cursor.getString(2));
                    contact.setPrice(cursor.getString(3));
                    contact.setDiscount(cursor.getString(4));
                    contact.setCompanyID(cursor.getString(5));
                    contact.setBundleType(cursor.getString(6));
                    contact.setBundleSubType(cursor.getString(7));
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



    public List<BundleDBObject> getairtimeContacts() {

        List<BundleDBObject> contactList = new ArrayList<BundleDBObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SMS +" WHERE "+KEY_BUNDLETYPE+" = 'vtu'" ;
        SQLiteOpenHelper helper = new BundleDatabaseHandler(mycontext);
        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor cursor = null ;
        try {
            cursor  = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    BundleDBObject contact = new BundleDBObject();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setBundleID(cursor.getInt(1));
                    contact.setBundleName(cursor.getString(2));
                    contact.setPrice(cursor.getString(3));
                    contact.setDiscount(cursor.getString(4));
                    contact.setCompanyID(cursor.getString(5));
                    contact.setBundleType(cursor.getString(6));
                    contact.setBundleSubType(cursor.getString(7));
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



    public List<BundleDBObject> getpinContacts(String Companyid, String Bundlesubtype) {

        List<BundleDBObject> contactList = new ArrayList<BundleDBObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SMS +" WHERE "+KEY_BUNDLETYPE+" = 'vtu' AND "+KEY_COMPANYID+" = '"+Companyid+"' AND "+KEY_BUNDLESUBTYPE+" = '"+Bundlesubtype+"'" ;
        SQLiteOpenHelper helper = new BundleDatabaseHandler(mycontext);
        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor cursor = null ;
        try {
            cursor  = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    BundleDBObject contact = new BundleDBObject();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setBundleID(cursor.getInt(1));
                    contact.setBundleName(cursor.getString(2));
                    contact.setPrice(cursor.getString(3));
                    contact.setDiscount(cursor.getString(4));
                    contact.setCompanyID(cursor.getString(5));
                    contact.setBundleType(cursor.getString(6));
                    contact.setBundleSubType(cursor.getString(7));
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

    // Getting All Contacts
    public List<BundleDBObject> getAllContacts() {

        List<BundleDBObject> contactList = new ArrayList<BundleDBObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SMS ;
        SQLiteOpenHelper helper = new BundleDatabaseHandler(mycontext);
        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor cursor = null ;
        try {
            cursor  = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    BundleDBObject contact = new BundleDBObject();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setBundleID(cursor.getInt(1));
                    contact.setBundleName(cursor.getString(2));
                    contact.setPrice(cursor.getString(3));
                    contact.setDiscount(cursor.getString(4));
                    contact.setCompanyID(cursor.getString(5));
                    contact.setBundleType(cursor.getString(6));
                    contact.setBundleSubType(cursor.getString(7));
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
    public int updateContact(BundleDBObject contact) {
        SQLiteOpenHelper helper = new BundleDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BUNDLEID, contact.getBundleID());
        values.put(KEY_BUNDLENAME, contact.getBundleName());
        values.put(KEY_PRICE, contact.getPrice());
        values.put(KEY_DISCOUNT, contact.getDiscount());
        values.put(KEY_COMPANYID, contact.getCompanyID());
        values.put(KEY_BUNDLETYPE, contact.getBundleType());
        values.put(KEY_BUNDLESUBTYPE, contact.getBundleSubType());
        values.put(KEY_DELETE, contact.getDelete());
        // updating row
        return db.update(TABLE_SMS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
    }



    public void delete(BundleDBObject contact) {
        SQLiteOpenHelper helper = new BundleDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_SMS, KEY_DELETE + " = ?",
                new String[]{String.valueOf(contact.getDelete())});
        db.close();
    }

    public void deleteuserid(BundleDBObject contact) {
        SQLiteOpenHelper helper = new BundleDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_SMS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }

}