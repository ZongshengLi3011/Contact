package com.example.myaddressbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    //添加联系人的按钮
    Button buttonAdd;
    //显示联系人的控件
    ListView listViewPhone;
    //创建联系人集合
    private List<Phone> phones = new ArrayList<>();
    //获取类
    ListAdapter adapter;
    //创建数据库对象
    SQLiteDatabase db;
    Datebase dbHelper = new Datebase(this, "PhoneNumber", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建控件对象
        buttonAdd = findViewById(R.id.button_add);
        listViewPhone = findViewById(R.id.list_list);
        button = findViewById(R.id.button);

        //点击添加按钮图标跳转到添加联系人页面
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPhoneNumber.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //监听按钮，如果点击，就跳转
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(MainActivity.this, Activity_ss.class);
                startActivity(intent);
            }
        });
        //以读写的方式读取数据，一旦数据库的磁盘空间满了，数据库就只能读而不能写，getWritableDatabase()打开数据库就会出错。
        db = dbHelper.getWritableDatabase();
        //在数据源中查询指定的SQL，返回查询结果所组成的序表
        Cursor cursor = db.query("PhoneNumber", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {//开始查询是否有数据
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String phone1 = cursor.getString(cursor.getColumnIndex("phone1"));
                @SuppressLint("Range") String phone2 = cursor.getString(cursor.getColumnIndex("phone2"));
                @SuppressLint("Range") String housePhone = cursor.getString(cursor.getColumnIndex("housePhone"));
                @SuppressLint("Range") String officePone = cursor.getString(cursor.getColumnIndex("officePhone"));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
                @SuppressLint("Range") String remark = cursor.getString(cursor.getColumnIndex("remark"));
                Phone phoneInfo = new Phone(name, phone1, phone2, housePhone, officePone, address, remark);
                phones.add(phoneInfo);
            } while (cursor.moveToNext());//查询数据浏览完了没
        }
        adapter = new ListAdapter(MainActivity.this, R.layout.list_item, phones);
        listViewPhone.setAdapter(adapter);
        //点击事件，显示联系人基本信息
        listViewPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Phone phone_check = phones.get(i);
                String checkName = phone_check.getName(),
                        checkPhone1 = phone_check.getPhone1(),
                        checkPhone2 = phone_check.getPhone2(),
                        checkHousePhone = phone_check.getHouerPhone(),
                        checkOfficePhone = phone_check.getOfficephone(),
                        checkAddress = phone_check.getAddress(),
                        checkRemark = phone_check.getRemark();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage(
                        "         姓               名：" + checkName + "\n" +
                                "         联 系 方 式 1 ：" + checkPhone1 + "\n" +
                                "         联 系 方 式 2 ：" + checkPhone2 + "\n" +
                                "         家 庭 座 机 号：" + checkHousePhone + "\n" +
                                "         办 公 座 机 号：" + checkOfficePhone + "\n" +
                                "         地               址 ：" + checkAddress + "\n" +
                                "         备               注 ：" + checkRemark + "\n");
                builder.setTitle("                  查看联系人");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
        //点击事件，删除联系人功能
        listViewPhone.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DeleteDialog(i);
                return true;
            }
        });
        //显示联系人信息
        adapter.setOnItemCallClickListener(new ListAdapter.onItemCallListener() {
            @Override
            public void onCallClick(int i) {

                Phone phone_check = phones.get(i);
                String phoneNumber = phone_check.getPhone1();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        //点击事件，点击修改图标进入修改编辑页面
        adapter.setOnItemChangesClickListener(new ListAdapter.onItemChangesListener() {
            @Override
            public void onChangesClick(int i) {
                Phone phone_check = phones.get(i);
                String checkName = phone_check.getName(),
                        checkPhone1 = phone_check.getPhone1(),
                        checkPhone2 = phone_check.getPhone2(),
                        checkHousePhone = phone_check.getHouerPhone(),
                        checkOfficePhone = phone_check.getOfficephone(),
                        checkAddress = phone_check.getAddress(),
                        checkRemark = phone_check.getRemark();
                Intent intent = new Intent(MainActivity.this, EditPhone.class);
                intent.putExtra("extra_name", checkName);
                intent.putExtra("extra_phone1", checkPhone1);
                intent.putExtra("extra_phone2", checkPhone2);
                intent.putExtra("extra_housePhone", checkHousePhone);
                intent.putExtra("extra_officePhone", checkOfficePhone);
                intent.putExtra("extra_address", checkAddress);
                intent.putExtra("extra_remark", checkRemark);
                intent.putExtra("extra_i", i);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "编辑", Toast.LENGTH_SHORT).show();
            }
        });
        //点击事件，点击查询图标进入查询页面
        adapter.setOnItemChaxunClickListener(new ListAdapter.onItemChaxunListener() {
            @Override
            public void onChaxunClick(int i) {
                Phone phone_check = phones.get(i);
                String checkName = phone_check.getName(),
                        checkPhone1 = phone_check.getPhone1(),
                        checkPhone2 = phone_check.getPhone2(),
                        checkHousePhone = phone_check.getHouerPhone(),
                        checkOfficePhone = phone_check.getOfficephone(),
                        checkAddress = phone_check.getAddress(),
                        checkRemark = phone_check.getRemark();
                Intent intent = new Intent(MainActivity.this, EditPhone.class);
                intent.putExtra("extra_name", checkName);
                intent.putExtra("extra_phone1", checkPhone1);
                intent.putExtra("extra_phone2", checkPhone2);
                intent.putExtra("extra_housePhone", checkHousePhone);
                intent.putExtra("extra_officePhone", checkOfficePhone);
                intent.putExtra("extra_address", checkAddress);
                intent.putExtra("extra_remark", checkRemark);
                intent.putExtra("extra_i", i);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "查询", Toast.LENGTH_SHORT).show();
            }
        });
        //发送短信的点击事件
        adapter.setOnItemMassgasClickListener(new ListAdapter.onItemMassgasListener() {
            @Override
            public void onMassgasClick(int i) {
                Phone phone_check = phones.get(i);
                String phoneNumber = phone_check.getPhone1();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("smsto:" + phoneNumber));
                startActivity(intent);
                Toast.makeText(MainActivity.this, "短信", Toast.LENGTH_SHORT).show();
            }
        });


        //删除联系人的方法
    }

    private void DeleteDialog(final int positon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("是否将 lizongshen 增添收藏联系人");
        builder.setTitle("提示");
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Phone phone_check = phones.get(positon);
                String checkName = phone_check.getName(),
                        checkPhone1 = phone_check.getPhone1(),
                        checkPhone2 = phone_check.getPhone2(),
                        checkHousePhone = phone_check.getHouerPhone(),
                        checkOfficePhone = phone_check.getOfficephone(),
                        checkAddress = phone_check.getAddress(),
                        checkRemark = phone_check.getRemark();
                phones.remove(positon);//清除联系人的信息
                adapter.notifyDataSetChanged();  //更新listView
                db.delete("PhoneNumber", "name = ? and phone1 = ? and phone2 = ? and housePhone = ? and officePhone = ? and address = ? and remark = ?", new String[]{checkName, checkPhone1, checkPhone2, checkHousePhone, checkOfficePhone, checkAddress, checkRemark});
                Toast.makeText(MainActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
