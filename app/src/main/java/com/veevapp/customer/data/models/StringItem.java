package com.veevapp.customer.data.models;

import com.veevapp.customer.view.adapter.BaseRecyclerAdapter;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class StringItem implements BaseRecyclerAdapter.IDiff {
    public int id;
    public String text;

    public StringItem(int id,String text){
        this.id = id;
        this.text = text;
    }

    @Override
    public boolean areContentsTheSame(Object thisType) {
        return text.equals(((StringItem)thisType).text);
    }

    @Override
    public boolean areItemsTheSame(Object thisType) {
        return id == ((StringItem)thisType).id;
    }
}
