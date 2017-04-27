package com.software.lienket.library.object;

/**
 * Created by KIEN on 4/23/2017.
 */

public class EntityColumn<T> {
    private boolean id = false;
    private String name;
    private Class dataType;
    private T value;
    private Class belongsTo;
    private String updateAction, deleteAction;
    private String fieldType;

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getDataType() {
        return dataType;
    }

    public void setDataType(Class dataType) {
        this.dataType = dataType;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Class getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(Class belongsTo) {
        this.belongsTo = belongsTo;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(String updateAction) {
        this.updateAction = updateAction;
    }

    public String getDeleteAction() {
        return deleteAction;
    }

    public void setDeleteAction(String deleteAction) {
        this.deleteAction = deleteAction;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
