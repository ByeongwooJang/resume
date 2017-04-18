package com.example.gwnu.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;

import java.util.Calendar;


public class MainActivity extends Activity implements View.OnClickListener {

    String time = "";
    Calendar cal = Calendar.getInstance();

    public static String deviceName;
    public static String deviceAddress;

    public static TextView result = null;
    public static TextView etDis= null;
    public static TextView etGas= null;
    public static TextView etTem= null;
    public static TextView etHum= null;


    Button viewchart = null;

   // private RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "SensorVal.db", null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  //      mLayout = (RelativeLayout) findViewById(R.id.activity_main);
//        mLayout.setBackgroundColor(Color.rgb(0,0,0));


        result = (TextView) findViewById(R.id.result);
        etDis = (TextView) findViewById(R.id.DistanceValue);
        etGas = (TextView) findViewById(R.id.GasValues);
        etTem = (TextView) findViewById(R.id.TempertureValue);
        etHum = (TextView) findViewById(R.id.HumidityValues);

        /*dbHelper.insert("20160901115206", 13, 24.0, 20, 61);
        dbHelper.insert("20160907175106", 13, 24.0, 19, 54);
        dbHelper.insert("20160915115206", 13.1, 23.0, 23, 57);
        dbHelper.insert("20160921115206", 13.1, 22.0, 21, 53);
        dbHelper.insert("20160929115206", 13.1, 21.0, 20, 50);
        dbHelper.insert("20161001115206", 13.2, 23.0, 18, 53);
        dbHelper.insert("20161007175106", 13.2, 24.0, 18, 50);
        dbHelper.insert("20161015115206", 13.2, 25.0, 17, 55);
        dbHelper.insert("20161021115206", 13.2, 27.0, 19, 50);
        dbHelper.insert("20161029115206", 13.2, 26.0, 13, 50);
        dbHelper.insert("20161101115206", 13.2, 25.0, 18, 50);
        dbHelper.insert("20161107175106", 13.3, 22.0, 18, 50);
        dbHelper.insert("20161115115206", 13.3, 21.0, 16, 40);
        dbHelper.insert("20161121115206", 13.4, 23.0, 14, 40);
        dbHelper.insert("20161129115206", 13.5, 23.0, 11, 30);
        dbHelper.insert("20161201115206", 13.6, 22.0, 0, 20);
        dbHelper.insert("20161207175106", 13.7, 21.0, 0, 30);*/



        dbHelper.getResult();
        result.setText(dbHelper.print());
        time = String.format("%04d%02d%02d%02d%02d%02d",
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
        System.out.println("TIME :::  " + time);

        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                double dis = Double.parseDouble(etDis.getText().toString());
                double gas = Double.parseDouble(etGas.getText().toString());
                double tem = Double.parseDouble(etTem.getText().toString());
                double hum = Double.parseDouble(etHum.getText().toString());
                time = String.format("%04d%02d%02d%02d%02d%02d",
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
                System.out.println(time);

                dbHelper.insert(time, dis, gas, tem,hum);
                result.setText(dbHelper.print());

            }
        });



        Button launch2 = (Button)findViewById(R.id.button2);//데이터 리스트 버튼
        launch2.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {

// TODO Auto-generated method stub
                Intent intentViewData = new Intent(MainActivity.this, ViewDataList.class);
// 두번째 액티비티를 실행하기 위한 인텐트
                startActivity(intentViewData);
// 두번째 액티비티를 실행합니다.
            }
        });

        Button launch = (Button)findViewById(R.id.Connect);
        launch.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {

// TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, DeviceScanActivity.class);
// 두번째 액티비티를 실행하기 위한 인텐트
                startActivity(intent);
// 두번째 액티비티를 실행합니다.
            }
        });

        viewchart = (Button)findViewById(R.id.chart);
        viewchart.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if(viewchart == v)
        {
            try{
                Intent intent = new DisplayChart().execute(this);
                startActivity(intent);
            }catch(Exception e) {}
        }
    }
}
