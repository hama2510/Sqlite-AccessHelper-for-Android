package com.software.lienket.library;

import android.database.sqlite.SQLiteDatabase;

import com.software.lienket.library.exception.NoAccessibleException;
import com.software.lienket.library.object.Entity;
import com.software.lienket.library.utils.EntityUtil;
import com.software.lienket.library.utils.QueryUtil;
import com.software.lienket.library.utils.SqliteOpenHelperUtil;

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



    public void insert(T obj) {
        SQLiteDatabase db = connection.getWritableDatabase();
        Entity entity = EntityUtil.getInstance().getEntity(obj);
        long id = db.insert(entity.getTableName(), null, SqliteOpenHelperUtil.getInstance().createContentValues(entity.getColumns()));
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
        db.update(entity.getTableName(), SqliteOpenHelperUtil.getInstance().createContentValues(entity.getColumns()),
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
