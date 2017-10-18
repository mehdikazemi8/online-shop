package com.veevapp.customer.data.models;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class Color extends BaseModel{
    private String id;
    private String codeName;
    private String name;

    public Color(String id,String codeName,String name){
        this.id = id;
        this.codeName = codeName;
        this.name = name;
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}