package com.software.lienket.sqlitehelperlibrary.HelperLibrary.Object;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Column;

import java.util.ArrayList;

/**
 * Created by KIEN on 4/23/2017.
 */

public class Entity {
    private String tableName;
    private boolean ai;
    private ArrayList<EntityColumn> columns;
    private ArrayList<Class> hasManyTables;
    private Class clazz;
    private boolean created = false;

    public Entity() {
        columns = new ArrayList<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isAi() {
        return ai;
    }

    public void setAi(boolean ai) {
        this.ai = ai;
    }

    public ArrayList<EntityColumn> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<EntityColumn> columns) {
        this.columns = columns;
    }

    public ArrayList<Class> getHasManyTables() {
        return hasManyTables;
    }

    public void setHasManyTables(ArrayList<Class> hasManyTables) {
        this.hasManyTables = hasManyTables;
    }

    public ArrayList<EntityColumn> getIds() {
        ArrayList<EntityColumn> ids = new ArrayList<>();
        for (EntityColumn item : columns) {
            if (item.isId())
                ids.add(item);
        }
        return ids;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public ArrayList<Class> getBelongsTo() {
        ArrayList<Class> classes = new ArrayList<>();
        for (EntityColumn item : columns) {
            if (item.getBelongsTo() != null)
                classes.add(item.getBelongsTo());
        }
        return classes;
    }
}
