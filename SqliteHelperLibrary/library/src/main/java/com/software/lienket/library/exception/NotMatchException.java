package com.software.lienket.library.exception;

/**
 * Created by KIEN on 4/26/2017.
 */

public class NotMatchException extends RuntimeException {
    public NotMatchException(String attribute, String attributeType, String column, String columnType) {
        super("Attribute " + attribute + " has type is " + attributeType
                + ", but column " + column + " has type is " + columnType);
    }
}
