package com.veevapp.customer.util.listener;

public interface OnItemSelectedListener<T> {

    void onSelect(T object);

    void onDeselect(T object);
}
