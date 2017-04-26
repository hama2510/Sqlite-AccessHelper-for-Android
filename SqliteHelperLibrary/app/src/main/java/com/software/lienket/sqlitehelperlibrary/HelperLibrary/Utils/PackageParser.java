package com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils;

import android.content.Context;
import android.util.Log;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Object.Entity;

import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * Created by KIEN on 4/21/2017.
 */

public class PackageParser {

    private static PackageParser instance;
    private static Context context;

    private PackageParser(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        if (context != null) {
            if (instance == null)
                instance = new PackageParser(context);
        } else {
            throw new RuntimeException("Context is null!");
        }
    }

    public static PackageParser getInstance() {
        if (instance == null)
            throw new RuntimeException("PackageParser need init to use!");
        return instance;
    }

    /**
     * @param packageName name of the package where you store entity classes
     * @return
     */
    public ArrayList<Class> getClasses(String packageName) {
        ArrayList<Class> classes = new ArrayList<>();
        try {
            PathClassLoader classLoader = (PathClassLoader) context.getClassLoader();
            String packageCodePath = context.getPackageCodePath();
            DexFile df = new DexFile(packageCodePath);
            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
                String className = iter.nextElement();
                if (isTargetClassName(className)) {
                    if (isTargetInPackage(className, packageName)) {
                        classes.add(classLoader.loadClass(className));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * @param classes list all entity classes
     * @return a graph of list entities
     */
    public ArrayList<Entity> getEntities(ArrayList<Class> classes) {
        ArrayList<Entity> entities = new ArrayList<>();
        for (Class item : classes) {
            entities.add(EntityUtil.getInstance().getEntity(item));
        }
    }

    private boolean isTargetClassName(String className) {
        return className.startsWith(context.getPackageName()) && !className.contains("$");
    }

    private boolean isTargetInPackage(String className, String packageName) {
        return className.contains(packageName);

    }
//    public static void main(String[] args) {
//        PackageParser.getInstance().getAllFilesInPackage("Persistent").get(0);
//    }
}
