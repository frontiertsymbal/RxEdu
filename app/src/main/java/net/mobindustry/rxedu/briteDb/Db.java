package net.mobindustry.rxedu.briteDb;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

public final class Db {

    public static final int BOOLEAN_FALSE = 0;
    public static final int BOOLEAN_TRUE = 1;
    private static BriteDatabase singleton;

    private Db() {
        throw new AssertionError("No instances.");
    }

    public static BriteDatabase getInstance() {
        if (singleton == null) {
            throw new AssertionError("No instances.");
        }
        return singleton;
    }

    public static void initDataBase(Context context) {
        SqlBrite sqlBrite = SqlBrite.create(message -> Log.d("SqlBrite", message));
        DbOpenHelper dbOpenHelper = new DbOpenHelper(context);
        singleton = sqlBrite.wrapDatabaseHelper(dbOpenHelper);
        singleton.setLoggingEnabled(true);
    }

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }
}
