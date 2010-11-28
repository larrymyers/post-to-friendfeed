package com.larrymyers.android.posttoff;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AuthView extends Activity implements OnClickListener {

    private AuthAdapter mAuthDb;
    
    private EditText mUsername;
    private EditText mRemoteKey;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);
        
        mAuthDb = new AuthPrefAdapter(this);
        
        Auth auth = mAuthDb.getAuth();
        
        mUsername = (EditText) findViewById(R.id.username);
        mRemoteKey = (EditText) findViewById(R.id.remotekey);
        
        if (savedInstanceState != null) {
            
        } else if (auth != null) {
            mUsername.setText(auth.getUsername());
            mRemoteKey.setText(auth.getRemoteKey());
        }
        
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        String username = mUsername.getText().toString();
        String remoteKey = mRemoteKey.getText().toString();
        
        Auth auth = new Auth();
        auth.setUsername(username);
        auth.setRemoteKey(remoteKey);
        
        mAuthDb.createAuth(auth);
        
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        if (mAuthDb != null) {
            mAuthDb.close();
        }
    }
}
