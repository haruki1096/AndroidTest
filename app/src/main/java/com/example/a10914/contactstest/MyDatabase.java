package com.example.a10914.contactstest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 10914 on 2020/6/26.
 */

public class MyDatabase  extends SQLiteOpenHelper {
    public static final String  contacts ="create table contacts("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"phone integer,"
            +"sex text)";

    private Context mContext;

    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext=context;
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_CONTACTS);

    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }

}
