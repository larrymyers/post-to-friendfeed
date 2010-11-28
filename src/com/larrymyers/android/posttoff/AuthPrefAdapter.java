package com.larrymyers.android.posttoff;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AuthPrefAdapter implements AuthAdapter {
    
    private Context mCtx;
    
    public AuthPrefAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    
    public void close() {
        // nothing to do here
    }

    public void createAuth(Auth auth) {
        SharedPreferences prefs = mCtx.getSharedPreferences("prefs", 0);
        Editor editor = prefs.edit();
        
        editor.putString("username", auth.getUsername());
        editor.putString("remoteKey", auth.getRemoteKey());
        
        editor.commit();
    }

    public Auth getAuth() {
        SharedPreferences prefs = mCtx.getSharedPreferences("prefs", 0);
        
        Auth auth = new Auth();
        
        auth.setUsername(prefs.getString("username", null));
        auth.setRemoteKey(prefs.getString("remoteKey", null));
        
        return auth;
    }

    public void updateAuth(Auth auth) {
        createAuth(auth);
    }
}
