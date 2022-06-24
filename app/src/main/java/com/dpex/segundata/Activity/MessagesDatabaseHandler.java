package com.dpex.segundata.Activity;

/**
 * Created by USER on 11/22/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class MessagesDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MessageDB";
    Context mycontext ;
    // Contacts table name
    private static final String TABLE_SMS = "messagetbl";

    // Contacts Table Columns names
    //SETTINGS
    private static final String KEY_ID = "id";

    private static final String KEY_HISTORYID = "historyid";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY= "body";
    private static final String KEY_TIMESENT = "timesent";
    private static final String KEY_STATUS = "stat";


    public MessagesDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mycontext = context ;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_HISTORYID + " INTEGER,"
                + KEY_TITLE + " TEXT,"
                + KEY_BODY + " TEXT,"
                + KEY_TIMESENT + " TEXT,"
                + KEY_STATUS + " TEXT" + ")";
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
    public void addContact(MessagesDBObject contact) {
        SQLiteOpenHelper helper = new MessagesDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HISTORYID, contact.getHistoryID());
        values.put(KEY_TITLE, contact.getTitle());
        values.put(KEY_BODY, contact.getBody());
        values.put(KEY_TIMESENT, contact.getTimeSent());
        values.put(KEY_STATUS, contact.getStatus());

        // Inserting Row
        db.insert(TABLE_SMS, null, values);
        db.close(); // Closing database connection



    }

    // Getting single contact
    MessagesDBObject getContact(int id) {
        SQLiteOpenHelper helper = new MessagesDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getReadableDatabase();
        MessagesDBObject contact = null ;
        Cursor cursor = null ;
        try {
            cursor  = db.query(TABLE_SMS, new String[]{KEY_ID,
                            KEY_HISTORYID,
                            KEY_TITLE,
                    KEY_BODY,
                    KEY_TIMESENT,
                            KEY_STATUS,}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            contact = new MessagesDBObject(Integer.parseInt(cursor.getString(0)),
                  Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

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
    public List<MessagesDBObject> getAllContacts() {

        List<MessagesDBObject> contactList = new ArrayList<MessagesDBObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SMS+" ORDER BY "+KEY_HISTORYID+" DESC " ;
        SQLiteOpenHelper helper = new MessagesDatabaseHandler(mycontext);
        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor cursor = null ;
        try {
            cursor  = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    MessagesDBObject contact = new MessagesDBObject();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setHistoryID(Integer.parseInt(cursor.getString(1)));
                    contact.setTitle(cursor.getString(2));
                    contact.setBody(cursor.getString(3));
                    contact.setTimeSent(cursor.getString(4));
                    contact.setStatus(cursor.getString(5));


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
    public List<MessagesDBObject> getSingleContacts(int id) {

        List<MessagesDBObject> contactList = new ArrayList<MessagesDBObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SMS+" WHERE "+ KEY_ID+" = '"+id+"'" ;
        SQLiteOpenHelper helper = new MessagesDatabaseHandler(mycontext);
        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor cursor = null ;
        try {
            cursor  = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    MessagesDBObject contact = new MessagesDBObject();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setHistoryID(Integer.parseInt(cursor.getString(1)));
                    contact.setTitle(cursor.getString(2));
                    contact.setBody(cursor.getString(3));
                    contact.setTimeSent(cursor.getString(4));
                    contact.setStatus(cursor.getString(5));


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



    public int  getunread() {
        int count = 0 ;
        List<MessagesDBObject> contactList = new ArrayList<MessagesDBObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SMS+" WHERE "+KEY_STATUS+" = '0'" ;
        SQLiteOpenHelper helper = new MessagesDatabaseHandler(mycontext);
        SQLiteDatabase db =helper.getWritableDatabase();
        Cursor cursor = null ;
        try {
            cursor  = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    count++ ;
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            return 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        // return contact list
        return count;
    }




    // Updating single contact
    public int updateContact(MessagesDBObject contact) {
        SQLiteOpenHelper helper = new MessagesDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, contact.getStatus());

        // updating row
        return db.update(TABLE_SMS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
    }




    public void deleteuserid(MessagesDBObject contact) {
        SQLiteOpenHelper helper = new MessagesDatabaseHandler(mycontext);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_SMS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getID())});
        db.close();
    }

}