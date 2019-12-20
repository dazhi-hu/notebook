package com.example.notebook;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DictProvider extends ContentProvider{

    private static UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
    private static final int WORDS=1;
    private static final int WORD=2;
    private MyDatabaseHelper myDatabaseHelper;
    static{
        matcher.addURI(Words.AUTHORITY, "words", WORDS);
        matcher.addURI(Words.AUTHORITY, "word/#", WORD);
    }

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        // TODO Auto-generated method stub
        SQLiteDatabase database=myDatabaseHelper.getReadableDatabase();
        int num=0;
        switch(matcher.match(arg0)){
            case WORDS:
                num=database.delete("dict", arg1, arg2);
                break;
            case WORD:
                long id=ContentUris.parseId(arg0);
                String whereClause=Words.Word._ID+"="+id;
                if(whereClause!=null&&!whereClause.equals("")){
                    whereClause=whereClause+"and"+arg1;
                }
                num=database.delete("dict", whereClause, arg2);
                break;
            default:
                throw new IllegalArgumentException("未知Uri"+arg0);
        }
        getContext().getContentResolver().notifyChange(arg0, null);
        return num;
    }

    @Override
    public String getType(Uri arg0) {
        // TODO Auto-generated method stub
        switch(matcher.match(arg0)){
            case WORDS:
                return "vnd.android.cursor.dir/com.example.notebook";
            case WORD:
                return "vnd.android.cursor.item/com.example.notebook";
            default:
                throw new IllegalArgumentException("位置Uri:"+arg0);
        }
    }

    @Override
    public Uri insert(Uri arg0, ContentValues arg1) {
        // TODO Auto-generated method stub
        SQLiteDatabase database=myDatabaseHelper.getReadableDatabase();
        switch(matcher.match(arg0)){
            case WORDS:
                long rowId=database.insert("dict", Words.Word._ID, arg1);
                if(rowId>0){
                    Uri wordUri=ContentUris.withAppendedId(arg0, rowId);
                    getContext().getContentResolver().notifyChange(wordUri, null);
                    return wordUri;
                }
                break;
            default:
                throw new IllegalArgumentException("未知Uri:"+arg0);
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        myDatabaseHelper=new MyDatabaseHelper(this.getContext(), "myDict.db3", 1);
        return true;
    }

    @Override
    public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
                        String arg4) {
        // TODO Auto-generated method stub
        SQLiteDatabase database=myDatabaseHelper.getReadableDatabase();
        switch(matcher.match(arg0)){
            case WORDS:
                return database.query("dict", arg1, arg2, arg3, null, null, arg4);
            case WORD:
                long id=ContentUris.parseId(arg0);
                String whereCaluse=Words.Word._ID+"="+id;
                //如果原来的where子句在，拼接where子句
                if(arg2!=null&&!arg2.equals("")){
                    whereCaluse=whereCaluse+"and"+arg2;
                }
                return database.query("dict", arg1, whereCaluse, arg3, null, null, arg4);
            default:
                throw new IllegalArgumentException("未知Uri:"+arg0);
        }

    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        // TODO Auto-generated method stub
        SQLiteDatabase database=myDatabaseHelper.getReadableDatabase();
        int num=0;
        switch(matcher.match(arg0)){
            case WORDS:
                num=database.update("dict", arg1, arg2, arg3);
                break;
            case WORD:
                long id=ContentUris.parseId(arg0);
                String whereClause=Words.Word._ID+"="+id;
                if(arg2!=null&&!arg2.equals("")){
                    whereClause=whereClause+"and"+arg2;
                }
                num=database.update("dict", arg1, arg2, arg3);
                break;
            default:
                throw new IllegalArgumentException("未知Uri:"+arg0);
        }
        getContext().getContentResolver().notifyChange(arg0, null);
        return num;
    }



}
