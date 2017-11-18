package com.veevapp.customer.util;

import com.veevapp.customer.data.models.SortItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class SortHandler {
    private static SortHandler mInstance;
    public static SortHandler getInstance(){
        if(mInstance==null)
            mInstance = new SortHandler();

        return mInstance;
    }

    List<SortItem> mSortItems;
    public SortHandler(){
        mSortItems = new ArrayList<>();
        mSortItems.add(new SortItem(0,"نزدیک ترین","nearest"));
        mSortItems.add(new SortItem(1,"قیمت از زیاد به کم","decreasingPrice"));
        mSortItems.add(new SortItem(2,"قیمت از کم به زیاد","increasingPrice"));
        mSortItems.add(new SortItem(3,"جدید ترین","deadline"));
    }

    public List<SortItem> getItems(){
        return mSortItems;
    }

    public SortItem getItem(int pos) {
        return mSortItems.get(pos);
    }
}