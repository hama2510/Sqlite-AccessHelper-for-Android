package com.software.lienket.sqlitehelperlibrary.HelperLibrary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Column;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Excecption.NotMatchException;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils.EntityUtil;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils.QueryUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by KIEN on 4/24/2017.
 */

public class Query<T> {
    private static DatabaseConnection connection;
    private Class clazz;
    private QueryUtil queryUtil;


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

    public ArrayList<T> findAll() {
        ArrayList<T> ls = new ArrayList<>();
        SQLiteDatabase db = connection.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryUtil.findAllSql(), null);
        if (cursor.moveToFirst()) {
            do {
                T result = mapper(cursor, clazz);
                if (result != null)
                    ls.add(result);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ls;
    }

    public T findById(T id) {
        SQLiteDatabase db = connection.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryUtil.findSql(), new String[]{String.valueOf(id)});
        T result = null;
        if (cursor.moveToFirst()) {
            result = mapper(cursor, clazz);
        }
        cursor.close();
        db.close();
        return result;
    }


    private T mapper(Cursor cursor, Class clazz) {
        T result = null;
        try {
            result = (T) clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            Field f;
            int length = fields.length;
            int type;
            int index;
            for (int i = 0; i < length; i++) {
                f = fields[i];
                f.setAccessible(true);
                index = cursor.getColumnIndex(f.getAnnotation(Column.class).name());
                type = cursor.getType(index);
                if (type == Cursor.FIELD_TYPE_INTEGER) {
                    if (f.getType().equals(int.class) || f.getType().equals(Integer.class))
                        f.set(result, cursor.getInt(index));
                    else if (f.getType().equals(long.class) || f.getType().equals(Long.class))
                        f.set(result, cursor.getLong(index));
                    else
                        throw new NotMatchException(f.getName(), f.getType().getName(),
                                f.getAnnotation(Column.class).name(), "INTEGER");
                } else if (type == Cursor.FIELD_TYPE_FLOAT) {
                    if (f.getType().equals(float.class) || f.getType().equals(Float.class))
                        f.set(result, cursor.getInt(index));
                    else if (f.getType().equals(double.class) || f.getType().equals(Double.class))
                        f.set(result, cursor.getLong(index));
                    else
                        throw new NotMatchException(f.getName(), f.getType().getName(),
                                f.getAnnotation(Column.class).name(), "REAL");
                } else if (type == Cursor.FIELD_TYPE_STRING) {
                    if (f.getType().equals(String.class))
                        f.set(result, cursor.getString(index));
                    else
                        throw new NotMatchException(f.getName(), f.getType().getName(),
                                f.getAnnotation(Column.class).name(), "STRING");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
