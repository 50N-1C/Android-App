package com.example.task;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "user";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FNAME = "first_name";
    private static final String COLUMN_LNAME = "last_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_FNAME + " TEXT,"
                + COLUMN_LNAME + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        //onCreate(db);
    }

    public boolean insertUser(String username, String email, String password, String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_FNAME, firstName);
        values.put(COLUMN_LNAME, lastName);

        long result = db.insert(TABLE_USER, null, values);
        db.close();

        return result != -1;
    }

    public boolean validateUser(String usernameEmail, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE (" + COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?) AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{usernameEmail, usernameEmail, password});

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValid;
    }

    @SuppressLint("Range")
    public User getUser(String usernameEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + "=? OR " + COLUMN_EMAIL + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{usernameEmail, usernameEmail});

        if (cursor.moveToFirst()) {
            User user = new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            user.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FNAME)));
            user.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LNAME)));
            cursor.close();
            db.close();
            return user;
        }

        cursor.close();
        db.close();
        return null;
    }
}
