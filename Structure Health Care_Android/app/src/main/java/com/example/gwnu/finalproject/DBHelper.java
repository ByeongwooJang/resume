package com.example.gwnu.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by gwnu on 2016-12-02.
 */

public class DBHelper extends SQLiteOpenHelper {
    class data{
        public int index;
        public String date;
        public double distance;
        public double temperture;
        public double gas;
        public double humidity;
    }
    public static ArrayList<data> list = new ArrayList<data>();

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수

    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE SensorVal (_id INTEGER PRIMARY KEY AUTOINCREMENT, create_at TEXT, dis DOUBLE, gas DOUBLE, tem DOUBLE, hum DOUBLE);");
        //db.execSQL("CREATE TABLE SensorV (_id INTEGER PRIMARY KEY AUTOINCREMENT, create_at TEXT, dis DOUBLE, gas DOUBLE, temp DOUBLE, hum DOUBLE);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //, double tem, double hum
    public void insert(String create_at, double dis , double gas, double tem ,double hum) {
        data tmp = new data();
        tmp.date = create_at;
        tmp.distance = dis;
        tmp.gas = gas;
        tmp.temperture = tem;
        tmp.humidity = hum;
        list.add(tmp);
        System.out.println("INSERT");
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SensorVal VALUES(null, '" + create_at + "', " + dis + ", " + gas + ", " + tem + ", " + hum +");");
        //db.execSQL("INSERT INTO SensorV VALUES(null, '" + create_at + "', " + dis + ", '" + gas +  ", '" + tem +"');");
        db.close();

    }
    /*
        public void update(String item, int price) {
            SQLiteDatabase db = getWritableDatabase();
            // 입력한 항목과 일치하는 행의 가격 정보 수정
            db.execSQL("UPDATE MONEYBOOK SET price=" + price + " WHERE item='" + item + "';");
            db.close();
        }
    */
    public void delete(String date) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM SensorVal WHERE create_at='" + date + "';");
        list.clear();
        getResult();
        db.close();
    }
    public String print(){
        Collections.reverse(list);
        String re = "Recently added data\n";

        if(list.isEmpty() == true) {
            Collections.reverse(list);
            return re;
        }
        else {
            switch(list.size()){
                case 1:
                    re += list.get(0).date + "\nDis : " +
                            list.get(0).distance + "  Gas : " +
                            list.get(0).gas + "  Tem : " +
                            list.get(0).temperture + "  humi : " +
                            list.get(0).humidity + "\n\n";
                            Collections.reverse(list);
                            break;
                case 2:
                    for (int i = 0; i < 2; i++) {
                        re += list.get(i).date + "\nDis : " +
                                list.get(i).distance + "  Gas : " +
                                list.get(i).gas + "  Tem : " +
                                list.get(i).temperture + "  humi : " +
                                list.get(i).humidity + "\n\n";
                    }
                    Collections.reverse(list);
                    break;
                case 3:
                    for (int i = 0; i < 3; i++) {
                        re += list.get(i).date + "\nDis : " +
                                list.get(i).distance + "  Gas : " +
                                list.get(i).gas + "  Tem : " +
                                list.get(i).temperture + "  humi : " +
                                list.get(i).humidity + "\n\n";
                    }
                    Collections.reverse(list);
                    break;
                default:
                    for (int i = 0; i < 4; i++) {
                        re += list.get(i).date + "\nDis : " +
                                list.get(i).distance + "  Gas : " +
                                list.get(i).gas + "  Tem : " +
                                list.get(i).temperture + "  humi : " +
                                list.get(i).humidity + "\n\n";
                    }
                    Collections.reverse(list);

            }

        }
        return re;
    }
    public String getResult() {
        // 읽기가 가능하게 DB 열기
        int flag = -1;
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        System.out.println("GETRESULT");
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM SensorVal", null);
        while (cursor.moveToNext()) {
            flag = -1;
            data temp = new data();
            temp.index = Integer.parseInt(cursor.getString(0));
            temp.date = cursor.getString(1);
            temp.distance = cursor.getDouble(2);
            temp.gas = cursor.getDouble(3);
            temp.temperture = cursor.getDouble(4);
            temp.humidity = cursor.getDouble(5);
            for (int i = 0; i < list.size(); i++) {
                if (temp.date.equals(list.get(i).date)) {
                    flag = 0;
                    break;
                } else {
                    flag = -1;
                }
            }
            if (flag == -1)
                list.add(temp);

           result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " |Dis : "
                    + cursor.getDouble(2)
                    + " |Gas :  "
                    + cursor.getDouble(3)
                    + " |Tem :  "
                    + cursor.getDouble(4)
                    + " |Humi :  "
                    + cursor.getDouble(5)
                    + "\n";
        }
        /*while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " |D "
                    + cursor.getDouble(2)
                    + " |G "
                    + cursor.getDouble(3)
                    + " |T "
                    + cursor.getDouble(4)
                    + " |H "
                    + cursor.getDouble(5)
                    + "\n";
        }*/

        return result;
    }

}
