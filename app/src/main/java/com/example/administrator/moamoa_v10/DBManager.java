package com.example.administrator.moamoa_v10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.moamoa_v10.Config.Keyword;

/**
 * Created by Administrator on 2016-12-23.
 */

public class DBManager {

    private final String DB_NAME    = "keyword_db";
    private final int    DB_VESION  = 1;

    private Context         m_Context       = null;
    private DBOpenHelper    m_Helper        = null;
    private SQLiteDatabase  m_DbController  = null;

    public DBManager(Context _Context)
    {
        this.m_Context = _Context;
        this.m_Helper  = new DBOpenHelper(m_Context, DB_NAME, null, DB_VESION);
    }

    public void Open()
    {
        this.m_DbController = m_Helper.getWritableDatabase();
    }

    public void Close()
    {
        this.m_DbController.close();
    }

    public void InsertKeyword(String _sql, Keyword _keyword)
    {
        String[] sqlData = _keyword.getKeywordArray();
        this.m_DbController.execSQL(_sql, sqlData);
    }

    public void DeleteKeyword(String _sql, Keyword _keyword)
    {
        String[] sqlData = { _keyword.getM_keyword() };
        this.m_DbController.execSQL(_sql, sqlData);
    }

    public void SelectKeyword()
    {

    }

    public void SelectAllKeyword()
    {

    }












    private class DBOpenHelper extends SQLiteOpenHelper{

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    };
}
