package net.mobindustry.rxedu.briteDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.mobindustry.rxedu.model.Address;
import net.mobindustry.rxedu.model.Company;
import net.mobindustry.rxedu.model.Geo;
import net.mobindustry.rxedu.model.User;

final class DbOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    private static final String CREATE_USER = ""
            + "CREATE TABLE " + User.TABLE + "("
            + User.COL_ID + " INTEGER NOT NULL PRIMARY KEY,"
            + User.COL_USER_ID + " INTEGER NOT NULL,"
            + User.COL_NAME + " TEXT NOT NULL,"
            + User.COL_USER_NAME + " TEXT NOT NULL,"
            + User.COL_EMAIL + " TEXT NOT NULL,"
            + User.COL_PHONE + " TEXT NOT NULL,"
            + User.COL_WEB_SITE + " TEXT NOT NULL"
            + ")";

    private static final String CREATE_ADDRESS = ""
            + "CREATE TABLE " + Address.TABLE + "("
            + Address.COL_ID + " INTEGER NOT NULL PRIMARY KEY,"
            + Address.COL_USER_ID + " INTEGER NOT NULL,"
            + Address.COL_STREET + " TEXT NOT NULL,"
            + Address.COL_SUITE + " TEXT NOT NULL,"
            + Address.COL_CITY + " TEXT NOT NULL,"
            + Address.COL_ZIP_CODE + " TEXT NOT NULL"
            + ")";

    private static final String CREATE_GEO = ""
            + "CREATE TABLE " + Geo.TABLE + "("
            + Geo.COL_ID + " INTEGER NOT NULL PRIMARY KEY,"
            + Geo.COL_ADDRESS_ID + " INTEGER NOT NULL,"
            + Geo.COL_LAT + " REAL NOT NULL,"
            + Geo.COL_LNG + " REAL NOT NULL"
            + ")";

    private static final String CREATE_COMPANY = ""
            + "CREATE TABLE " + Company.TABLE + "("
            + Company.COL_ID + " INTEGER NOT NULL PRIMARY KEY,"
            + Company.COL_USER_ID + " INTEGER NOT NULL,"
            + Company.COL_NAME + " TEXT NOT NULL,"
            + Company.COL_CATCH_PHRASE + " TEXT NOT NULL,"
            + Company.COL_BS + " TEXT NOT NULL"
            + ")";

    public DbOpenHelper(Context context) {
        super(context, "rxEdu.db", null /* factory */, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, CREATE_USER);
        createTable(db, CREATE_ADDRESS);
        createTable(db, CREATE_GEO);
        createTable(db, CREATE_COMPANY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void createTable(SQLiteDatabase sqLiteDatabase, String sql) {
        try {
            sqLiteDatabase.execSQL(sql);
        } catch (Exception e) {
            String textError = "Error create table [" + sql + "]";
            textError = textError + " Error = " + e.getMessage();
            Log.e(this.getClass().getName(), textError);
        }
    }
}
