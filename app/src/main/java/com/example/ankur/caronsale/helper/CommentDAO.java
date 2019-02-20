package com.example.ankur.caronsale.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.Model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentDAO extends SQLiteOpenHelper {

    public CommentDAO(Context context){
        super(context,"android_api",null,6);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void dbInsert(Comment objComment){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = getContentValues(objComment);
        database.insert("COMMENT",null,values);
    }

    @NonNull
    private ContentValues getContentValues(Comment objComment) {
        ContentValues values = new ContentValues();

        //id INTEGER PRIMARY KEY,carID INEGER,ownerId TEXT,comment_user_id TEXT, " +
        //                "commentText TEXT)";

        values.put("carID",objComment.getCarID());
        values.put("ownerId",objComment.getOwnerId());
        values.put("comment_user_id",objComment.getComment_userID());
        values.put("commentText",objComment.getCommentText());
        return values;
    }

    public List<Comment> dbSearch(long carId){
        SQLiteDatabase db = this.getReadableDatabase();


        // //id INTEGER PRIMARY KEY,carID INEGER,ownerId TEXT,comment_user_id TEXT, " +
        //        //                "commentText TEXT)";

        String sql = "SELECT * from COMMENT WHERE carID = "+carId;

        Cursor c = db.rawQuery(sql,null);

        List<Comment> commentList = new ArrayList<Comment>();

        while (c.moveToNext()){
            Comment objComment = new Comment();

            objComment.setId(c.getLong(c.getColumnIndex("id")));
            objComment.setCarID(c.getLong(c.getColumnIndex("carID")));
            objComment.setOwnerId(c.getString(c.getColumnIndex("ownerId")));
            objComment.setComment_userID(c.getString(c.getColumnIndex("comment_user_id")));
            objComment.setCommentText(c.getString(c.getColumnIndex("commentText")));


            commentList.add(objComment);
        }
        c.close();

        return commentList;
    }
}
