package com.example.basicmusic.Admin.ModelAdmin;

public class ModelMusic {
    private int id;
    private String mTitle;
    private String titleCategory;
    private String mSinger;
    private String imageUri;

    public ModelMusic() {
    }

    public ModelMusic(int id, String mTitle, String titleCategory, String mSinger, String imageUri) {
        this.id = id;
        this.mTitle = mTitle;
        this.titleCategory = titleCategory;
        this.mSinger = mSinger;
        this.imageUri = imageUri;
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
