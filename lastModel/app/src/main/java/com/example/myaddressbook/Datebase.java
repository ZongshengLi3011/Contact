package com.example.myaddressbook;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//创建数据库
public class Datebase extends SQLiteOpenHelper {
    //定义创建数据库说用到的语句，并定制了数据库字段
    private static  final String CREATE_PhoneNumber = "create table PhoneNumber(id integer primary key autoincrement, name text, " +
            "phone1 text, phone2 text, housePhone text, officePhone text,address text,remark text)";
    private Context mContext;
    public  Datebase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库
        sqLiteDatabase.execSQL(CREATE_PhoneNumber);//执行一条sql语句
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //升级数据库
        sqLiteDatabase.execSQL("drop table if exists PhoneNumber");//执行一条sql语句，创建表之前判断表是否存在，如果存在则删除已有表
        onCreate(sqLiteDatabase);
    }
}
