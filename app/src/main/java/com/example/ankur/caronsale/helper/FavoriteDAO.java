package com.example.ankur.caronsale.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.Model.Favorite;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO extends SQLiteOpenHelper {

    public FavoriteDAO(Context context){
        super(context,"android_api",null,6);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void dbInsert(String userId, long carID){
        SQLiteDatabase database = getWritableDatabase();
        //ContentValues values = getContentValues(objFav);
        String sql = "INSERT INTO FAVORITE VALUES(null,"+carID+",'"+userId+"',1)";
        database.execSQL(sql);
        //database.insert("FAVORITE",null,values);
    }

    @NonNull
    private ContentValues getContentValues(Favorite objFav) {
        ContentValues values = new ContentValues();

        //CREATE TABLE FAVORITE (id INTEGER PRIMARY KEY,carID INTEGER, ownerId TEXT)";

        values.put("carID",objFav.getCarID());
        values.put("ownerId",objFav.getOwnerId());
        values.put("IsFavorite",objFav.getIsFavorite());
        return values;
    }

    public List<Favorite> dbSearch(String userId, long carID){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * from FAVORITE where ownerId = "+userId + " AND carID = "+carID ;

        Cursor c = db.rawQuery(sql,null);

        List<Favorite> favoriteList = new ArrayList<Favorite>();

        while (c.moveToNext()){
            Favorite objFav = new Favorite();

            //carId INTEGER PRIMARY KEY NOT NULL, modelName TEXT NOT NULL," +
            //                " description TEXT NOT NULL, carCapacity TEXT NOT NULL, kmsDriven TEXT NOT NULL, price TEXT NOT NULL, mfgYear TEXT, ownerId TEXT, carPhoto TEXT, ownerType TEXT)";

            objFav.setId(c.getLong(c.getColumnIndex("id")));
            objFav.setCarID(c.getLong(c.getColumnIndex("carID")));
            objFav.setOwnerId(c.getString(c.getColumnIndex("ownerId")));
            objFav.setIsFavorite(c.getInt(c.getColumnIndex("IsFavorite")));

            favoriteList.add(objFav);
        }
        c.close();

        return favoriteList;
    }

    public List<Favorite> dbSearchCar(String userId){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * from FAVORITE where ownerId = "+userId;

        Cursor c = db.rawQuery(sql,null);

        List<Favorite> favoriteList = new ArrayList<Favorite>();

        while (c.moveToNext()){
            Favorite objFav = new Favorite();

            //carId INTEGER PRIMARY KEY NOT NULL, modelName TEXT NOT NULL," +
            //                " description TEXT NOT NULL, carCapacity TEXT NOT NULL, kmsDriven TEXT NOT NULL, price TEXT NOT NULL, mfgYear TEXT, ownerId TEXT, carPhoto TEXT, ownerType TEXT)";

            objFav.setId(c.getLong(c.getColumnIndex("id")));
            objFav.setCarID(c.getLong(c.getColumnIndex("carID")));
            objFav.setOwnerId(c.getString(c.getColumnIndex("ownerId")));
            objFav.setIsFavorite(c.getInt(c.getColumnIndex("IsFavorite")));

            favoriteList.add(objFav);
        }
        c.close();

        return favoriteList;
    }

    public void dbUpdate(String userId, long carID){
        SQLiteDatabase db = getWritableDatabase();

       /* ContentValues favData = getContentValues(objFav);
        //String[] params = {((Long.toString(objFav.getId())))};
        //db.update("CAR",carData,"carId = ?",params);
/*        String strSQL = "UPDATE CAR SET IsLiked = 1 WHERE carId = "+ objCar.getCarId();

        db.execSQL(strSQL);*/

        String sql = "DELETE FROM FAVORITE where ownerId = "+userId + " AND carID = "+carID ;
        db.execSQL(sql);

       // db.delete("FAVORITE","id ="+objFav.getId(),null);
       // db.update("FAVORITE",favData,"id ="+objFav.getId(),null);

    }
}
