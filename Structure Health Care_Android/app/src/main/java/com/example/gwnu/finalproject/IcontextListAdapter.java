package com.example.gwnu.finalproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**

 * Created by Mac02 on 2015-12-08.

 */

public class IcontextListAdapter extends BaseAdapter {


    private Context mContext;

    private List<IconTextItem> mItems = new ArrayList<IconTextItem>();


    public IcontextListAdapter(Context mContext) {

        this.mContext = mContext;

    }


    public void addItem(IconTextItem it){

        mItems.add(it);

    }


    public void setmItems(List<IconTextItem> mItems) {

        this.mItems = mItems;

    }


    @Override

    public int getCount() {

        return mItems.size();

    }


    @Override

    public Object getItem(int position) {

        return mItems.get(position);

    }


    public boolean areAllltemsSelectable(){

        return false;

    }


    public boolean isSelectable(int position){

        try {

            return mItems.get(position).isSelectable();

        } catch (IndexOutOfBoundsException ex){

            return false;

        }

    }


    @Override

    public long getItemId(int position) {

        return position;

    }


    // getView is organize display

    // if convertView is not null reuse view, else change data in view

    // if null, make object

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        IconTextView itemView;

        if (convertView == null){

            itemView = new IconTextView(mContext, mItems.get(position));

        } else {

            itemView = (IconTextView)convertView;

            itemView.setText(0, mItems.get(position).getData(0));

            itemView.setText(1, mItems.get(position).getData(1));

            /*itemView.setText(2, mItems.get(position).getData(2));

            itemView.setText(3, mItems.get(position).getData(3));*/

        }

        return itemView;

    }

}
