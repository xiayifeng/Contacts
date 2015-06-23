package com.xyf.model;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shxiayf on 2015/6/23.
 */
public interface SqliteModel {

    public enum SqliteOperation{
        query,
        modify,
        insert,
        delete
    }

    public void doSql(SQLiteDatabase db,SqliteOperation opt);

}
