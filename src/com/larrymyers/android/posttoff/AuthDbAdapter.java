package com.larrymyers.android.posttoff;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AuthDbAdapter implements AuthAdapter {
    
    
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "auth";
    private static final int DATABASE_VERSION = 1;
    
    private final Context mCtx;

    public static final String KEY_ID = "_id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_REMOTEKEY = "remotekey";
    public static final String KEY_OAUTH_TOKEN = "oauth_token";
    
    private static final String SQL_CREATE = "create table " + DATABASE_TABLE + " (" + KEY_ID 
        + " integer primary key autoincrement, "
        + KEY_USERNAME + " text not null, " + KEY_REMOTEKEY +  " text, " + KEY_OAUTH_TOKEN + " text);";
    
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
    
    public AuthDbAdapter(Context ctx) {
        this.mCtx = ctx;
        this.mDbHelper = new DatabaseHelper(mCtx);
        this.mDb = mDbHelper.getWritableDatabase();
    }
    
    /* (non-Javadoc)
     * @see com.larrymyers.android.posttoff.AuthAdapter#close()
     */
    public void close() {
        mDbHelper.close();
    }
    
    /* (non-Javadoc)
     * @see com.larrymyers.android.posttoff.AuthAdapter#createAuth(com.larrymyers.android.posttoff.Auth)
     */
    public void createAuth(Auth auth) {
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, auth.getUsername());
        values.put(KEY_REMOTEKEY, auth.getRemoteKey());
        values.put(KEY_OAUTH_TOKEN, auth.getOauthToken());
        
        mDb.insert(DATABASE_TABLE, null, values);
    }
    
    /* (non-Javadoc)
     * @see com.larrymyers.android.posttoff.AuthAdapter#updateAuth(com.larrymyers.android.posttoff.Auth)
     */
    public void updateAuth(Auth auth) {
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, auth.getUsername());
        values.put(KEY_REMOTEKEY, auth.getRemoteKey());
        values.put(KEY_OAUTH_TOKEN, auth.getOauthToken());
        
        mDb.update(DATABASE_TABLE, values, KEY_USERNAME + "=" + auth.getUsername(), null);
    }
    
    /* (non-Javadoc)
     * @see com.larrymyers.android.posttoff.AuthAdapter#getAuth()
     */
    public Auth getAuth() {
        String[] columns = {KEY_USERNAME, KEY_REMOTEKEY, KEY_OAUTH_TOKEN};
        Cursor cursor = mDb.query(DATABASE_TABLE, columns, null, null, null, null, null);
        
        if (cursor != null) {
            cursor.moveToFirst();
            
            if (cursor.getColumnCount() == 0) {
                return null;
            }
            
            String username = cursor.getString(cursor.getColumnIndex(KEY_USERNAME));
            String remoteKey = cursor.getString(cursor.getColumnIndex(KEY_REMOTEKEY));
            String oauthToken = cursor.getString(cursor.getColumnIndex(KEY_OAUTH_TOKEN));
            
            cursor.close();
            
            Auth auth = new Auth();
            auth.setUsername(username);
            auth.setRemoteKey(remoteKey);
            auth.setOauthToken(oauthToken);
            
            return auth;
        }
        
        return null;
    }
}
