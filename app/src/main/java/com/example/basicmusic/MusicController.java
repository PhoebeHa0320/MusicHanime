package com.example.basicmusic;

import android.content.ContentUris;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.paging.PagingDataAdapter;

import com.example.basicmusic.api.MusicApiProvider;
import com.example.basicmusic.data.Music;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import phucdv.android.musichelper.Song;

public class MusicController {
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private int mCurrentIndex;
    private boolean mIsPreparing;
    private MusicSource mMusicSource;
    private List<Song> mData;

    // : Đổi sang dạng singleton - start
    private static MusicController sInstance;

    public static MusicController getInstance(Context context){
        if(sInstance == null){
            sInstance = new MusicController(context);
        }
        return sInstance;
    }

    public static MusicController getInstanceNoCreate(){
        return sInstance;
    }
    public void setData(List<Song> data) {
        mData = data;
    }

    public List<Song> getData() {
        return mData;
    }

    public interface MusicSource {
        int getSize();

        Music getAtIndex(int index);
    }

    public void setMusicSource(MusicSource musicSource){
        mMusicSource = musicSource;
    }
    // : Đổi sang dạng singleton - end

    public MusicController(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mIsPreparing = false;
            }
        });

        mCurrentIndex = -1;
        mIsPreparing = false;
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public boolean isPreparing() {
        return mIsPreparing;
    }

    public void playNext() {
        if (mMusicSource.getSize() != 0) {
            if (mCurrentIndex < mMusicSource.getSize() - 1) {
                mCurrentIndex++;
            } else {
                mCurrentIndex = 0;
            }
            playSongAt(mCurrentIndex);
        }
    }

    public void playPrev() {
        if (mMusicSource.getSize() != 0) {
            if (mCurrentIndex > 0) {
                mCurrentIndex--;
            } else {
                mCurrentIndex = mMusicSource.getSize() - 1;
            }
            playSongAt(mCurrentIndex);
        }
    }

    public void pause() {
        mMediaPlayer.pause();
    }

    public void start() {
        mMediaPlayer.start();
    }

    //    public void playSongAt(int index) {
//        mMediaPlayer.reset();
//        String url = MusicApiProvider.BASE_URL + mMusicSource.getAtIndex(index).getData();
//        try {
//            mMediaPlayer.setDataSource(url);
//            mCurrentIndex = index;
//        } catch (Exception e) {
//            Log.e("MUSIC SERVICE", "Error starting data source", e);
//        }
//        mMediaPlayer.prepareAsync();
//        mIsPreparing = true;==================================
//    }
    public void playSongAt(int index) {
        mMediaPlayer.reset();
        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, Long.parseLong(mMusicSource.getAtIndex(index).getId()));
        try {
            mMediaPlayer.setDataSource(mContext, trackUri);
            mCurrentIndex = index;
        } catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error starting data source", e);
        }
        mMediaPlayer.prepareAsync();
        mIsPreparing = true;
    }

}
