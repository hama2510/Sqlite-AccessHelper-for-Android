package com.software.lienket.sqlitehelperlibrary.HelperLibrary;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Excecption.NoAccessibleException;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Object.EntityColumn;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Object.Entity;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils.EntityUtil;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils.QueryUtil;

import java.util.ArrayList;

/**
 * Created by KIEN on 4/22/2017.
 */

public class DataAccessObject<T> {
    private static final DataAccessObject instance = new DataAccessObject();
    private DatabaseConnection connection;

    public static DataAccessObject getInstance() {
        return instance;
    }

    private DataAccessObject() {
        connection = DatabaseConnection.getConnection();
        Query.init(connection);
    }

    private ContentValues createContentValues(ArrayList<EntityColumn> columns) {
        ContentValues values = new ContentValues();
        for (EntityColumn item : columns) {
            if (item.getValue() != null) {
                if (item.getValue() instanceof String)
                    values.put(item.getName(), (String) item.getValue());
                else if (item.getValue() instanceof Integer)
                    values.put(item.getName(), (Integer) item.getValue());
                else if (item.getValue() instanceof Long)
                    values.put(item.getName(), (Long) item.getValue());
                else if (item.getValue() instanceof Float)
                    values.put(item.getName(), (Float) item.getValue());
                else if (item.getValue() instanceof Double)
                    values.put(item.getName(), (Double) item.getValue());
            }
        }
        return values;
    }

    public void insert(T obj) {
        SQLiteDatabase db = connection.getWritableDatabase();
        Entity entity = EntityUtil.getInstance().getEntity(obj);
        long id = db.insert(entity.getTableName(), null, createContentValues(entity.getColumns()));
        try {
            EntityUtil.getInstance().setId(obj, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NoAccessibleException();
        }
        db.close();
    }

    public void update(T obj) {
        SQLiteDatabase db = connection.getWritableDatabase();
        Entity entity = EntityUtil.getInstance().getEntity(obj);
        String[] tmp = new String[entity.getIds().size()];
        int i = 0;
        for (Object item : entity.getIds()) {
            tmp[i] = String.valueOf(item);
            i++;
        }
        db.update(entity.getTableName(), createContentValues(entity.getColumns()),
                new QueryUtil(obj.getClass()).whereClause(),
                tmp);
        db.close();
    }

    public void delete(T obj) {
        SQLiteDatabase db = connection.getWritableDatabase();
        Entity entity = EntityUtil.getInstance().getEntity(obj);
        String[] tmp = new String[entity.getIds().size()];
        int i = 0;
        for (Object item : entity.getIds()) {
            tmp[i] = String.valueOf(item);
            i++;
        }
        db.delete(entity.getTableName(), new QueryUtil(obj.getClass()).whereClause(), tmp);
        db.close();
    }

    public Query createQuery(Class clazz) {
        return Query.create(clazz);
    }
}
