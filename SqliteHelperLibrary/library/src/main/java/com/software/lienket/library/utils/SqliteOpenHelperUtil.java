package com.software.lienket.library.utils;

import android.content.ContentValues;
import android.database.Cursor;

import com.software.lienket.library.annotation.Column;
import com.software.lienket.library.exception.NoAccessibleException;
import com.software.lienket.library.exception.NotMatchException;
import com.software.lienket.library.object.EntityColumn;

import java.lang.reflect.Field;
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

    public T mapper(Cursor cursor, Class clazz) {
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
            throw new NoAccessibleException();
        }
        return result;
    }
}
