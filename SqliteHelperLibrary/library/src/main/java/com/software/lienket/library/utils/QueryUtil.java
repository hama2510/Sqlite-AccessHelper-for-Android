package com.software.lienket.library.utils;

import android.util.Log;

import com.software.lienket.library.object.Entity;
import com.software.lienket.library.object.EntityColumn;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by KIEN on 4/20/2017.
 */

public class QueryUtil {

    private Entity entity;

    public QueryUtil(Class clazz) {
        entity = EntityUtil.getInstance().getEntity(clazz);
    }

    public static ArrayList<String> createTable(ArrayList<Class> classes) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Class> created = new ArrayList<>();
        while (!classes.isEmpty()) {
            list.addAll(create(classes, created, classes.get(0)));
        }
        return list;
    }

    private static ArrayList<String> create(ArrayList<Class> classes, ArrayList<Class> created, Class clazz) {
        ArrayList<String> list = new ArrayList<>();
        Entity e = EntityUtil.getInstance().getEntity(clazz);
        int length = e.getBelongsTo().size();
        if (length == 0) {
            created.add(clazz);
            classes.remove(clazz);
            list.add(createTableHasNoForeignKey(e));
        } else {
            for (int j = 0; j < length; j++) {
                if (!ClassUtil.isExisted(created, e.getBelongsTo().get(j))) {
                    list.addAll(create(classes, created, e.getBelongsTo().get(j)));
                }
            }
            list.add(createTablesHasForeignKey(e));
            created.add(clazz);
            classes.remove(clazz);
        }
        return list;
    }

    private static String createTableHasNoForeignKey(Entity entity) {
        String sql = "CREATE TABLE " + entity.getTableName() + " (";
        int length = entity.getColumns().size();
        for (int i = 0; i < length; i++) {
            if (entity.getColumns().get(i).getBelongsTo() == null) {
                sql += entity.getColumns().get(i).getName();
                sql += " " + StringUtil.getDatabaseType(entity.getColumns().get(i).getDataType());
                sql += " " + entity.getColumns().get(i).getFieldType();
                if (i < length - 1) {
                    sql += ", ";
                }
                entity.setCreated(true);
            } else {
                return null;
            }
        }
        ArrayList<EntityColumn> ids = entity.getIds();
        int size = ids.size();
        if (size > 0) {
            if (size == 1) {
                sql += ", PRIMARY KEY (" + ids.get(0).getName();
                if (entity.isAi())
                    sql += " AUTOINCREMENT";
                sql += ")";
            } else {
                sql += ", PRIMARY KEY (";
                for (int i = 0; i < size; i++) {
                    sql += ids.get(i).getName();
                    if (i < size - 1)
                        sql += ", ";
                }
                sql += ")";
            }
        }
        sql += ")";
        Log.e("sql", sql);
        return sql;
    }

    private static String createTablesHasForeignKey(Entity entity) {
        String sql = "CREATE TABLE " + entity.getTableName() + " (";
        int length = entity.getColumns().size();
        Class tmp;
        Entity e;
        for (int i = 0; i < length; i++) {
            sql += entity.getColumns().get(i).getName();
            sql += " " + StringUtil.getDatabaseType(entity.getColumns().get(i).getDataType());
            sql += " " + entity.getColumns().get(i).getFieldType();
            tmp = entity.getColumns().get(i).getBelongsTo();
            if (tmp != null) {
                e = EntityUtil.getInstance().getEntity(tmp);
                sql += " REFERENCES " + e.getTableName() + "(" + e.getIds().get(0).getName() + ")"
                        + " ON UPDATE " + entity.getColumns().get(i).getUpdateAction()
                        + " ON DELETE " + entity.getColumns().get(i).getDeleteAction();
            }
            if (i < length - 1) {
                sql += ", ";
            }
        }
        ArrayList<EntityColumn> ids = entity.getIds();
        int size = ids.size();
        if (size > 0) {
            if (size == 1) {
                sql += ", PRIMARY KEY (" + ids.get(0).getName();
                if (entity.isAi())
                    sql += " AUTOINCREMENT";
                sql += ")";
            } else {
                sql += ", PRIMARY KEY (";
                for (int i = 0; i < size; i++) {
                    sql += ids.get(i).getName();
                    if (i < size - 1)
                        sql += ", ";
                }
                sql += ")";
            }
        }
        sql += ")";
        Log.e("sql", sql);
        return sql;
    }

    public String deleteTable() {
        return "DROP TABLE IF EXISTS " + entity.getTableName();
    }

    public String findAllSql() {
        return "SELECT * from " + entity.getTableName();
    }

    public String findSql() {
        String sql = findAllSql() + " WHERE " + whereClause();
        return sql;
    }

    public String whereClause() {
        String sql = "";
        ArrayList<EntityColumn> ids = entity.getIds();
        Iterator<EntityColumn> iterator = ids.iterator();
        while (iterator.hasNext()) {
            sql += iterator.next().getName() + " = ? ";
            if (iterator.hasNext()) {
                sql += " AND ";
            }
        }
        return sql;
    }
}
