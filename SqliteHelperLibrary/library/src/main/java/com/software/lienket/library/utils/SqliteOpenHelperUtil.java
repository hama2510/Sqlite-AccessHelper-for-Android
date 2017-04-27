package com.software.lienket.library.utils;

import android.content.ContentValues;

import com.software.lienket.library.object.EntityColumn;

import java.util.ArrayList;

/**
 * Created by KIEN on 4/27/2017.
 */

public class SqliteOpenHelperUtil<T> {
    private static final SqliteOpenHelperUtil ourInstance = new SqliteOpenHelperUtil();

    public static SqliteOpenHelperUtil getInstance() {
        return ourInstance;
    }

    private SqliteOpenHelperUtil() {
    }

    private void put(ContentValues values, String key, T value) {
        if (value instanceof String)
            values.put(key, (String) value);
        else if (value instanceof Integer || int.class.isInstance(value))
            values.put(key, (Integer) value);
        else if (value instanceof Long || long.class.isInstance(value))
            values.put(key, (Long) value);
        else if (value instanceof Float || float.class.isInstance(value))
            values.put(key, (Float) value);
        else if (value instanceof Double || double.class.isInstance(value))
            values.put(key, (Double) value);
    }

    public ContentValues createContentValues(ArrayList<EntityColumn> columns) {
        ContentValues values = new ContentValues();
        for (EntityColumn item : columns) {
            if (item.getValue() != null) {
                put(values, item.getName(), (T) item.getValue());
            }
        }
        return values;
    }
}
