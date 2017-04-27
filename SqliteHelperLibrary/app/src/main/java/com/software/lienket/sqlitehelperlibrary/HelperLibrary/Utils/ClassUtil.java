package com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils;

import java.util.ArrayList;

/**
 * Created by KIEN on 4/27/2017.
 */

public class ClassUtil {
    public static boolean isExisted(ArrayList<Class> classes, Class clazz) {
        for (Class item : classes) {
            if (clazz.equals(item))
                return true;
        }
        return false;
    }

    public static boolean isContain(ArrayList<Class> container, ArrayList<Class> sub) {
        for (Class item : sub) {
            if (!isExisted(container, item))
                return false;
        }
        return true;
    }

}
