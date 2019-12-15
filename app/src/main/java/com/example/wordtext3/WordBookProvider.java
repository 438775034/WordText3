package com.example.wordtext3;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class WordBookProvider extends ContentProvider {

    public static final int WORD_DIR=0;
    public static final int WORD_ITEM=1;
    public static final String AUTHOEITY="com.example.wordtext3.provider";
    private static UriMatcher uriMatcher;
    private MyDatabaseHelper databaseHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHOEITY,"wordbook",WORD_DIR);
        uriMatcher.addURI(AUTHOEITY,"wordbook/#",WORD_ITEM);
    }

    public WordBookProvider() {
    }





    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)){
            case WORD_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.wordtext3.provider.wordbook";
            case WORD_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.wordtext3.provider.wordbook";
        }
                return null;
    }



    @Override
    public boolean onCreate() {
        databaseHelper = new MyDatabaseHelper(getContext(),"WordBook.db",null,1);
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    //添加数据
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case WORD_DIR:
            case WORD_ITEM:
                long  newWordID = db.insert("WordBook",null,values);
                uriReturn = Uri.parse("content://"+AUTHOEITY+"/wordbook/"+newWordID);
                break;
            default:
                break;

        }
        return uriReturn;
    }

    //查询数据
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case WORD_DIR:
                cursor = db.query("WordBook",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case WORD_ITEM:
                String wordID = uri.getPathSegments().get(1);
                cursor = db .query("WordBook",projection,"id=?",new String[]{wordID},null,null,sortOrder);
                break;
            default :
                break;

        }
        return cursor;

    }


    //更新数据
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)){
            case WORD_DIR:
                updatedRows = db.update("WordBook",values,selection,selectionArgs);
                break;
            case WORD_ITEM:
                String wordID = uri.getPathSegments().get(1);
                updatedRows = db.update("WordBook",values,"id = ?",new String[]{wordID});
                break;
            default :
                break;

        }
        return updatedRows;
    }

    //删除数据
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)){
            case WORD_DIR:
                deletedRows = db.delete("WordBook",selection,selectionArgs);
                break;
            case WORD_ITEM:
                String wordID = uri.getPathSegments().get(1);
                deletedRows = db.delete("WordBook","id=?",new String[]{wordID});
                break;
            default :
                break;

        }
        return deletedRows;
    }
}

