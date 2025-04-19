package com.example.cs_360_jasonrestucci_option1_projecttwo;

import static com.example.cs_360_jasonrestucci_option1_projecttwo.MainActivity.itemArrayList;
import static com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.LoginActivity.userArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.User;

/**
 * Author: Jason Restucci
 * Date Last Modified: 3/18/25
 * Description: Manager for the user table
 */

public class SQLiteManager extends SQLiteOpenHelper {

    public static SQLiteManager sqLiteManager;

    private static final String DATA_BASE_NAME = "InvHelperDB";
    private static final int DATA_BASE_VERSION = 1;
    private static final String TABLE_NAME = "Item";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String ITEM_NAME_FIELD = "Name";
    private static final String ITEM_QUANTITY_FIELD = "Qty";
    private static final String ITEM_CATEGORY_FIELD = "Category";

    //item
    public SQLiteManager(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }



    public static SQLiteManager instanceOfDatabase(Context context){
        if(sqLiteManager == null){
            sqLiteManager = new SQLiteManager(context);
        }
        return sqLiteManager;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // item table
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(ITEM_NAME_FIELD)
                .append(" TEXT, ")
                .append(ITEM_CATEGORY_FIELD)
                .append(" TEXT, ")
                .append(ITEM_QUANTITY_FIELD)
                .append(" INT)");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    // adds item to database
    public void addItemToDatabase(item item){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, item.getId());
        contentValues.put(ITEM_NAME_FIELD, item.getName());
        contentValues.put(ITEM_CATEGORY_FIELD, item.getCategory());
        contentValues.put(ITEM_QUANTITY_FIELD, item.getQty());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    // populates memory/array from database items
    public void populateItemArrayList(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if (result.getCount() != 0){
                while (result.moveToNext()){
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    String category = result.getString(3);
                    int qty = result.getInt(4);
                    item item = new item(id, name, qty, category);
                    itemArrayList.add(item);
                }
            }
        }
    }

    // updates item in DB
    public void updateItemInDB(item item){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_FIELD, item.getId());
        contentValues.put(ITEM_NAME_FIELD, item.getName());
        contentValues.put(ITEM_CATEGORY_FIELD, item.getCategory());
        contentValues.put(ITEM_QUANTITY_FIELD, item.getQty());

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(item.getId())});
    }

    // deletes item in DB
    public  void deleteItemInDB(item item){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        sqLiteDatabase.delete(TABLE_NAME,  "name=?", new String[]{String.valueOf(item.getName())});
        sqLiteDatabase.close();
    }
}
