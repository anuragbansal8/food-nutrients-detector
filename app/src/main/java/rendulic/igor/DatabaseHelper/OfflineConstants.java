package rendulic.igor.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Wasim on 5/30/2018.
 */


public class OfflineConstants {

    // here are the table name and the column names of category table

    public static final String NUTRITION_TABLE = "nutrition_table";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_CALORIES = "calories";
    public static final String COL_FAT = "fat_g";
    public static final String COL_CHOLESTROL = "cholestrol_mg";
    public static final String COL_SODIUM = "sodium_mg";
    public static final String COL_POTASSIUM = "potassium_mg";
    public static final String COL_CARBOHYDRATES = "carbohydrates_g";
    public static final String COL_PROTEINS = "proteins_g";
    // here is the Query to create the category table

    public static final String CREATE_NUTRITION_TABLE = " CREATE TABLE "
            + NUTRITION_TABLE + " ( "
            + COL_ID + " INTEGER PRIMARY KEY,"
            + COL_NAME + " TEXT, "
            + COL_CALORIES + " TEXT,"
            + COL_FAT + " TEXT,"
            + COL_CHOLESTROL + " TEXT,"
            + COL_SODIUM + " TEXT,"
            + COL_POTASSIUM + " TEXT,"
            + COL_CARBOHYDRATES + " TEXT,"
            + COL_PROTEINS + " TEXT )";

    public static final String MY_DAILY_HEALTH_TABLE = "my_daily_health";
    public static final String COL_HEALTH_ID = "id";
    public static final String COL_DATE = "date";
    public static final String COL_ELEMENT_NAME = "name";
    public static final String COL_ELEMENT_CALORIES = "calories";
    public static final String COL_ELEMENT_FAT = "fat_g";
    public static final String COL_ELEMENT_CHOLESTROL = "cholestrol_mg";
    public static final String COL_ELEMENT_SODIUM = "sodium_mg";
    public static final String COL_ELEMENT_POTASSIUM = "potassium_mg";
    public static final String COL_ELEMENT_CARBOHYDRATES = "carbohydrates_g";
    public static final String COL_ELEMENT_PROTEINS = "proteins_g";


    public static final String CREATE_DAILY_HEALTH_TABLE = " CREATE TABLE "
            + MY_DAILY_HEALTH_TABLE + " ( "
            + COL_HEALTH_ID + " INTEGER PRIMARY KEY,"
            + COL_DATE + " TEXT, "
            + COL_ELEMENT_NAME + " TEXT, "
            + COL_ELEMENT_CALORIES + " TEXT,"
            + COL_ELEMENT_FAT + " TEXT,"
            + COL_ELEMENT_CHOLESTROL + " TEXT,"
            + COL_ELEMENT_SODIUM + " TEXT,"
            + COL_ELEMENT_POTASSIUM + " TEXT,"
            + COL_ELEMENT_CARBOHYDRATES + " TEXT,"
            + COL_ELEMENT_PROTEINS + " TEXT )";


    public static final String DAILY_HEALTH = "DAILY_HEALTH";

    public static final String CREATE_MY_DAILY_HEALTH_TABLE = " CREATE TABLE "
            + DAILY_HEALTH + " ( "
            + COL_HEALTH_ID + " INTEGER PRIMARY KEY,"
            + COL_DATE + " TEXT, "
            + COL_ELEMENT_CALORIES + " TEXT,"
            + COL_ELEMENT_FAT + " TEXT,"
            + COL_ELEMENT_CHOLESTROL + " TEXT,"
            + COL_ELEMENT_SODIUM + " TEXT,"
            + COL_ELEMENT_POTASSIUM + " TEXT,"
            + COL_ELEMENT_CARBOHYDRATES + " TEXT,"
            + COL_ELEMENT_PROTEINS + " TEXT )";


    public static final String SP_MAIN = "SP_MAIN";


    public static final String SP_IS_ELEMENTS_INSERTED = "SP_IS_ELEMENTS_INSERTED";
    public static final String KEY_SELECETED_POSTION = "KEY_SELECETED_POSTION";
    public static final String SP_ITEM_MAIN ="SP_ITEM_MAIN" ;
    public static final String SP_ITEM_NAME ="SP_ITEM_NAME" ;
}
