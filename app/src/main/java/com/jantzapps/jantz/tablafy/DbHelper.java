package com.jantzapps.jantz.tablafy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by jantz on 6/5/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME ="Tablafy";
    private static final int DB_VER = 1;
    public static final String DB_TABLE ="Tabs";
    public static final String DB_COLUM ="TabName";
    public static final String DB_COLUM2 ="TabId";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_TABLE + " (" +
                DB_COLUM + " TEXT, " +
                DB_COLUM2 + " NUMERIC " +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        db.execSQL(query);
        onCreate(db);

    }

    public void insertNewChannel (String channel) {
        SQLiteDatabase  db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUM, channel);
        this.getWritableDatabase().insertOrThrow(DB_TABLE,"",values);
        db.close();
    }

    public void deleteChannel(String channel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUM + " = ?",new String[]{String.valueOf(channel)});
        db.close();
    }

    public void deleteAllChannel() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DB_TABLE);
        db.close();
    }

    public ArrayList<String> getChannelList(){
        ArrayList<String>channelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUM},null,null,null,null,null);
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLUM);
            channelList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return channelList;
    }
}

