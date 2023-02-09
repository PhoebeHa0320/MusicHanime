package com.example.basicmusic.Admin.ModelAdmin;

public class CategoryMusic {
    private String id, nameCategory,url_image,uid;
    long timestamp;

    public CategoryMusic() {
    }

    public CategoryMusic(String id, String nameCategory, String url_image, String uid, long timestamp) {
        this.id = id;
        this.nameCategory = nameCategory;
        this.url_image = url_image;
        this.uid = uid;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
