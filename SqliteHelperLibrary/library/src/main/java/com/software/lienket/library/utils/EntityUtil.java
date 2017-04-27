package com.software.lienket.library.utils;

import com.software.lienket.library.annotation.BelongsTo;
import com.software.lienket.library.annotation.Column;
import com.software.lienket.library.annotation.HasMany;
import com.software.lienket.library.annotation.Id;
import com.software.lienket.library.annotation.Table;
import com.software.lienket.library.exception.NoAccessibleException;
import com.software.lienket.library.exception.NotEntityException;
import com.software.lienket.library.object.Entity;
import com.software.lienket.library.object.EntityColumn;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by KIEN on 4/21/2017.
 */

public class EntityUtil<T> {

    private static EntityUtil instance = new EntityUtil();

    private EntityUtil() {
    }

    public static EntityUtil getInstance() {
        return instance;
    }

    public boolean isEntity(Class clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            if (clazz.isAnnotationPresent(com.software.lienket.library.annotation.Entity.class)) {
                return true;
            }
        }
        return false;
    }

    private Entity getTableInfo(Class clazz) {
        Entity entity = new Entity();
        if (clazz.isAnnotationPresent(HasMany.class)) {
            ArrayList<Class> arr = new ArrayList<>();
            Class[] tmp = ((HasMany) clazz.getAnnotation(HasMany.class)).entities();
            for (Class item : tmp) {
                arr.add(item);
            }
            entity.setHasManyTables(arr);
        }
        entity.setTableName(((Table) clazz.getAnnotation(Table.class)).name());
        entity.setAi(isAI(clazz));
        return entity;
    }

    public Entity getEntity(Class clazz) {
        if (isEntity(clazz)) {
            Entity entity = getTableInfo(clazz);
            entity.setColumns(getColumnsInfo(clazz));
            entity.setClazz(clazz);
            return entity;
        } else {
            throw new NotEntityException(clazz.getName());
        }
    }

    public Entity getEntity(T obj) {
        Class clazz = obj.getClass();
        if (isEntity(clazz)) {
            Entity entity = getTableInfo(obj.getClass());
            entity.setColumns(getColumnsInfo(obj));
            entity.setClazz(obj.getClass());
            return entity;
        } else {
            throw new NotEntityException(clazz.getName());
        }
    }

    private EntityColumn getColumn(T obj, Field f) {
        if (f.isAnnotationPresent(Column.class)) {
            EntityColumn column = new EntityColumn();
            column.setName(f.getAnnotation(Column.class).name());
            column.setDataType(f.getType());
            column.setFieldType(f.getAnnotation(Column.class).fieldType());
            if (f.isAnnotationPresent(Id.class)) {
                column.setId(true);
            }
            if (f.isAnnotationPresent(BelongsTo.class)) {
                column.setBelongsTo(f.getAnnotation(BelongsTo.class).table());
                column.setUpdateAction(f.getAnnotation(BelongsTo.class).updateAction());
                column.setDeleteAction(f.getAnnotation(BelongsTo.class).deleteAction());
            }
            if (obj != null) {
                f.setAccessible(true);
                try {
                    column.setValue(f.get(obj));
                } catch (Exception e) {
                    throw new NoAccessibleException();
                }
            }
            return column;
        }
        return null;
    }

    private ArrayList<EntityColumn> getColumnsInfo(Class clazz) {
        ArrayList<EntityColumn> columns = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        EntityColumn column = null;
        for (Field f : fields) {
            column = getColumn(null, f);
            if (column != null)
                columns.add(column);
            else
                throw new NotEntityException(clazz.getName() + ". Missing column annotation");
        }
        return columns;
    }

    private ArrayList<EntityColumn> getColumnsInfo(T obj) {
        EntityColumn column = null;
        ArrayList<EntityColumn> columns = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            column = getColumn(obj, f);
            if (column != null)
                columns.add(column);
            else
                throw new NotEntityException(obj.getClass().getName());
        }
        return columns;
    }

    private boolean isAI(Class clazz) {
        boolean ai = false;
        int count = 0;
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Id.class)) {
                count++;
                ai = f.getAnnotation(Id.class).AI();
            }
        }
        if (count != 1)
            ai = false;
        return ai;
    }

    public String getIdColumn(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Id.class)) {
                return f.getName();
            }
        }
        return null;
    }

    public void setId(T obj, Long id) throws Exception {
        if (isAI(obj.getClass())) {
            String column = getIdColumn(obj.getClass());
            if (column != null) {
                Field f = obj.getClass().getDeclaredField(column);
                f.setAccessible(true);
                if (f.getType().isInstance(new Integer(1))) {
                    f.set(obj, id.intValue());
                } else
                    f.set(obj, id);
            }
        }
    }
}
