package com.example.ankur.caronsale.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ankur.caronsale.Model.REGISTERD_USERS;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "REGISTERD_USERS";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
    public  Context context;

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";*/

        /*String CREATE_LOGIN_TABLE = "CREATE TABLE registerd_users(id INTEGER PRIMARY KEY NOT NULL, unique_id TEXT NOT NULL UNIQUE, NAME TEXT NOT NULL," +
                "EMAIL TEXT NOT NULL UNIQUE, ENCRYPTED_PASSWORD TEXT NOT NULL, PHONE_NUMBER INTEGER NOT NULL, STREET_ADDRESS TEXT NOT NULL," +
                "POSTAL_CODE TEXT NOT NULL, CREATED_AT TEXT, UPDATE_AT TEXT)";*/

        String CREATE_LOGIN_TABLE = "CREATE TABLE REGISTERD_USERS (ID INTEGER PRIMARY KEY NOT NULL, UNIQUE_ID TEXT NOT NULL, NAME TEXT NOT NULL," +
                "EMAIL TEXT NOT NULL, PHONE_NUMBER TEXT NOT NULL, STREET_ADDRESS TEXT, POSTAL_CODE TEXT, CREATED_AT TEXT, UPDATED_AT TEXT,ISLOGGEDIN INTEGER)";
        sqLiteDatabase.execSQL(CREATE_LOGIN_TABLE);

        String CAR_TABLE = "CREATE TABLE CAR (carId INTEGER PRIMARY KEY NOT NULL, modelName TEXT NOT NULL," +
                " description TEXT NOT NULL, carCapacity TEXT NOT NULL, kmsDriven TEXT NOT NULL, price TEXT NOT NULL, mfgYear TEXT, ownerId TEXT, carPhoto TEXT, ownerType TEXT,IsLiked INTEGER)";
        sqLiteDatabase.execSQL(CAR_TABLE);

        String sqlPhoto = "CREATE TABLE PhotoBook (id INTEGER PRIMARY KEY, carID INTEGER, PhotoPath TEXT)";
        sqLiteDatabase.execSQL(sqlPhoto);

        String favTable = "CREATE TABLE FAVORITE (id INTEGER PRIMARY KEY,carID INTEGER, ownerId TEXT,IsFavorite INTEGER)";
        sqLiteDatabase.execSQL(favTable);

        String commentTable= "CREATE TABLE COMMENT(id INTEGER PRIMARY KEY,carID INEGER,ownerId TEXT,comment_user_id TEXT, " +
                "commentText TEXT)";
        sqLiteDatabase.execSQL(commentTable);

        String likeTable = "CREATE TABLE LIKETABLE (id INTEGER PRIMARY KEY,carID INTEGER, ownerId TEXT, IsLiked INTEGER)";
        sqLiteDatabase.execSQL(likeTable);


        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "CAR");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "PhotoBook");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "FAVORITE");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "COMMENT");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "LIKETABLE");

        // Create tables again
        onCreate(sqLiteDatabase);
    }
    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at, String phone_number,
            String street_address, String postalCode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        /*values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At*/
        values.put("NAME", name); // Name
        values.put("EMAIL", email); // Email
        values.put("unique_id", uid); // Email
        values.put("CREATED_AT", created_at); // Email
        values.put("PHONE_NUMBER", phone_number); // Email
        values.put("STREET_ADDRESS", street_address); // Email
        values.put("POSTAL_CODE", postalCode); // Email
        values.put("ISLOGGEDIN",1);

        // Inserting Row
        //HashMap<String, String> objUser = getUserDetailsFromID(uid);
        //if(objUser==null){
            long id = db.insert(TABLE_USER, null, values);
        //}//else{
            //update query to make isloggedIn true
           // updateUser(uid);
        //}


        db.close(); // Closing database connection

        //Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void updateUser(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        String strSQL = "UPDATE REGISTERD_USERS SET ISLOGGEDIN = 1 WHERE ISLOGGEDIN = "+ 0;

        db.execSQL(strSQL);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE " + "ISLOGGEDIN = 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("uid", cursor.getString(1));
            user.put("name", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("PHONE_NUMBER", cursor.getString(4)); // Email
            user.put("STREET_ADDRESS", cursor.getString(5)); // Email
            user.put("POSTAL_CODE", cursor.getString(6)); // Email
            user.put("created_at", cursor.getString(7));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public HashMap<String, String> getUserDetailsFromID(String id) {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE " +
                "UNIQUE_ID = "+ id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("uid", cursor.getString(1));
            user.put("name", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("PHONE_NUMBER", cursor.getString(4)); // Email
            user.put("STREET_ADDRESS", cursor.getString(5)); // Email
            user.put("POSTAL_CODE", cursor.getString(6)); // Email
            user.put("created_at", cursor.getString(7));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows

        String strSQL = "UPDATE REGISTERD_USERS SET ISLOGGEDIN = 0";

        db.execSQL(strSQL);


        //db.delete(TABLE_USER, null, null);
/*        db.delete("CAR", null, null);
        db.delete("PhotoBook", null, null);
        context.deleteDatabase(DATABASE_NAME);*/
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void dbUpdate(String name, String email, String uid, String created_at,String phone_number,
                         String street_address, String postalCode){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NAME", name); // Name
        values.put("EMAIL", email); // Email
        values.put("unique_id", uid); // Email
        values.put("CREATED_AT", created_at); // Email
        values.put("PHONE_NUMBER", phone_number); // Email
        values.put("STREET_ADDRESS", street_address); // Email
        values.put("POSTAL_CODE", postalCode); // Email
        values.put("ISLOGGEDIN",1);


        //ContentValues agentData = getContentValues(agent);
        //String[] params = {((Long.toString(agent.getAgentId())))};
        db.update("REGISTERD_USERS",values,"UNIQUE_ID ="+uid,null);
    }
}
