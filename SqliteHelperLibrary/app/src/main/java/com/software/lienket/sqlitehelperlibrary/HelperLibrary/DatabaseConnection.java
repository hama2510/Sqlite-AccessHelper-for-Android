package com.software.lienket.sqlitehelperlibrary.HelperLibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils.PackageParser;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils.QueryUtil;
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
    private static ArrayList<Class> classes = new ArrayList<>();

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
            classes = PackageParser.getInstance().getClasses(packageName);
            if (classes.isEmpty())
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
        ArrayList<String> ls = QueryUtil.createTable(classes);
        for (String item : ls) {
            db.execSQL(item);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        QueryUtil queryUtil;
        for (Class item : classes) {
            queryUtil = new QueryUtil(item);
            db.execSQL(queryUtil.deleteTable());
        }
        ArrayList<String> ls = new ArrayList<>();
        ls.addAll(QueryUtil.createTable(classes));
        for (String item : ls) {
            db.execSQL(item);
        }
    }
}
