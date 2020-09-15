package com.example.justloginregistertest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBOpenHelper_report extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DBOpenHelper_report(Context context) {
        super(context,"db_report",null,1);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS report(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "time TEXT," +
                "temperature TEXT," +
                "oneself TEXT," +
                "parents TEXT," +
                "location TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS report");
        onCreate(db);
    }

    public void add(String name,String time,String temperature,String oneself,String parents,String location){
        db.execSQL("INSERT INTO report (name,time,temperature,oneself,parents,location) VALUES(?,?,?,?,?,?)",new Object[]{name,time,temperature,oneself,parents,location});
    }

    public ArrayList<Time> getAllData(){

        ArrayList<Time> list = new ArrayList<Time>();
        Cursor cursor = db.query("report",null,null,null,null,null,"time DESC");
        while(cursor.moveToNext()){
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            list.add(new Time(time,name));
        }
        return list;
    }
}
