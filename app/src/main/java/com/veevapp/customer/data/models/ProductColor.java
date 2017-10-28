package com.veevapp.customer.data.models;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class ProductColor extends BaseModel{
    private String id;
    private String codeName;
    private String title;

    public ProductColor(String id,String codeName,String title){
        this.id = id;
        this.codeName = codeName;
        this.title = title;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCodeName() {
        return codeName;
    }
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String name) {
        this.title = name;
    }
}