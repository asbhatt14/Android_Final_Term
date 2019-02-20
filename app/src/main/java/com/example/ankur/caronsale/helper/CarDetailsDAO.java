package com.example.ankur.caronsale.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.ankur.caronsale.Model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarDetailsDAO extends SQLiteOpenHelper {

    public CarDetailsDAO(Context context){
        super(context,"android_api",null,6);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void dbInsert(Car objCar){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = getContentValues(objCar);
        database.insert("CAR",null,values);
    }

    @NonNull
    private ContentValues getContentValues(Car objCar) {
        ContentValues values = new ContentValues();

        //carId INTEGER PRIMARY KEY NOT NULL, modelName TEXT NOT NULL," +
        //                " description TEXT NOT NULL, carCapacity TEXT NOT NULL, kmsDriven TEXT NOT NULL, price TEXT NOT NULL, mfgYear TEXT, ownerId TEXT, carPhoto TEXT, ownerType TEXT)";

        values.put("modelName",objCar.getModelName());
        values.put("description",objCar.getDescription());
        values.put("carCapacity",objCar.getCarCapacity());
        values.put("kmsDriven",objCar.getKmsDriven());
        values.put("price",objCar.getPrice());
        values.put("mfgYear",objCar.getMfgYear());
        values.put("ownerId",objCar.getOwnerId());
        values.put("carPhoto",objCar.getCarPhoto());
        values.put("ownerType",objCar.getOwnerType());
        values.put("IsLiked",objCar.getIsLiked());
        return values;
    }

    public void dbUpdate(long carId){
        SQLiteDatabase db = getWritableDatabase();

        /*ContentValues carData = getContentValues(objCar);
        String[] params = {((Long.toString(objCar.getCarId())))};*/
        //db.update("CAR",carData,"carId = ?",params);
        String strSQL = "UPDATE CAR SET IsLiked = 0 WHERE carId = "+ carId;

        db.execSQL(strSQL);

        //db.update("CAR",carData,"carId ="+objCar.getCarId(),null);
    }

    public void dbUpdateCar(Car objCar){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues carData = getContentValues(objCar);
        String[] params = {((Long.toString(objCar.getCarId())))};
        //db.update("CAR",carData,"carId = ?",params);

        db.update("CAR",carData,"carId ="+objCar.getCarId(),null);
    }

    public List<Car> dbSearch(){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * from CAR";

        Cursor c = db.rawQuery(sql,null);

        List<Car> carList = new ArrayList<Car>();

        while (c.moveToNext()){
            Car objCar = new Car();

           //carId INTEGER PRIMARY KEY NOT NULL, modelName TEXT NOT NULL," +
            //                " description TEXT NOT NULL, carCapacity TEXT NOT NULL, kmsDriven TEXT NOT NULL, price TEXT NOT NULL, mfgYear TEXT, ownerId TEXT, carPhoto TEXT, ownerType TEXT)";

            objCar.setCarId(c.getLong(c.getColumnIndex("carId")));
            objCar.setModelName(c.getString(c.getColumnIndex("modelName")));
            objCar.setPrice(c.getString(c.getColumnIndex("price")));
            objCar.setDescription(c.getString(c.getColumnIndex("description")));
            objCar.setCarCapacity(c.getString(c.getColumnIndex("carCapacity")));
            objCar.setKmsDriven(c.getString(c.getColumnIndex("kmsDriven")));
            objCar.setMfgYear(c.getString(c.getColumnIndex("mfgYear")));
            objCar.setOwnerId(c.getString(c.getColumnIndex("ownerId")));
            objCar.setCarPhoto(c.getString(c.getColumnIndex("carPhoto")));
            objCar.setOwnerType(c.getString(c.getColumnIndex("ownerType")));
            objCar.setIsLiked(c.getInt(c.getColumnIndex("IsLiked")));

            carList.add(objCar);
        }
        c.close();

        return carList;
    }

    public Car dbSearchFavCar(long carID){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * from CAR WHERE carId = " + carID;

        Cursor c = db.rawQuery(sql,null);

        List<Car> carList = new ArrayList<Car>();

        Car objCar = new Car();

        while (c.moveToNext()){


            //carId INTEGER PRIMARY KEY NOT NULL, modelName TEXT NOT NULL," +
            //                " description TEXT NOT NULL, carCapacity TEXT NOT NULL, kmsDriven TEXT NOT NULL, price TEXT NOT NULL, mfgYear TEXT, ownerId TEXT, carPhoto TEXT, ownerType TEXT)";

            objCar.setCarId(c.getLong(c.getColumnIndex("carId")));
            objCar.setModelName(c.getString(c.getColumnIndex("modelName")));
            objCar.setPrice(c.getString(c.getColumnIndex("price")));
            objCar.setDescription(c.getString(c.getColumnIndex("description")));
            objCar.setCarCapacity(c.getString(c.getColumnIndex("carCapacity")));
            objCar.setKmsDriven(c.getString(c.getColumnIndex("kmsDriven")));
            objCar.setMfgYear(c.getString(c.getColumnIndex("mfgYear")));
            objCar.setOwnerId(c.getString(c.getColumnIndex("ownerId")));
            objCar.setCarPhoto(c.getString(c.getColumnIndex("carPhoto")));
            objCar.setOwnerType(c.getString(c.getColumnIndex("ownerType")));
            objCar.setIsLiked(c.getInt(c.getColumnIndex("IsLiked")));

            carList.add(objCar);
        }
        c.close();

        return objCar;
    }

    public List<Car> dbSearchForUSer(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * from CAR WHERE ownerId = "+id;

        Cursor c = db.rawQuery(sql,null);

        List<Car> carList = new ArrayList<Car>();

        while (c.moveToNext()){
            Car objCar = new Car();

            //carId INTEGER PRIMARY KEY NOT NULL, modelName TEXT NOT NULL," +
            //                " description TEXT NOT NULL, carCapacity TEXT NOT NULL, kmsDriven TEXT NOT NULL, price TEXT NOT NULL, mfgYear TEXT, ownerId TEXT, carPhoto TEXT, ownerType TEXT)";

            objCar.setCarId(c.getLong(c.getColumnIndex("carId")));
            objCar.setModelName(c.getString(c.getColumnIndex("modelName")));
            objCar.setPrice(c.getString(c.getColumnIndex("price")));
            objCar.setDescription(c.getString(c.getColumnIndex("description")));
            objCar.setCarCapacity(c.getString(c.getColumnIndex("carCapacity")));
            objCar.setKmsDriven(c.getString(c.getColumnIndex("kmsDriven")));
            objCar.setMfgYear(c.getString(c.getColumnIndex("mfgYear")));
            objCar.setOwnerId(c.getString(c.getColumnIndex("ownerId")));
            objCar.setCarPhoto(c.getString(c.getColumnIndex("carPhoto")));
            objCar.setOwnerType(c.getString(c.getColumnIndex("ownerType")));
            objCar.setIsLiked(c.getInt(c.getColumnIndex("IsLiked")));

            carList.add(objCar);
        }
        c.close();

        return carList;
    }
}
