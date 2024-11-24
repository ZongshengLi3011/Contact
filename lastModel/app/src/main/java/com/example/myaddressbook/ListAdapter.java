package com.example.myaddressbook;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
//联系人显示界面，打电话，修改联系人信息，发送短信，
public class ListAdapter extends ArrayAdapter<Phone>{
    private int resourceId;
    //创建手机通讯录对象
    Phone phone = new Phone();
    public ListAdapter(Context context, int textViewResourceId, List<Phone> objects /*,MyCallback callback*/){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
//        this.mCallback = callback;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final  int i = position;
        Phone phone = (Phone)getItem(position);
        View view;
        //定义所需要使用到的button和EditText控件对象
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            //创建控件对象
            viewHolder.phoneImage = (ImageView)view.findViewById(R.id.phone_image);
            viewHolder.textViewName = (TextView)view.findViewById(R.id.list_name);
            viewHolder.textViewPhone = (TextView)view.findViewById(R.id.list_phone);
            viewHolder.buttonCall = (Button)view.findViewById(R.id.button_call);
            viewHolder.buttonChanges = (Button)view.findViewById(R.id.button_change);
            viewHolder.buttonShotMassage = (Button)view.findViewById(R.id.button_massages);
            viewHolder.buttonChaxun = (Button)view.findViewById(R.id.button_chaxun);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.phoneImage.setImageResource(R.drawable.tx);

        //设置联系人图标
        viewHolder.textViewName.setText(phone.getName());
        if (phone.getPhone1().equals("")){
            if (phone.getPhone2().equals("")){
                if (phone.getHouerPhone().equals("")){
                    viewHolder.textViewPhone.setText(phone.getOfficephone());
                }else{
                    viewHolder.textViewPhone.setText(phone.getHouerPhone());
                }
            }else {
                viewHolder.textViewPhone.setText(phone.getPhone2());
            }
        }else {
            viewHolder.textViewPhone.setText(phone.getPhone1());
        }
        //点击打电话图标进入拨打电话界面
        viewHolder.buttonCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mOnItemCallListener.onCallClick(i);
            }
        });
        //点击修改信息进入修改信息页面
        viewHolder.buttonChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemChangesListener.onChangesClick(i);
            }
        });
        //点击查询信息进入查询信息页面
        viewHolder.buttonChaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemChaxunListener.onChaxunClick(i);
            }
        });
        //点击发送短信图标进入发送短信页面
        viewHolder.buttonShotMassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemMassgasListener.onMassgasClick(i);
            }
        });
        return view;
    }

    public void swapCursor(Cursor cursor) {
    }

    //定义所需要使用到的button和EditText控件对象
    class ViewHolder{
        ImageView phoneImage;
        TextView textViewName;
        TextView textViewPhone;
        Button buttonCall;
        Button buttonChanges;
        Button buttonShotMassage;
        Button buttonChaxun;
    }
//打电话
    public interface onItemCallListener {
        void onCallClick(int i);
    }
    private onItemCallListener mOnItemCallListener;
    public void setOnItemCallClickListener(onItemCallListener mOnItemCallListener) {
        this.mOnItemCallListener = mOnItemCallListener;
    }
//修改信息
    public interface onItemChangesListener {
        void onChangesClick(int i);
    }
    private onItemChangesListener mOnItemChangesListener;
    public void setOnItemChangesClickListener(onItemChangesListener mOnItemChangersListener) {
        this.mOnItemChangesListener = mOnItemChangersListener;
    }
//查询信息
    public interface onItemChaxunListener {
        void onChaxunClick(int i);
    }
    private onItemChaxunListener mOnItemChaxunListener;
    public void setOnItemChaxunClickListener(onItemChaxunListener mOnItemChaxunListener) {
        this.mOnItemChaxunListener = mOnItemChaxunListener;
    }
//发送短信
    public interface onItemMassgasListener {
        void onMassgasClick(int i);
    }
    private onItemMassgasListener mOnItemMassgasListener;
    public void setOnItemMassgasClickListener(onItemMassgasListener mOnItemMassgasListener) {
        this.mOnItemMassgasListener = mOnItemMassgasListener;
    }

}

