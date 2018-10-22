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

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nutrition.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(OfflineConstants.CREATE_NUTRITION_TABLE);
        sqLiteDatabase.execSQL(OfflineConstants.CREATE_DAILY_HEALTH_TABLE);
        sqLiteDatabase.execSQL(OfflineConstants.CREATE_MY_DAILY_HEALTH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OfflineConstants.NUTRITION_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OfflineConstants.MY_DAILY_HEALTH_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OfflineConstants.DAILY_HEALTH);
    }

    public void onInsertElements(String name, String calories, String fat, String cholestrol, String potassium, String sodium, String carbohydrates, String protein) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OfflineConstants.COL_NAME, name);
        contentValues.put(OfflineConstants.COL_CALORIES, calories);
        contentValues.put(OfflineConstants.COL_FAT, fat);
        contentValues.put(OfflineConstants.COL_CHOLESTROL, cholestrol);
        contentValues.put(OfflineConstants.COL_SODIUM, sodium);
        contentValues.put(OfflineConstants.COL_POTASSIUM, potassium);
        contentValues.put(OfflineConstants.COL_CARBOHYDRATES, carbohydrates);
        contentValues.put(OfflineConstants.COL_PROTEINS, protein);
        sqLiteDatabase.insertOrThrow(OfflineConstants.NUTRITION_TABLE, null, contentValues);
        sqLiteDatabase.close();

    }

    public ArrayList<DataModel> onReadElements(String elementName) {
        ArrayList<DataModel> modelArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String searchQry = " SELECT DISTINCT " +
                OfflineConstants.COL_NAME + "," +
                OfflineConstants.COL_CALORIES + "," +
                OfflineConstants.COL_FAT + "," +
                OfflineConstants.COL_CHOLESTROL + "," +
                OfflineConstants.COL_SODIUM + "," +
                OfflineConstants.COL_POTASSIUM + "," +
                OfflineConstants.COL_CARBOHYDRATES + "," +
                OfflineConstants.COL_PROTEINS +
                " FROM " + OfflineConstants.NUTRITION_TABLE + " WHERE " + OfflineConstants.COL_NAME + " = '" + elementName + "'";


        Cursor cursor = sqLiteDatabase.rawQuery(searchQry, null);

        DataModel dataModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                dataModel = new DataModel();
                dataModel.setName(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_NAME)));
                dataModel.setCalories(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_CALORIES)));
                dataModel.setFat(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_FAT)));
                dataModel.setCholestrol(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_CHOLESTROL)));
                dataModel.setSodium(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_SODIUM)));
                dataModel.setPotassium(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_POTASSIUM)));
                dataModel.setCarbohydrates(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_CARBOHYDRATES)));
                dataModel.setProteins(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_PROTEINS)));
                modelArrayList.add(dataModel);
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return modelArrayList;
    }


    public void onInsertDailyCalories(String date, String name, String calories, String fat, String cholestrol, String sodium, String potassium, String carbohydrates, String protein) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.d("TEST", "onInsertDailyCalories: "+name);
        contentValues.put(OfflineConstants.COL_DATE, date);
        contentValues.put(OfflineConstants.COL_NAME, name);
        contentValues.put(OfflineConstants.COL_ELEMENT_CALORIES, calories);
        contentValues.put(OfflineConstants.COL_ELEMENT_FAT, fat);
        contentValues.put(OfflineConstants.COL_ELEMENT_CHOLESTROL, cholestrol);
        contentValues.put(OfflineConstants.COL_ELEMENT_SODIUM, sodium);
        contentValues.put(OfflineConstants.COL_ELEMENT_POTASSIUM, potassium);
        contentValues.put(OfflineConstants.COL_ELEMENT_CARBOHYDRATES, carbohydrates);
        contentValues.put(OfflineConstants.COL_ELEMENT_PROTEINS, protein);
        sqLiteDatabase.insertOrThrow(OfflineConstants.MY_DAILY_HEALTH_TABLE, null, contentValues);
        sqLiteDatabase.close();
    }


    public ArrayList<DataModel> onReadOneDayHealthTable(String selectedDate) {
        ArrayList<DataModel> modelArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String searchQry = " SELECT DISTINCT " +
                OfflineConstants.COL_DATE + "," +
                OfflineConstants.COL_ID + "," +
                OfflineConstants.COL_NAME + "," +
                OfflineConstants.COL_CALORIES + "," +
                OfflineConstants.COL_FAT + "," +
                OfflineConstants.COL_CHOLESTROL + "," +
                OfflineConstants.COL_SODIUM + "," +
                OfflineConstants.COL_POTASSIUM + "," +
                OfflineConstants.COL_CARBOHYDRATES + "," +
                OfflineConstants.COL_PROTEINS +
                " FROM " + OfflineConstants.MY_DAILY_HEALTH_TABLE + " WHERE " + OfflineConstants.COL_DATE + " = '" + selectedDate + "'";


        Cursor cursor = sqLiteDatabase.rawQuery(searchQry, null);

        DataModel dataModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                dataModel = new DataModel();
                dataModel.setCalories(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_CALORIES)));
                dataModel.setName(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_NAME)));
                dataModel.setFat(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_FAT)));
                dataModel.setCholestrol(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_CHOLESTROL)));
                dataModel.setSodium(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_SODIUM)));
                dataModel.setPotassium(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_POTASSIUM)));
                dataModel.setCarbohydrates(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_CARBOHYDRATES)));
                dataModel.setProteins(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_PROTEINS)));
                modelArrayList.add(dataModel);
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return modelArrayList;

    }


    public void onInsertDailyData(String date, String calories, String fat, String cholestrol, String sodium, String potassium, String carbohydrates, String protein) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OfflineConstants.COL_DATE, date);
        contentValues.put(OfflineConstants.COL_ELEMENT_CALORIES, calories);
        contentValues.put(OfflineConstants.COL_ELEMENT_FAT, fat);
        contentValues.put(OfflineConstants.COL_ELEMENT_CHOLESTROL, cholestrol);
        contentValues.put(OfflineConstants.COL_ELEMENT_SODIUM, sodium);
        contentValues.put(OfflineConstants.COL_ELEMENT_POTASSIUM, potassium);
        contentValues.put(OfflineConstants.COL_ELEMENT_CARBOHYDRATES, carbohydrates);
        contentValues.put(OfflineConstants.COL_ELEMENT_PROTEINS, protein);
        sqLiteDatabase.insertOrThrow(OfflineConstants.DAILY_HEALTH, null, contentValues);
        sqLiteDatabase.close();
    }


    public ArrayList<DataModel> onReadDailyElements() {
        ArrayList<DataModel> modelArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String searchQry = " SELECT DISTINCT " +
                OfflineConstants.COL_DATE + "," +
                OfflineConstants.COL_CALORIES + "," +
                OfflineConstants.COL_FAT + "," +
                OfflineConstants.COL_CHOLESTROL + "," +
                OfflineConstants.COL_SODIUM + "," +
                OfflineConstants.COL_POTASSIUM + "," +
                OfflineConstants.COL_CARBOHYDRATES + "," +
                OfflineConstants.COL_PROTEINS +
                " FROM " + OfflineConstants.DAILY_HEALTH;


        Cursor cursor = sqLiteDatabase.rawQuery(searchQry, null);

        DataModel dataModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                dataModel = new DataModel();
                dataModel.setCalories(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_CALORIES)));
                dataModel.setDate(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_DATE)));
                dataModel.setFat(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_FAT)));
                dataModel.setCholestrol(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_CHOLESTROL)));
                dataModel.setSodium(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_SODIUM)));
                dataModel.setPotassium(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_POTASSIUM)));
                dataModel.setCarbohydrates(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_CARBOHYDRATES)));
                dataModel.setProteins(cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_PROTEINS)));
                modelArrayList.add(dataModel);
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return modelArrayList;
    }


    public String onReadCalories(String selectedDate) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String searchQry = " SELECT DISTINCT " +
                OfflineConstants.COL_CALORIES +
                " FROM " + OfflineConstants.DAILY_HEALTH + " WHERE " + OfflineConstants.COL_DATE + " = '" + selectedDate + "'";


        Cursor cursor = sqLiteDatabase.rawQuery(searchQry, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String totalCalories = cursor.getString(cursor.getColumnIndex(OfflineConstants.COL_CALORIES));

            if (totalCalories != null) {
                cursor.close();
                return totalCalories;
            } else {
                cursor.close();
                return "0";
            }
        } else return "0";


    }


    public void onUpdateDailyCalories(String updatedCalories, String selectedDate) {
        String updateQuery = " UPDATE " + OfflineConstants.DAILY_HEALTH +
                " SET "
                + OfflineConstants.COL_CALORIES + "='" + updatedCalories + "'" +
                " WHERE " + OfflineConstants.COL_DATE + "='" + selectedDate + "'";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(updateQuery);
    }
}