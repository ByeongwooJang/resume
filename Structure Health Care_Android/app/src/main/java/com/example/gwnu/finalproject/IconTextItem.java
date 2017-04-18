package com.example.gwnu.finalproject;

/**
 * Created by gwnu on 2016-12-05.
 */


/**

 * Created by ntsysMac01 on 2015-12-08.

 */


//IconTextItem 자바 클래스는 값을 저장해줄 공간의 틀을 짜는 것


public class IconTextItem {

    //private란 변경이 되면 안 되는 변수에 대해 접근 제한 자 설정

    private String[] mData;

    private boolean mSelectable = true;



    //파라메타값이 배열로된것을 사용함

    public IconTextItem(String[] obj){

        mData = obj;

    }


    //위와 다르게 파라메타 값에 배열이 아닌 직접 본인이 넣을 때

    //위와 이거와 동일한 기능을 하지만 위의 방법을 쓰는 게 좀 더 간단하고 좋아 보임

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


    //메소드 getData()는 mData 값을 반환(이유는 다른 자바 클래스에서 쓸 수 있게)

    public String[] getData() {

        return mData;

    }


    //위의 메소드 getData()는 배열로 되어있기 때문에 한 개씩 값을 가져가기 위해서는 쪼개주는 작업이 필요함

    //현재 이 getData() 메소드는 파라메타 값에 가져오고 싶은 배열의 순번을 넣어주면 알아서 쪼개주는 역할

    public String getData(int index) {

        //이 구문은 getData(index) 값을 줬을 때 값을 불러올 mData에 값이 있는지 혹은 index 값이 mData가 가지고 있는 값을 초과했는지를 판별해주는 조건문

        if (mData == null || index >= mData.length) {

            return null;

        }

        return mData[index];

    }


    public void setData(String[] obj){

        mData = obj;

    }




}