package com.example.readnotice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class DataAdd extends AppCompatActivity {
    EditText edit_title,edit_content,edit_id;
    Button btn_sure,btn_cancel;
    Button btn_update,btn_del;
    MySql mySql;
    SQLiteDatabase db;
    static int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_add);
        mySql=new MySql(DataAdd.this,"notices",null,1);
        db=mySql.getWritableDatabase();
        initViews();

    }

    private void initViews() {
        edit_content=this.findViewById(R.id.edit_content);
        edit_title=this.findViewById(R.id.edit_title);
        btn_cancel=this.findViewById(R.id.btn_cancel);
        btn_update=this.findViewById(R.id.btn_update);
        btn_del=this.findViewById(R.id.btn_del);
        btn_sure=this.findViewById(R.id.btn_sure);
        edit_id=this.findViewById(R.id.edit_id);
        //数据添加
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=edit_title.getText().toString();
                String content=edit_content.getText().toString();
                String id=edit_id.getText().toString();

                ContentValues values=new ContentValues();
                values.put("title",title);
                values.put("content",content);
                values.put("id",id);
                db.insert("notice",null,values);
            }
        });
        //数据更新
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id2=edit_id.getText().toString();
                String title2=edit_title.getText().toString();
                String content2=edit_content.getText().toString();
                ContentValues values=new ContentValues();

                values.put("title",title2);
                values.put("content",content2);
                 db.update("notice", values, "id=?",new String[]{id2});
            }
        });
        //数据删除
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id3=edit_id.getText().toString();
                db.delete("notice", "id=?",new String[]{id3});
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=db.query("notice",new String[]{"id","title","content"},null,null,null,null,null);
                while (cursor.moveToNext()){
                    String id=cursor.getString(cursor.getColumnIndex("id"));
                    String title=cursor.getString(cursor.getColumnIndex("title"));
                    String content=cursor.getString(cursor.getColumnIndex("content"));
                    Log.d("cb", "onClick: "+id+" "+title+"\n"+content);
                }
                cursor.close();
//                finish();
            }
        });

    }
}