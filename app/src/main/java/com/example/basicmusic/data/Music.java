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

    @SerializedName("albumUri")
    @Expose
    private Uri albumUri;

    @SerializedName("times")
    @Expose
    private String times;

    private long duration;

    public Music(String title, String data, String singer, Uri albumUri, String timesMusic ) {
        this.title = title;
        this.data = data;
        this.singer = singer;
        this.albumUri = albumUri;
        this.times = timesMusic;
    }

    public Music(String title, String data, String singer, Uri albumUri, String timesMusic, long duration) {
        this.title = title;
        this.data = data;
        this.singer = singer;
        this.albumUri = albumUri;
        this.times = timesMusic;
        this.duration = duration;
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

    public void setAlbumUri(Uri albumUri) {
        this.albumUri = albumUri;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
