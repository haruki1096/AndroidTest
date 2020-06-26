package com.example.a10914.contactstest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    public static final int Contacts_DIR=0;
    public static final int Contacts_ITEM=1;

    public static final String AUTHORITY="com.example.a10914.contactstest.provider";
    private static UriMatcher uriMatcher;
    private MyDatabase dbHelper;
    static{
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",Contacts_DIR);
        uriMatcher.addURI(AUTHORITY,"book/#",Contacts_ITEM);

    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int deletedRows=0;
        switch (uriMatcher.match(uri)){
            case Contacts_DIR:
                deletedRows=db.delete("Contacts",selection,selectionArgs);
                break;
            case Contacts_ITEM:
                String ContactsId=uri.getPathSegments().get(1);
                deletedRows=db.delete("Contacts","id=?",new String[]{ContactsId});
                break;

        }
        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)){
            case Contacts_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.a10914.contactstest.provider.book";
            case Contacts_ITEM:
                return "vnd.android.cursor.dir/vnd.com.example.a10914.contactstest.provider.book";

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Uri uriReturn =null;
        switch(uriMatcher.match(uri)){
            case Contacts_DIR:
            case Contacts_ITEM:
                long newContactId=db.insert("Contacts",null,values);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/Contacts/"+newContactId);
                break;

            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        dbHelper=new MyDatabase(getContext(),"phonebook.db",null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=null;
        switch(uriMatcher.match(uri)){
            case Contacts_DIR:
                cursor=db.query("Contacts",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case Contacts_ITEM:
                String ContactsId=uri.getPathSegments().get(1);
                cursor=db.query("Contacts",projection,"id=?",new String[]{ContactsId},null,null,sortOrder);
                break;

            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int updatedRows=0;
        switch(uriMatcher.match(uri)){
            case Contacts_DIR:
                updatedRows=db.update("Contact",values,selection,selectionArgs);
                break;
            case Contacts_ITEM:
                String ContactsId=uri.getPathSegments().get(1);
                updatedRows=db.update("Contact",values,"id=?",new String[] {ContactsId});
                break;

            default:
                break;
        }
        return updatedRows;
    }
}
