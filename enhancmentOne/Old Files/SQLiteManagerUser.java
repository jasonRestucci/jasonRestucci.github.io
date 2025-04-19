package com.example.cs_360_jasonrestucci_option1_projecttwo;
import static com.example.cs_360_jasonrestucci_option1_projecttwo.MainActivity.itemArrayList;
import static com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.LoginActivity.userArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.User;


public class SQLiteManagerUser extends SQLiteOpenHelper{
    public static SQLiteManagerUser sqLiteManagerUser;

    private static final String USER_DATA_BASE_NAME = "InvHelperDBUSER";
    private static final int DATA_BASE_VERSION = 1;
    private static final String COUNTER = "Counter";

    private static final String USER_TABLE_NAME = "User";
    private static final String USERID_FIELD = "id";
    private static final String USERNAME_FIELD = "Name";
    private static final String USERPASS_FIELD = "Password";


    //user
    public SQLiteManagerUser(Context context) {
        super(context, USER_DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }



    public static SQLiteManagerUser instanceOfUserDatabase(Context context){
        if(sqLiteManagerUser == null){
            sqLiteManagerUser = new SQLiteManagerUser(context);
        }
        return sqLiteManagerUser;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabaseUser) { // creates table for users
        StringBuilder userSQL;
        userSQL = new StringBuilder()
                .append("CREATE TABLE ")
                .append(USER_TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(USERID_FIELD)
                .append(" INT, ")
                .append(USERNAME_FIELD)
                .append(" TEXT, ")
                .append(USERPASS_FIELD)
                .append(" TEXT)");

        sqLiteDatabaseUser.execSQL(userSQL.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabaseUser, int oldVersion, int newVersion) {

    }


    // adds user to DB
    public void addUserToDatabase(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERID_FIELD, user.getUserId());
        contentValues.put(USERNAME_FIELD, user.getUsername());
        contentValues.put(USERPASS_FIELD, user.getUserpass());

        sqLiteDatabase.insert(USER_TABLE_NAME, null, contentValues);
    }


    // populates memory/array with DB users
    public void populateUserArrayList(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null)) {
            if (result.getCount() != 0){
                while (result.moveToNext()){
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    String pass = result.getString(3);
                    User user = new User(id, name, pass);
                    userArrayList.add(user);
                }
            }
        }
    }

    // update user info
    public void updateUserInDB(User user){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USERID_FIELD, user.getUserId());
        contentValues.put(USERNAME_FIELD, user.getUsername());
        contentValues.put(USERPASS_FIELD, user.getUserpass());

        sqLiteDatabase.update(USER_TABLE_NAME, contentValues, USERID_FIELD + " =? ", new String[]{String.valueOf(user.getUserId())});
    }

    //delete user
    public  void deleteUserInDB(User user){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        sqLiteDatabase.delete(USER_TABLE_NAME,  "name=?", new String[]{String.valueOf(user.getUsername())});
        sqLiteDatabase.close();
    }
}
