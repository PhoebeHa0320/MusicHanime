package com.example.basicmusic.Admin.ModelAdmin;



public class Singer {
    private String id, title,url,uid;
    long timestamp;
    //contructor null

    public Singer() {
    }
//
    public Singer(String id, String nameSinger, String url_image) {
        this.id = id;
        this.title = nameSinger;
        this.url = url_image;

    }

//    public Singer(String nameSinger, String url_image) {
//        this.nameSinger = nameSinger;
//        this.url_image = url_image;
//    }


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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
