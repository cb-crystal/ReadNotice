package com.example.readnotice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button btn_add,btn_search;
    ListView listView;
    MySql mySql;
    SQLiteDatabase db;
    ArrayList<HashMap<String,String>>data=new ArrayList<>();
    HashMap<String,String>map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        mySql=new MySql(MainActivity.this,"notices",null,1);
        db=mySql.getWritableDatabase();

        initViews();
        initDatas();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }

    private void initDatas() {
        data.clear();
        Cursor cursor=db.query("notice",new String[]{"id","title","content"},null,null,null,null,null);
        while (cursor.moveToNext()){
            map=new HashMap<>();
            String title=cursor.getString(cursor.getColumnIndex("title"));
            String content=cursor.getString(cursor.getColumnIndex("content"));
            map.put("title",title);
            map.put("content",content);
            data.add(map);
            Log.d("cb", "onClick: "+title);
        }
        cursor.close();
        SimpleAdapter adapter=new SimpleAdapter(
                MainActivity.this,
                data,
                R.layout.list,
                new String[]{"title","content"},
                new int[]{R.id.txt_title,R.id.txt_content}
        );
        listView.setAdapter(adapter);
    }


    private void initViews() {
    btn_add=this.findViewById(R.id.btn_add);
    btn_search=this.findViewById(R.id.btn_search);
    listView=this.findViewById(R.id.listView);

    //添加数据
    btn_add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this,DataAdd.class);
            startActivity(intent);
        }
    });
    //查询数据
//        btn_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}