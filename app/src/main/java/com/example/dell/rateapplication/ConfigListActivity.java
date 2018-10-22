package com.example.dell.rateapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ConfigListActivity extends AppCompatActivity {

   /* protected void onCreate(Bundle savedInstanceState) { //多栏列表视图
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        List<String> data = new ArrayList<String>();
        GridView listView = (GridView) findViewById(R.id.mylist);
        for(int i=0;i<100;i++){
            data.add("item" + i);
        }
        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);

        //listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }*/
    protected void onCreate(Bundle savedInstanceState) { //列表空数据视图
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_none);
        List<String> data = new ArrayList<String>();
        ListView listView = findViewById(R.id.mylist);
        for(int i=0;i<100;i++){
            data.add("item" + i);
        }
        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nodata));
    }
}
