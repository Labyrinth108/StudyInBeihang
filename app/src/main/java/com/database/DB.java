package com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by SONY on 2016/2/20.
 */
public class DB {
    public static final String DB_NAME="StudyInBeihang.db";
    private  static DB StudyInBeihangDB;
    private SQLiteDatabase db;
    private int version;
    private Context context;
    public DB(Context c){
        StudyInBeihangOpenHelper dbHelper=new StudyInBeihangOpenHelper(c,DB_NAME,null,1);
        db=dbHelper.getWritableDatabase();
        context=c;
    }
    public synchronized static  DB getInstance(Context context){
        if(StudyInBeihangDB==null){
            StudyInBeihangDB=new DB(context);
        }
        return StudyInBeihangDB;
    }
    //储存到数据库
    public void saveClassroom(Classroom classroom) {
        if (classroom != null) {
            ContentValues values=new ContentValues();
            values.put("location",classroom.getLocation());
            values.put("room",classroom.getRoom());
            values.put("idnum", classroom.getIdnum());
            values.put("percent", classroom.getPercent());
            db.insert("Classroom", null, values);
        }
    }
    public void updateClassroom(Classroom classroom){
        if (classroom != null) {
            db.execSQL("update Classroom set percent=? where location=? and room=?",
                    new String[]{classroom.getPercent(), classroom.getLocation(), classroom.getRoom()});
        }
    }
    //从数据库中读取教室信息
    public  String loadClassroom(String l,String r){
        Cursor cursor=db.rawQuery("select percent from Classroom where location=? and room =?",new String[]{l,r});
        if(cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        if(cursor!=null){
            cursor.close();
        }
        return "";
    }
}
