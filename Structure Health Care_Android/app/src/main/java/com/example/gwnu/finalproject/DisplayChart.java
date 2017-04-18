package com.example.gwnu.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DisplayChart extends AbstractDemoChart
{



  public Intent execute(Context context)
  {

    String[] titles = new String[] { "distance", "gas", "temperature", "humidity"};
    List<Date[]> dates = new ArrayList<Date[]>();
    List<double[]> values = new ArrayList<double[]>();

    List<DBHelper.data> listDB = DBHelper.list;
    List<Date> listInputDate = new ArrayList<Date>();

//    int iSize = listDB.size();
//
//    int dataTypeCount = titles.length;
//
//    for(int j = 0; j < dataTypeCount; ++j) {
//      Date[] dateValues = new Date[iSize];
//
//      for (int i = 0; i < iSize; ++i) {
//        String date = listDB.get(i).date;
//        String year = date.substring(0, 4);
//        String month = date.substring(4, 6);
//        String day = date.substring(6, 8);
//
//        dateValues[i] = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month), Integer.parseInt(day));
//      }
//
//      dates.add(dateValues);
//    }


    int iSize = listDB.size();
    for(int i = 0 ; i < iSize; ++i)
    {
      String date = listDB.get(i).date;
      String year = date.substring(0, 4);
      String month = date.substring(4, 6);
      String day = date.substring(6, 8);

      Date inputDate = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month), Integer.parseInt(day));
      listInputDate.add(inputDate);
    }


    int dataTypeCount = titles.length;

    for(int j = 0 ; j < dataTypeCount; ++j) {
      Date[] dateValues = new Date[listInputDate.size()];
      iSize = listDB.size();

      for (int i = 0; i < iSize ; ++i) {
        dateValues[i] = listInputDate.get(i);
      }
      dates.add(dateValues);
    }


    iSize = listDB.size();
    double[] distanceData = new double[listDB.size()];

    for(int i =  0; i < iSize; ++i)
    {
      distanceData[i] = listDB.get(i).distance;
    }
    values.add(distanceData);



    iSize = listDB.size();
    double[] gasData = new double[listDB.size()];

    for(int i =  0; i < iSize; ++i)
    {
      gasData[i] = listDB.get(i).gas;
    }
    values.add(gasData);



    iSize = listDB.size();
    double[] temperatureData = new double[listDB.size()];

    for(int i =  0; i < iSize; ++i)
    {
      temperatureData[i] = listDB.get(i).temperture;
    }
    values.add(temperatureData);


    iSize = listDB.size();
    double[] humidityData = new double[listInputDate.size()];

    for(int i =  0; i < iSize; ++i)
    {
      humidityData[i] = listDB.get(i).humidity;
    }
    values.add(humidityData);



    int[] colors = new int[] { Color.GRAY, Color.GREEN, Color.RED, Color.BLUE, };
    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.TRIANGLE, PointStyle.DIAMOND, PointStyle.SQUARE };
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);

    setChartSettings(renderer, "Values", "Date", "%", dates.get(0)[0].getTime(), dates.get(0)[dates.get(0).length - 1].getTime(), -20, 100, Color.YELLOW, Color.MAGENTA);

    renderer.setYLabels(20);//한  화면에 레이블이 몇개나 보이도록 할건지. 클수록 간격이 좁아진다
    renderer.setXLabels(5);
//    renderer.setlabel
    //renderer.setDisplayValues(true);

    return ChartFactory.getTimeChartIntent(context, buildDateDataset(titles, dates, values),renderer, "yyyy/MM/dd");
    
  }
}
