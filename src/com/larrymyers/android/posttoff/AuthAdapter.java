package com.larrymyers.android.posttoff;

public interface AuthAdapter {

    public abstract void close();

    public abstract void createAuth(Auth auth);

    public abstract void updateAuth(Auth auth);

    public abstract Auth getAuth();

}