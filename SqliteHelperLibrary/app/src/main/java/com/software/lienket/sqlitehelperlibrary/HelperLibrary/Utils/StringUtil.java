package com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils;

/**
 * Created by KIEN on 4/20/2017.
 */

public class StringUtil {
    public static boolean isBlank(String str) {
        if (str == null || str.equals(""))
            return true;
        return false;
    }

    public static String getDatabaseType(Class f) {
        if (f.getName().equals("int"))
            return "INTEGER";
        else if (f.getName().equals("java.lang.Integer"))
            return "INTEGER";
        else if (f.getName().equals("long"))
            return "INTEGER";
        else if (f.getName().equals("java.lang.Long"))
            return "INTEGER";
        else if (f.getName().equals("float"))
            return "REAL";
        else if (f.getName().equals("java.lang.Float"))
            return "REAL";
        else if (f.getName().equals("double"))
            return "REAL";
        else if (f.getName().equals("java.lang.Double"))
            return "REAL";
        else if (f.getName().equals("java.lang.String"))
            return "TEXT";
        throw new RuntimeException("Data type is not supported");
    }
}
