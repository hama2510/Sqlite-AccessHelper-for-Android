package com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Object.EntityColumn;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Object.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by KIEN on 4/20/2017.
 */

public class QueryUtil {

    private Entity entity;

    public QueryUtil(Class clazz) {
        try {
            entity = EntityUtil.getInstance().getEntity(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> createTable(ArrayList<Entity> entities) {
        ArrayList<String> list = new ArrayList<>();
        Iterator iterator = entities.iterator();
        Entity e;
        String tmp;
        ArrayList<Entity> created = new ArrayList<>();
        while (iterator.hasNext()) {
            e = (Entity) iterator.next();
            tmp = createTableHasNoForeignKey(e);
            if (tmp != null) {
                list.add(tmp);
                created.add(e);
                entities.remove(e);
//                iterator.remove();
            }
        }
        iterator = entities.iterator();
        while (iterator.hasNext()) {
            tableHasForeignKey((Entity) iterator.next(), created)
            list.add();
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

//    private static String tableHasForeignKey(Entity entity,  ArrayList<Entity> created) {
//        String sql = "CREATE TABLE " + entity.getTableName() + " (";
//        int length = entity.getColumns().size();
//        Class tmp;
////        Entity e;
//        for (int i = 0; i < length; i++) {
//            sql += entity.getColumns().get(i).getName();
//            sql += " " + StringUtil.getDatabaseType(entity.getColumns().get(i).getDataType());
//            sql += " " + entity.getColumns().get(i).getFieldType();
//            tmp = entity.getColumns().get(i).getBelongsTo();
//            if (tmp != null) {
//
////                e = EntityUtil.getInstance().getEntity(tmp);
//                sql += " REFERENCES " + e.getTableName() + "(" + e.getIds().get(0).getName() + ")"
//                        + " ON UPDATE " + entity.getColumns().get(i).getUpdateAction()
//                        + " ON DELETE " + entity.getColumns().get(i).getDeleteAction();
//            }
//            if (i < length - 1) {
//                sql += ", ";
//            }
//        }
//        ArrayList<EntityColumn> ids = entity.getIds();
//        int size = ids.size();
//        if (size > 0) {
//            if (size == 1) {
//                sql += ", PRIMARY KEY (" + ids.get(0).getName();
//                if (entity.isAi())
//                    sql += " AUTOINCREMENT";
//                sql += ")";
//            } else {
//                sql += ", PRIMARY KEY (";
//                for (int i = 0; i < size; i++) {
//                    sql += ids.get(i).getName();
//                    if (i < size - 1)
//                        sql += ", ";
//                }
//                sql += ")";
//            }
//        }
//        sql += ")";
//        Log.e("sql", sql);
//        return sql;
//    }

    private static String tableHasForeignKey(Entity entity) {
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

    public static ArrayList<String> deleteTable(ArrayList<Entity> entities) {
        ArrayList<String> ls = new ArrayList<>();
        for (Entity item : entities) {
            ls.add("DROP TABLE IF EXISTS " + item.getTableName());
        }
        return ls;
    }

    public String findSql() {
        return "SELECT * from " + entity.getTableName() + "WHERE " + whereClause();
    }

    public String findAllSql() {
        return "SELECT * from " + entity.getTableName();
    }

    public String findByIdSql() {
        String sql = findAllSql() + whereClause();
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
