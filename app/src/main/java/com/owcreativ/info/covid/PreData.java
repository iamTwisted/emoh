package com.owcreativ.info.covid;

public class PreData {
    public String title;
    public String description;
    public String category;

    public PreData( String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}