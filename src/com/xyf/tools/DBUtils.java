package com.xyf.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.xyf.model.SqliteModel;

/**
 * Created by shxiayf on 2015/6/23.
 */
public class DBUtils {

    private DBOpenHelper dbHelper;
    private static final String dbName = "contacts.db";
    private static final int dbVersion = 1;

    public DBUtils(Context mContext) {
        synchronized (DBUtils.class) {
            dbHelper = new DBOpenHelper(mContext, dbName, dbVersion);
        }
    }

    public void doExecSql(SqliteModel bean,SqliteModel.SqliteOperation opt) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        bean.doSql(db,opt);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    public void doQuery(SqliteModel bean) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        bean.doSql(db, SqliteModel.SqliteOperation.query);
        db.close();

    }

}
