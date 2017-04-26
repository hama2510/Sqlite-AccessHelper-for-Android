package com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils;

import java.lang.reflect.Field;

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

    public static int findInArray(String[] arr, String str) {
        int length = arr.length;
        int index = -1;
        if (arr != null && str != null) {
            for (int i = 0; i < length; i++) {
                if (str.equals(arr[i])) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}
