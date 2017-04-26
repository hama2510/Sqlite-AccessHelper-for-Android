package com.software.lienket.sqlitehelperlibrary.HelperLibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Excecption.NotEntityException;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Object.Entity;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils.QueryUtil;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils.PackageParser;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils.EntityUtil;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils.StringUtil;

import java.util.ArrayList;

/**
 * Created by KIEN on 4/20/2017.
 */

public class DatabaseConnection extends SQLiteOpenHelper {
    private static int version;
    private static String databaseName;
    private static DatabaseConnection connection;
    private static Context context;
    private static ArrayList<Entity> entities = new ArrayList<>();

    private DatabaseConnection(Context context) {
        super(context, databaseName, null, version);
    }

    public static void init(Context context) {
        if (context == null)
            throw new RuntimeException("Context is null");
        if (DatabaseConnection.context == null) {
            DatabaseConnection.context = context;
        }
    }

    public static void setupDatabase(String databaseName, int version, String packageName) {
        if (context == null)
            throw new RuntimeException("Database has not been initialized!");
        if (connection == null) {
            if (StringUtil.isBlank(databaseName))
                throw new RuntimeException("Database name cannot be blank");
            if (version <= 0)
                throw new RuntimeException("Database version minimum is 1");
            if (StringUtil.isBlank(packageName))
                throw new RuntimeException("Package contains persistent objects cannot be blank");
            PackageParser.init(context);
            DatabaseConnection.databaseName = databaseName;
            DatabaseConnection.version = version;
            ArrayList<Class> classes = PackageParser.getInstance().getEntities(packageName);
            for (Class item : classes) {
                if (EntityUtil.getInstance().isEntity(item))
                    entities.add(EntityUtil.getInstance().getEntity(item));
                else {
                    throw new NotEntityException(item.getName());
                }
            }
            if (entities.isEmpty())
                throw new RuntimeException("Package contains persistent objects cannot be blank");
            connection = new DatabaseConnection(context);
        }
    }

    public static DatabaseConnection getConnection() {
        if (connection == null)
            throw new RuntimeException("Database has not been set up!");
        return connection;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ArrayList<String> ls = QueryUtil.createTable(entities);
        for (String item : ls) {
            db.execSQL(item);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArrayList<String> ls = QueryUtil.deleteTable(entities);
        ls.addAll(QueryUtil.createTable(entities));
        for (String item : ls) {
            db.execSQL(item);
        }
    }
}
