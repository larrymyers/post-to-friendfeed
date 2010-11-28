package com.larrymyers.android.posttoff;

public interface EntryAdapter {

    public abstract void close();

    public abstract void saveUnsentEntry(Entry entry);

    public abstract Entry getUnsentEntry();

}