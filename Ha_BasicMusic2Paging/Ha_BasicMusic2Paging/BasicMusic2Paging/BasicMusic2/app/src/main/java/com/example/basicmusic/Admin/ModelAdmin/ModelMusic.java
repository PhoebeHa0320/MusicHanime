package com.example.basicmusic.Admin.ModelAdmin;

public class ModelMusic {
    private int id;
    private String mTitle;
    private String titleCategory;
    private String mSinger;
    private String imageUri;
    private String uid;
    private String date;
    private String timeMusic;
    long timestamp;

    public ModelMusic() {
    }

    public ModelMusic(int id, String mTitle, String titleCategory, String mSinger, String imageUri, String uid, long timestamp,String date,String timeMusic) {
        this.id = id;
        this.mTitle = mTitle;
        this.titleCategory = titleCategory;
        this.mSinger = mSinger;
        this.imageUri = imageUri;
        this.uid = uid;
        this.timestamp = timestamp;
        this.date= date;
        this.timeMusic = timeMusic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getTitleCategory() {
        return titleCategory;
    }

    public void setTitleCategory(String titleCategory) {
        this.titleCategory = titleCategory;
    }

    public String getmSinger() {
        return mSinger;
    }

    public void setmSinger(String mSinger) {
        this.mSinger = mSinger;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
