package com.software.lienket.library.exception;

/**
 * Created by KIEN on 4/26/2017.
 */

public class NoAccessibleException extends RuntimeException {
    public NoAccessibleException() {
        super("Cannot get value from entity or value is null");
    }
}
