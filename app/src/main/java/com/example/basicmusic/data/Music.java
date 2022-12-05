package com.example.basicmusic.data;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Music {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("singer")
    @Expose
    private String singer;

    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("data")
    @Expose
    private String data;

    public Music(String title, String data, String singer) {
        this.title = title;
        this.data = data;
        this.singer = singer;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Music{" +
                "title='" + title + '\'' +
                ", singer='" + singer + '\'' +
                ", id='" + id + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public Uri getAlbumUri(Uri albumUri) {
        return albumUri;
    }

    public Uri getAlbumUri() {
        return null ;
    }
}
