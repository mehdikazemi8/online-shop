package com.veevapp.customer.data.models;

public class SubCategory {
    private String id;
    private String title;
    private String photoURL;
    private boolean selected = false;

    public SubCategory() {
    }


    public SubCategory(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
