package com.veevapp.customer.data.models;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class SortItem{
    public int id;
    public String text;
    public String key;
    public SortItem(int id, String text,String key) {
        this.id = id;
        this.text = text;
        this.key = key;
    }
}