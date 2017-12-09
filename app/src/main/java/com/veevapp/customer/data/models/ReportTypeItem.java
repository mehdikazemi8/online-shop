package com.veevapp.customer.data.models;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class ReportTypeItem extends BaseModel{
    public int id;
    public String title;


    public ReportTypeItem(){}
    public ReportTypeItem(int id,String title){
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
