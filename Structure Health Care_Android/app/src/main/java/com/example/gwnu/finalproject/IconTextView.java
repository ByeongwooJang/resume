package com.example.gwnu.finalproject;

/**
 * Created by gwnu on 2016-12-05.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


/**

 * Created by ntsysMac01 on 2015-12-08.

 */



public class IconTextView extends LinearLayout{


    private TextView mText01;

    private TextView mText02;

    private TextView mText03;

    private TextView mText04;


    public IconTextView(Context context, IconTextItem aItem) {

        super(context);


        LayoutInflater inflater = (LayoutInflater)

                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.listitem, this, true);



        mText01 = (TextView) findViewById(R.id.dataItem01);

        mText01.setText(aItem.getData(0));


        mText02 = (TextView) findViewById(R.id.dataItem02);

        mText02.setText(aItem.getData(1));


        /*mText03 = (TextView) findViewById(R.id.dataItem03);

        mText03.setText(aItem.getData(2));

        mText04 = (TextView) findViewById(R.id.dataItem04);

        mText04.setText(aItem.getData(3));*/

    }


    //setting automatically about parameters
    public void setText(int index, String data){

        if (index == 0 ){

            mText01.setText(data);

        } else if (index == 1){

            mText02.setText(data);

        } else if (index == 2){

            mText03.setText(data);

        }  else if (index == 3){

            mText03.setText(data);

        } else {

            throw new IllegalArgumentException();

        }

    }



    public IconTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }


    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

    }





}
