package com.software.lienket.sqlitehelperlibrary.HelperLibrary.Excecption;

/**
 * Created by KIEN on 4/26/2017.
 */

public class NotEntityException extends RuntimeException {
    public NotEntityException(String className) {
        super("Class " + className + " is not an entity");
    }
}
