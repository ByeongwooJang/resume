package com.example.gwnu.finalproject;

/**
 * Created by gwnu on 2016-12-05.
 */


//IconTextItem java class is making form that save values


public class IconTextItem {

    private String[] mData;

    private boolean mSelectable = true;


    public IconTextItem(String[] obj){

        mData = obj;

    }


    public IconTextItem(String obj01, String obj02) {

        mData = new String[4];

        mData[0] = obj01;

        mData[1] = obj02;

        /*mData[2] = obj03;

        mData[3] = obj04;*/

    }


    public boolean isSelectable(){

        return mSelectable;

    }

    public void setSelectable(boolean selectable) {

        mSelectable = selectable;

    }


    public String[] getData() {

        return mData;

    }


    //getData is organized by array, so need to split

    //getData() method split and bing data

    public String getData(int index) {


        if (mData == null || index >= mData.length) {

            return null;

        }

        return mData[index];

    }


    public void setData(String[] obj){

        mData = obj;

    }




}
