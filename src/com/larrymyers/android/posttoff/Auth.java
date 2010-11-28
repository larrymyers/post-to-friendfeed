package com.larrymyers.android.posttoff;

public class Auth {
    private String username;
    private String remoteKey;
    private String oauthToken;
    
    public Auth() {
        this.username = "";
        this.remoteKey = "";
        this.oauthToken = "";
    }
    
    public String getBasicAuth() {
        return username + ":" + remoteKey;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getRemoteKey() {
        return remoteKey;
    }
    
    public void setRemoteKey(String remoteKey) {
        this.remoteKey = remoteKey;
    }
    
    public String getOauthToken() {
        return oauthToken;
    }
    
    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }
}
