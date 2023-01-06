package com.example.basicmusic.Admin.ModelAdmin;

public class CategoryMusic {
    private int id;
    private String titleCategory;
    private String uriImage;

    public CategoryMusic() {
    }

    public CategoryMusic(int id, String titleCategory, String uriImage) {
        this.id = id;
        this.titleCategory = titleCategory;
        this.uriImage = uriImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleCategory() {
        return titleCategory;
    }

    public void setTitleCategory(String titleCategory) {
        this.titleCategory = titleCategory;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }
}
