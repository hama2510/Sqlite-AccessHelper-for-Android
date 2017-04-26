package com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.BelongsTo;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Column;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.HasMany;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Id;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Table;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Excecption.NoAccessibleException;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Excecption.NotEntityException;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Object.Entity;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Object.EntityColumn;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Object.GraphEntity;

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
            if (clazz.isAnnotationPresent(com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Entity.class)) {
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

    public ArrayList<GraphEntity> getEntities(ArrayList<Class> classes) {
        ArrayList<GraphEntity> entities = new ArrayList<>();
        int size = classes.size();
        Class clazz;
        for (int i = 0; i < size; i++) {
            clazz = classes.get(i);
            if (isEntity(clazz)) {
                Entity entity = getTableInfo(clazz);
                ArrayList<EntityColumn> columns = new ArrayList<>();
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    EntityColumn column = new EntityColumn();
                    if (f.isAnnotationPresent(Column.class)) {
                        column.setName(f.getAnnotation(Column.class).name());
                        column.setDataType(f.getType());
                        column.setFieldType(f.getAnnotation(Column.class).fieldType());
                        if (f.isAnnotationPresent(Id.class)) {
                            column.setId(true);
                        }
                        if (f.isAnnotationPresent(BelongsTo.class)) {
                            column.setBelongsTo(f.getAnnotation(BelongsTo.class).entity());
                            column.setUpdateAction(f.getAnnotation(BelongsTo.class).updateAction());
                            column.setDeleteAction(f.getAnnotation(BelongsTo.class).deleteAction());
                        }
                    }
                    columns.add(column);
                }
                entity.setColumns(columns);
                entity.setClazz(clazz);
//                entities.add(entity);
                classes.remove(clazz);
            } else {
                throw new NotEntityException(clazz.getName());
            }
        }
        return entities;
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
        EntityColumn column = new EntityColumn();
        if (f.isAnnotationPresent(Column.class)) {
            column.setName(f.getAnnotation(Column.class).name());
            column.setDataType(f.getType());
            column.setFieldType(f.getAnnotation(Column.class).fieldType());
            if (f.isAnnotationPresent(Id.class)) {
                column.setId(true);
            }
            if (f.isAnnotationPresent(BelongsTo.class)) {
                column.setBelongsTo(f.getAnnotation(BelongsTo.class).entity());
                column.setUpdateAction(f.getAnnotation(BelongsTo.class).updateAction());
                column.setDeleteAction(f.getAnnotation(BelongsTo.class).deleteAction());
            }
            if (obj != null) {
                f.setAccessible(true);
                try {
                    column.setValue(f.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new NoAccessibleException();
                }
            }
        }
        return column;
    }

    private ArrayList<EntityColumn> getColumnsInfo(Class clazz) {
        ArrayList<EntityColumn> columns = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            columns.add(getColumn(null, f));
        }
        return columns;
    }

    private ArrayList<EntityColumn> getColumnsInfo(T obj) {
        ArrayList<EntityColumn> columns = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            columns.add(getColumn(obj, f));
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
