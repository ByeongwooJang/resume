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


    //onCreate 안에다가 선언해줘도 되지만 다른 곳에서도 이 변수를 사용 가능하기 위해서는 밖에다 선언(전역변수)

    ListView listView1;

    IcontextListAdapter adapter;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        int i = 0;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_data_list);

        System.out.println("ENTER");
        //리스트 뷰의 아이디 값을 찾아서 불러온 후 변수에 담아준다

        listView1 = (ListView)findViewById(R.id.listView);

        //어댑터를 생성한다

        adapter = new IcontextListAdapter(this);

        //res 폴더 안에 필요한 무엇을 가져오기 위해서는 선언해줘야 됨

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
        //리스트 뷰에 어댑터를 셋팅 함

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
        //리스트 뷰를 클릭하면 해당 위치값을 받아와서 그 위치값의 Data를 읽어와서 curData에 저장한 후 Toast로 보여줌

        /*
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                IconTextItem curItem = (IconTextItem) adapter.getItem(position);

                //총 3개의 값을 가져오기 때문에 첫 번째 제목을 보여주기 위해선 배열 0번째의 값을 나타내주면 됨

                String[] curData=curItem.getData();

                //String temp = curItem.getData(0); 이렇게 써도 무관함 똑같은 의미를 지님

                Toast.makeText(getApplicationContext(), "Selected : " + curData[0], Toast.LENGTH_LONG).show();

            }

        });
*/
    }

}