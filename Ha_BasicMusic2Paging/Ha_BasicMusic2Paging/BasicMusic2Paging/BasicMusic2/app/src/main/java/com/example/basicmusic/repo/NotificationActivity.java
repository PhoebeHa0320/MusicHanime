package com.example.basicmusic.repo;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmusic.MusicController;
import com.example.basicmusic.Notification.NotificationMusic;
import com.example.basicmusic.R;
import com.example.basicmusic.SongAdapter;
import com.example.basicmusic.data.Music;

import java.util.List;

import phucdv.android.musichelper.MediaHelper;
import phucdv.android.musichelper.Song;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SongAdapter mAdapter;
    MusicController mMusicController;
    private ImageButton mPrev;
    private ImageButton mPlayPause;
    private ImageButton mNext;

    private NotificationMusic mNotificationMusic;

    MusicRepository mMusicRepository = new MusicRepository();

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mNotificationMusic =
                    ((NotificationMusic.MusicServiceBinder) iBinder).getService();
            checkPermission();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
//                case NotificationMusic.ACTION_DATA_CHANGE:
//                    mAdapter.setData(mNotificationMusic.getCurrentIndex());
//                    break;
                case NotificationMusic.ACTION_PLAY_AT:
                    mPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
                    break;
                case NotificationMusic.ACTION_PLAY:
                    mPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
                    break;
                case NotificationMusic.ACTION_PAUSE:
                    mPlayPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    break;
                case NotificationMusic.ACTION_NEXT:
                    mPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
                    break;
                case NotificationMusic.ACTION_PREVIOUS:
                    mPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
                    break;
            }
        }
    };

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);
        } else {
            doRetrieveAllSong();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rcy_song);
//        mAdapter = new SongAdapter(this,"calll",);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, Music song, int pos) {
                mNotificationMusic.playSongAt(pos);
            }
        });

        mPrev = findViewById(R.id.btn_prev);
        mPlayPause = findViewById(R.id.btn_play_pause);
        mNext = findViewById(R.id.btn_next);

        mPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNotificationMusic.getMusicController().getCurrentIndex() >= 0) {
                    if (mNotificationMusic.getMusicController().isPlaying()) {
                        mNotificationMusic.pause();
                    } else {
                        mNotificationMusic.play();
                    }
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotificationMusic.next();
            }
        });

        mPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotificationMusic.prev();
            }
        });

        Intent intent = new Intent(this, NotificationMusic.class);

        startService(intent);
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);

        IntentFilter filter = new IntentFilter();
        filter.addAction(NotificationMusic.ACTION_DATA_CHANGE);
        filter.addAction(NotificationMusic.ACTION_PLAY_AT);
        filter.addAction(NotificationMusic.ACTION_PLAY);
        filter.addAction(NotificationMusic.ACTION_PAUSE);
        filter.addAction(NotificationMusic.ACTION_NEXT);
        filter.addAction(NotificationMusic.ACTION_PREVIOUS);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 999) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                doRetrieveAllSong();
            }
        }
    }

    private void doRetrieveAllSong() {
//        MediaHelper.retrieveAllSong(this, new MediaHelper.OnFinishRetrieve() {
//            @Override
//            public void onFinish(List<Song> list) {
//                mNotificationMusic.setData(list);
//            }
//        });

        mAdapter.isLocal = true;
        mMusicRepository.getLocalMusic(getBaseContext()).observe(this, new Observer<List<Music>>() {
            @Override
            public void onChanged(List<Music> music) {
                mAdapter.setData(music);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mBroadcastReceiver);
        unbindService(mServiceConnection);
    }

}
