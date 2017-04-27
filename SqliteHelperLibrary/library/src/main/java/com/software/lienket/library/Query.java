package com.software.lienket.library;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.software.lienket.library.annotation.Column;
import com.software.lienket.library.exception.NotMatchException;
import com.software.lienket.library.utils.QueryUtil;
import com.software.lienket.library.utils.SqliteOpenHelperUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by KIEN on 4/24/2017.
 */

public class Query<T> {
    private static DatabaseConnection connection;
    private Class clazz;
    private QueryUtil queryUtil;
    private SQLiteDatabase db;
    private Cursor cursor;


    private Query(DatabaseConnection connection, Class clazz) {
        this.connection = connection;
        this.clazz = clazz;
        queryUtil = new QueryUtil(clazz);
    }

    public static void init(DatabaseConnection connection) {
        if (connection == null) {
            throw new RuntimeException("Database connection is closed");
        }
        Query.connection = connection;
    }

    public static Query create(Class clazz) {
        if (connection == null) {
            throw new RuntimeException("Query has not been initialized");
        }
        return new Query(connection, clazz);
    }

    private void closeConnection() {
        if (db != null)
            db.close();
        if (cursor != null)
            cursor.close();
    }

    public ArrayList<T> findAll() {
        ArrayList<T> ls = new ArrayList<>();
        db = connection.getReadableDatabase();
        cursor = db.rawQuery(queryUtil.findAllSql(), null);
        if (cursor.moveToFirst()) {
            do {
                T result = (T) SqliteOpenHelperUtil.getInstance().mapper(cursor, clazz);
                if (result != null)
                    ls.add(result);
            } while (cursor.moveToNext());
        }
        closeConnection();
        return ls;
    }

    public T findById(T id) {
        db = connection.getReadableDatabase();
        cursor = db.rawQuery(queryUtil.findSql(), new String[]{String.valueOf(id)});
        T result = null;
        if (cursor.moveToFirst()) {
            result = (T) SqliteOpenHelperUtil.getInstance().mapper(cursor, clazz);
        }
        closeConnection();
        return result;
    }

    public T execute(String sql, String[] param) {
        db = connection.getReadableDatabase();
        cursor = db.rawQuery(queryUtil.findSql(), param);
        T result = null;
        if (cursor.moveToFirst()) {
            result = (T) SqliteOpenHelperUtil.getInstance().mapper(cursor, clazz);
        }
        closeConnection();
        return result;
    }
}
