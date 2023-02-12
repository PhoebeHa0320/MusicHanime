package com.example.basicmusic.Admin.ModelAdmin;

public class CategoryMusic {
    private String id, title, url,uid;
    long timestamp;

    public CategoryMusic() {
    }

    public CategoryMusic(String id, String nameCategory, String url_image) {
        this.id = id;
        this.title = nameCategory;
        this.url = url_image;

    }
//
//    public CategoryMusic(String nameCategory, String url_image) {
//        this.nameCategory = nameCategory;
//        this.url_image = url_image;
//    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
