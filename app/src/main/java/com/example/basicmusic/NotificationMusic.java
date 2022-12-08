package com.example.basicmusic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.List;

import phucdv.android.musichelper.Song;

public class NotificationMusic extends Service {
    public static final String ACTION_DATA_CHANGE = "action_data_change";
    public static final String ACTION_PLAY_AT = "action_play_at";
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";

    MusicController mMusicController;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        mMusicController = new MusicController(this);
        createNotificatinoChannel();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null || intent.getAction() == null) {
            startForeground(999, createNotification());
        } else if(intent.getAction().equals(ACTION_PLAY)){
            if(mMusicController.isPlaying()){
                pause();
            } else {
                play();
            }
        } else if(intent.getAction().equals(ACTION_NEXT)){
            next();
        } else if(intent.getAction().equals(ACTION_PREVIOUS)){
            prev();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    public class MusicServiceBinder extends Binder {
        public NotificationMusic getService(){
            return NotificationMusic.this;
        }
    }

    public MusicController getMusicController(){
        return mMusicController;
    }

    public void setData(List<Song> songs){
        mMusicController.setData(songs);
        Intent intent = new Intent(ACTION_DATA_CHANGE);
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(intent);
    }

    public List<Song> getData(){
        return mMusicController.getData();
    }

    public void playSongAt(int index){
        mMusicController.playSongAt(index);
        Intent intent = new Intent(ACTION_PLAY_AT);
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(intent);
    }

    public int getCurrentIndex(){
        return mMusicController.getCurrentIndex();
    }

    public void play(){
        mMusicController.start();
        Intent intent = new Intent(ACTION_PLAY);
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(intent);
    }

    public void pause(){
        mMusicController.pause();
        Intent intent = new Intent(ACTION_PAUSE);
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(intent);
    }

    public void next(){
        mMusicController.playNext();
        Intent intent = new Intent(ACTION_NEXT);
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(intent);
    }

    public void prev(){
        mMusicController.playPrev();
        Intent intent = new Intent(ACTION_PREVIOUS);
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicServiceBinder();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificatinoChannel(){
        NotificationChannel channel =
                new NotificationChannel("music_channel",
                        "Music play back",
                        NotificationManager.IMPORTANCE_DEFAULT);
        getSystemService(NotificationManager.class)
                .createNotificationChannel(channel);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification createNotification(){
        RemoteViews remoteViews =
                new RemoteViews(this.getPackageName(), R.layout.layout_notification_music);

        Intent playItent = new Intent(this, NotificationMusic.class);
        playItent.setAction(ACTION_PLAY);
        PendingIntent playPendingIntent =
                PendingIntent.getService(
                        this, 100,
                        playItent, PendingIntent.FLAG_IMMUTABLE);

        Intent nextItent = new Intent(this, NotificationMusic.class);
        playItent.setAction(ACTION_NEXT);
        PendingIntent nextPendingIntent =
                PendingIntent.getService(
                        this, 100,
                        nextItent, PendingIntent.FLAG_IMMUTABLE);

        Intent prevIntent = new Intent(this, NotificationMusic.class);
        playItent.setAction(ACTION_PREVIOUS);
        PendingIntent prevPendingIntent =
                PendingIntent.getService(
                        this, 100,
                        prevIntent, PendingIntent.FLAG_IMMUTABLE);

        remoteViews.setOnClickPendingIntent(R.id.img_play_pause, playPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.img_skip_next, nextPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.img_skip_previous, prevPendingIntent);

        Notification notification = new Notification.Builder(this, "music_channel")
                .setCustomContentView(remoteViews)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setStyle(new Notification.DecoratedCustomViewStyle())
                .build();
        return notification;
    }
}
