package com.example.myaddressbook;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Inquiry extends AppCompatActivity {
    //定义所需要使用到的button和EditText控件对象
    Button buttonCheck,
            buttonSave;
    EditText editTextName,
            editTextPhone1,
            editTextPhone2,
            editTextHousePhone,
            editTextOfficePhone,
            editTextAddress,
            editTextRemark;

    //定义数据库类型的变量
    private Datebase datebase;
    SQLiteDatabase db;
    int i;

    //创建数据库对象
    Datebase dbHelper = new Datebase(this,"PhoneNumber",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cx_phone);

        //创建控件对象
        buttonSave = findViewById(R.id.check_save);
        buttonCheck = findViewById(R.id.check_back);
        editTextName = findViewById(R.id.check_edit_name);
        editTextPhone1 = findViewById(R.id.check_edit_phone_number1);
        editTextPhone2 = findViewById(R.id.check_edit_phone_number2);
        editTextHousePhone = findViewById(R.id.check_edit_house_number);
        editTextOfficePhone = findViewById(R.id.check_edit_office_number);
        editTextAddress = findViewById(R.id.check_edit_address);
        editTextRemark = findViewById(R.id.check_edit_remark);

        //创建数据库对象
        datebase = new Datebase(this,"PhoneNumber",null,1);

        //返回的点击事件
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inquiry.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //获取从中活动传递下来的数据
        Intent intent = getIntent();
        editTextName.setText(intent.getStringExtra("extra_name"));
        editTextPhone1.setText(intent.getStringExtra("extra_phone1"));
        editTextPhone2.setText(intent.getStringExtra("extra_phone2"));
        editTextHousePhone.setText(intent.getStringExtra("extra_housePhone"));
        editTextOfficePhone.setText(intent.getStringExtra("extra_officePhone"));
        editTextAddress.setText(intent.getStringExtra("extra_address"));
        editTextRemark.setText(intent.getStringExtra("extra_remark"));
        i = intent.getIntExtra("extra_i",0);
        final String editName =  intent.getStringExtra("extra_name");

        //查询的点击事件
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                //获取用户此时输入的数据
                String edName = editTextName.getText().toString(),
                        edPhone1 = editTextPhone1.getText().toString(),
                        edPhone2 = editTextPhone2.getText().toString(),
                        edHousePhone = editTextHousePhone.getText().toString(),
                        edOfficePhone = editTextOfficePhone.getText().toString(),
                        edAddress = editTextAddress.getText().toString(),
                        edRemark = editTextRemark.getText().toString();

                db = datebase.getWritableDatabase();//缓存数据库实例功能
                int ID = 0;
                //在数据源中查询指定的SQL，返回查询结果所组成的序表
                Cursor cursor = db.query("PhoneNumber",null,null,null,null,null,null);
                if (cursor.moveToFirst()){//开头有数据就运行
                    do{
//                        获取此联系人在数据库表中的名字
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
//                        对比名字是否符合
                        if (name.equals(editName)){
//                            名字符合则获取此联系人在数据库表中的主键id
                            ID = cursor.getInt((cursor.getColumnIndex("id")));
                            break;
                        }
                    }while (cursor.moveToNext());//只要还有数据就继续运行
                }
                //将修改的数据存入数据库
                values.put("name",edName);
                values.put("phone1", edPhone1);
                values.put("phone2", edPhone2);
                values.put("housePhone", edHousePhone);
                values.put("officePhone", edOfficePhone);
                values.put("address",edAddress);
                values.put("remark", edRemark);
//                将所输入的信息提交至数据库修改，使用条件为主键等于刚刚所获取的主键值
                db.update("PhoneNumber",values,"id = ?",new String[]{String.valueOf(ID)});
                //清除数据库信息
                values.clear();
                //弹出提示，表示修改成功
                Toast.makeText(Inquiry.this,"恭喜你查询成功！",Toast.LENGTH_SHORT).show();
                //保存成功后跳转到通讯录首页
                Intent intent1 = new Intent(Inquiry.this,MainActivity.class);
                startActivity(intent1);
            }

        });


    }
}
