package com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * Created by SONY on 2016/2/20.
 */
public class DB {
    public static final String DB_NAME="StudyInBeihang.db";
    private  static DB StudyInBeihangDB;
    private static StudyInBeihangOpenHelper openhelper;
    private static SQLiteDatabase db;
    public DB(Context c){
        openhelper=new StudyInBeihangOpenHelper(c,DB_NAME,null,1);
        db=openhelper.getWritableDatabase();
    }
    public synchronized static DB getInstance(Context context){
        if(StudyInBeihangDB==null){
            StudyInBeihangDB=new DB(context);
        }
        return StudyInBeihangDB;
    }
    //储存到数据库
    public void saveCourse(Courseinfo ci) {
        if (ci != null) {
            ContentValues values=new ContentValues();
            values.put("week",ci.getWeek());
            values.put("room",ci.getRoom());
            values.put("startnum", ci.getStartnum());
            values.put("endnum", ci.getEndnum());
            db.insert("CourseInfo", null, values);
        }
    }
    //储存到数据库
    public void saveClassroom(Classroom classroom) {
        if (classroom != null) {
            ContentValues values=new ContentValues();
            values.put("location",classroom.getLocation());
            values.put("room", classroom.getRoom());
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
        Cursor cursor=db.rawQuery("select percent from Classroom where location=? and room=?",new String[]{l,r});
        if(cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        if(cursor!=null){
            cursor.close();
        }
        return "";
    }
    //从数据库中读取某教室的课表信息
    public  Cursor loadCourseInfo(String room) {
        Cursor cursor = db.rawQuery("select * from CourseInfo where room like?", new String[]{"%"+room+"%"});

        if(cursor.moveToFirst()) {
            return cursor;
        }
        else return null;
    }
}
