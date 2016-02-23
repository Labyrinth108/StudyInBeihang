package com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by SONY on 2016/2/20.
 */
public class StudyInBeihangOpenHelper extends SQLiteOpenHelper{
    private Context mcontext;
    public static final String CREATE_CLASSROOM="create table Classroom(id integer primary key autoincrement,location text," +
            "room text,idnum integer,percent text)";

    public StudyInBeihangOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mcontext=context;
    }
    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLASSROOM);
        Toast.makeText(mcontext, "Create succeed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}