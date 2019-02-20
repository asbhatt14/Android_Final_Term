package com.example.ankur.caronsale.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.ankur.caronsale.Model.Favorite;
import com.example.ankur.caronsale.Model.Like;

import java.util.ArrayList;
import java.util.List;

public class LikeDAO extends SQLiteOpenHelper {

    public LikeDAO(Context context){
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
        String sql = "INSERT INTO LIKETABLE VALUES(null,"+carID+",'"+userId+"',1)";
        database.execSQL(sql);

    }

    @NonNull
    private ContentValues getContentValues(Like objFav) {
        ContentValues values = new ContentValues();

        //CREATE TABLE FAVORITE (id INTEGER PRIMARY KEY,carID INTEGER, ownerId TEXT)";

        values.put("carID",objFav.getCarID());
        values.put("ownerId",objFav.getOwnerId());
        values.put("IsLiked",objFav.getIsLike());
        return values;
    }

    public List<Like> dbSearch(String userId, long carID){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * from LIKETABLE where ownerId = "+userId + " AND carID = "+carID ;

        //String sql = "SELECT * from LIKETABLE where carID = "+carID ;

        Cursor c = db.rawQuery(sql,null);

        List<Like> likeList = new ArrayList<Like>();

        while (c.moveToNext()){
            Like objLike = new Like();

            //carId INTEGER PRIMARY KEY NOT NULL, modelName TEXT NOT NULL," +
            //                " description TEXT NOT NULL, carCapacity TEXT NOT NULL, kmsDriven TEXT NOT NULL, price TEXT NOT NULL, mfgYear TEXT, ownerId TEXT, carPhoto TEXT, ownerType TEXT)";

            objLike.setId(c.getLong(c.getColumnIndex("id")));
            objLike.setCarID(c.getLong(c.getColumnIndex("carID")));
            objLike.setOwnerId(c.getString(c.getColumnIndex("ownerId")));
            objLike.setIsLike(c.getInt(c.getColumnIndex("IsLiked")));

            likeList.add(objLike);
        }
        c.close();

        return likeList;
    }

    public List<Like> dbSearchForLike(String userId, long carID){
        SQLiteDatabase db = this.getReadableDatabase();

        //String sql = "SELECT * from LIKETABLE where ownerId = "+userId + " AND carID = "+carID ;

        String sql = "SELECT * from LIKETABLE where carID = "+carID ;

        Cursor c = db.rawQuery(sql,null);

        List<Like> likeList = new ArrayList<Like>();

        while (c.moveToNext()){
            Like objLike = new Like();

            //carId INTEGER PRIMARY KEY NOT NULL, modelName TEXT NOT NULL," +
            //                " description TEXT NOT NULL, carCapacity TEXT NOT NULL, kmsDriven TEXT NOT NULL, price TEXT NOT NULL, mfgYear TEXT, ownerId TEXT, carPhoto TEXT, ownerType TEXT)";

            objLike.setId(c.getLong(c.getColumnIndex("id")));
            objLike.setCarID(c.getLong(c.getColumnIndex("carID")));
            objLike.setOwnerId(c.getString(c.getColumnIndex("ownerId")));
            objLike.setIsLike(c.getInt(c.getColumnIndex("IsLiked")));

            likeList.add(objLike);
        }
        c.close();

        return likeList;
    }

    public void dbUpdate(String userId, long carID){
        SQLiteDatabase db = getWritableDatabase();

       /* ContentValues favData = getContentValues(objFav);
        //String[] params = {((Long.toString(objFav.getId())))};
        //db.update("CAR",carData,"carId = ?",params);
/*        String strSQL = "UPDATE CAR SET IsLiked = 1 WHERE carId = "+ objCar.getCarId();

        db.execSQL(strSQL);*/

        String sql = "DELETE FROM LIKETABLE where ownerId = "+userId + " AND carID = "+carID ;
        db.execSQL(sql);

        // db.delete("FAVORITE","id ="+objFav.getId(),null);
        // db.update("FAVORITE",favData,"id ="+objFav.getId(),null);

    }
}
