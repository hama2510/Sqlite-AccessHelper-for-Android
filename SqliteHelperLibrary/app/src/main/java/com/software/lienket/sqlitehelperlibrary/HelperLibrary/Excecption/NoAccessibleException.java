package com.software.lienket.sqlitehelperlibrary.HelperLibrary.Excecption;

/**
 * Created by KIEN on 4/26/2017.
 */

public class NoAccessibleException extends RuntimeException {
    public NoAccessibleException() {
        super("Cannot get value from entity. Please provide getter and setter method");
    }
}
