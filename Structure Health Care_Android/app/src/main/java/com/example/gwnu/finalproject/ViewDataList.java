package com.example.gwnu.finalproject;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Collections;


public class ViewDataList extends AppCompatActivity {


    ListView listView1;

    IcontextListAdapter adapter;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        int i = 0;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_data_list);

        System.out.println("ENTER");

        listView1 = (ListView)findViewById(R.id.listView);

        adapter = new IcontextListAdapter(this);

        //if want to bring something in folder, declare

        Resources res = getResources();
        System.out.println("LIST SIZE : "+DBHelper.list.size());
        Collections.reverse(DBHelper.list);
        adapter.addItem(new IconTextItem("DO NOT DELETE", "-"));


        for(i = 0; i < DBHelper.list.size(); i++) {
            System.out.println(DBHelper.list.get(i).date + "  " + DBHelper.list.get(i).distance + " " + DBHelper.list.get(i).gas + " " + DBHelper.list.get(i).temperture + ", " + DBHelper.list.get(i).humidity);
            adapter.addItem(new IconTextItem(DBHelper.list.get(i).date, String.valueOf("거리 : "+ Double.toString(DBHelper.list.get(i).distance) + "     가스 : " + Double.toString(DBHelper.list.get(i).gas) + "     온도/습도 : " +Double.toString(DBHelper.list.get(i).temperture)+" / "+Double.toString(DBHelper.list.get(i).humidity))));
        }
        Collections.reverse(DBHelper.list);
        //adapter.addItem(new IconTextItem("20161208", "19.7","18","5/21"));
        //setting adapter in list view

        listView1.setAdapter(adapter);
        Button del = (Button) findViewById(R.id.delete);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText date = (EditText)findViewById(R.id.getDate);
                DBHelper dbHelper = new DBHelper(getApplicationContext(), "SensorVal.db", null, 1);
                dbHelper.delete(date.getText().toString());
                //result.setText(dbHelper.getResult());
            }
        });
        
        //if click list view, bring location's data and save in curData finally pring using Toast
    }

}
