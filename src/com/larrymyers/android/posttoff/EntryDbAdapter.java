package com.larrymyers.android.posttoff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EntryDbAdapter implements EntryAdapter {
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "unsent_entries";
    private static final int DATABASE_VERSION = 1;
    
    private final Context mCtx;

    public static final String KEY_ID = "_id";
    public static final String KEY_BODY = "body";
    public static final String KEY_COMMENT = "comment";
    
    private static final String SQL_CREATE = "create table " + DATABASE_TABLE + " (" + KEY_ID 
        + " integer primary key autoincrement, " + KEY_BODY + " text not null, " + KEY_COMMENT +  " text);";
    
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
    
    public EntryDbAdapter(Context ctx) {
        this.mCtx = ctx;
        this.mDbHelper = new DatabaseHelper(mCtx);
        this.mDb = mDbHelper.getWritableDatabase();
    }
    
    /* (non-Javadoc)
     * @see com.larrymyers.android.posttoff.EntryAdapter#close()
     */
    public void close() {
        mDbHelper.close();
    }
    
    /* (non-Javadoc)
     * @see com.larrymyers.android.posttoff.EntryAdapter#saveUnsentEntry(com.larrymyers.android.posttoff.Entry)
     */
    public void saveUnsentEntry(Entry entry) {
        ContentValues values = new ContentValues();
        values.put(KEY_BODY, entry.getBody());
        values.put(KEY_COMMENT, entry.getFirstComment());
        
        mDb.insert(DATABASE_TABLE, null, values);
    }
    
    /* (non-Javadoc)
     * @see com.larrymyers.android.posttoff.EntryAdapter#getUnsentEntry()
     */
    public Entry getUnsentEntry() {
        Entry entry = new Entry();
        
        Cursor c = mDb.query(DATABASE_TABLE, new String[] {KEY_BODY, KEY_COMMENT}, null, null, null, null, null);
        
        if (c != null) {
            c.moveToFirst();
            
            entry.setBody(c.getString(c.getColumnIndex(KEY_BODY)));
            entry.setFirstComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
            
            mDb.delete(DATABASE_TABLE, null, null);
        }
        
        return entry;
    }
}
