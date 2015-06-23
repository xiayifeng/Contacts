package com.xyf.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.xyf.model.ColumeModel;

/**
 * Created by shxiayf on 2015/6/23.
 */
public class DBOpenHelper extends SQLiteOpenHelper{

    public DBOpenHelper(Context context,String name,int version){
        super(context,name,null,version);
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + ColumeModel.TB_NAME + " ("
                + ColumeModel._ID + " PRIMARY KEY NOT NULL,"
                + ColumeModel.COL_NAME + " TEXT,"
                + ColumeModel.COL_ADDRESS + " TEXT,"
                + ColumeModel.COL_EMAIL + " TEXT,"
                + ColumeModel.COL_PHONE + " TEXT NOT NULL,"
                + ColumeModel.COL_MSN + " TEXT,"
                + ColumeModel.COL_QQ + " TEXT,"
                + ColumeModel.COL_COMP + "TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE " + ColumeModel.TB_NAME + " IF EXISTS";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
