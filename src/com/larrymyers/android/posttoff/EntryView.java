package com.larrymyers.android.posttoff;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EntryView extends Activity implements OnClickListener {
    
    private static final String LINK_ID = "link";
    private static final String COMMENT_ID = "comment";
    
    private EditText mBody;
    private EditText mComment;
    private TextView mStatus;
    
    private Auth mAuth;
    private AuthAdapter mAuthDb;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mAuthDb = new AuthPrefAdapter(this);
        
        mBody = (EditText) findViewById(R.id.body);
        mComment = (EditText) findViewById(R.id.comment);
        mStatus = (TextView) findViewById(R.id.status);
        
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            String url = extras.getString(Intent.EXTRA_TEXT);
            mBody.setText(url);
        } else {
            mBody.setText(savedInstanceState.getString(LINK_ID));
            mComment.setText(savedInstanceState.getString(COMMENT_ID));
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        mAuth = mAuthDb.getAuth();
        
        if (mAuth == null || mAuth.getUsername() == null) {
            startActivity(new Intent(this, AuthView.class));
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        if (mAuthDb != null) {
            mAuthDb.close();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LINK_ID, mBody.getText().toString());
        outState.putString(COMMENT_ID, mComment.getText().toString());
    }
    
    public void onClick(View v) {
        new SendEventTask().execute();
    }
    
    private Entry getEntry() {
        Entry entry = new Entry();
        
        entry.setBody(mBody.getText().toString());
        entry.setFirstComment(mComment.getText().toString());
        
        return entry;
    }
    
    private void showProgress(String status) {
        mStatus.setText(status);
    }
    
    private class SendEventTask extends AsyncTask<Void, String, Void> {

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            showProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            finish();
        }

        @Override
        protected Void doInBackground(Void... params) {
            publishProgress("Sending to FriendFeed ...");
            
            try {
                EntryService.send(getEntry(), mAuth);
            } catch (Exception e) {
                showProgress("Error sending to FriendFeed.");
                Log.e("Send to FF Error", e.getMessage());
            }
            
            publishProgress("Success!");
            
            return null;
        }
    }
}
