package com.example.morya.primedroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Morya on 25/04/2017.
 */

public class DataBasePrime extends SQLiteOpenHelper {
    public static final String TAG = "database";
    public static final String DATABASE_PRIMES="PrimeNumbers";
    public static final String TABLE_PRIMES="PrimeNumbersTable";
    public static final String COL_1="RANK";
    public static final String COL_2="PRIMENUMBER";
    public static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_PRIMES + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT" + ")";
    public static final String DELETE_TABLES ="DROP TABLE "+TABLE_PRIMES+";";

    public DataBasePrime(Context context) {
        super(context, DATABASE_PRIMES, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        Log.d(TAG,TABLE_PRIMES +" has been created.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLES);
        onCreate(db);
    }
    public boolean InsertData(String primenumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,primenumber);
        Long result = db.insert(TABLE_PRIMES,null,contentValues);
        if(result == -1){
            return true;
        }
        else{
            return false;
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res  =db.rawQuery("select * from"+TABLE_PRIMES,null);
        return res;
    }
    public boolean UpdateData(String rank,String primenumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,primenumber);
        int u = db.update(TABLE_PRIMES,contentValues,"RANK=?",new String[] {rank});
        if(u ==0){
            db.insertWithOnConflict(TABLE_PRIMES,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        }
        return true;
    }
}