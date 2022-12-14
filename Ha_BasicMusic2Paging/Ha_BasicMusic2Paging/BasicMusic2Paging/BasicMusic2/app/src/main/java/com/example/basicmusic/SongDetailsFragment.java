package com.example.basicmusic;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.basicmusic.data.Music;
import com.example.basicmusic.databinding.FragmentListMusicBinding;
import com.example.basicmusic.databinding.SongFragmentDetailBinding;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import phucdv.android.musichelper.Song;

public class SongDetailsFragment extends Fragment {


    MusicController mMusicController;
    SongFragmentDetailBinding songdetailsbinding;
    NavController mNavController;
    private SongAdapter mAdapter;
    MediaPlayer mediaPlayer;

    SeekBar seekBar;


    private MainActivityViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMusicController = MusicController.getInstance(getContext());


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        songdetailsbinding = SongFragmentDetailBinding.inflate(inflater, container, false);
        return songdetailsbinding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ẩn action barTop;
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        seekBar = songdetailsbinding.songProgress;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                mediaPlayer.seekTo(seekBar.getProgress());


            }
        });
        setDateTotal();
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mNavController =
                Navigation.findNavController(requireActivity(), R.id.idFragmentContainer);
        mAdapter = new SongAdapter(getContext(), new MusicComparator());
        mAdapter.setItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, Music song, int pos) {
                songdetailsbinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);

            }
        });
        songdetailsbinding.btnBack.setOnClickListener(v -> {
            mNavController.navigate(R.id.action_songDetailsFragment_to_fragmentContainer);

        });

        songdetailsbinding.btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getMusicController().getCurrentIndex() >= 0) {
                    if (mAdapter.getMusicController().isPlaying()) {
                        mAdapter.pause();
                        songdetailsbinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    } else {
                        mAdapter.start();
                        songdetailsbinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
                    }
                }
            }
        });

        songdetailsbinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.playNext();;
                setDateTotal();
                songdetailsbinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);


            }
        });

        songdetailsbinding.btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.playPrev();
                setDateTotal();
                songdetailsbinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
            }
        });
    }

    private void setDateTotal() {
//        String timeCurrent = String.valueOf(TimeUnit.MINUTES.toMinutes(System.currentTimeMillis()));
        String titleMusic = mMusicController.getMusicSource().getAtIndex(mMusicController.getCurrentIndex()).getTitle();
        Uri imageMusic = mMusicController.getMusicSource().getAtIndex(mMusicController.getCurrentIndex()).getAlbumUri();
        String timeMusic = mMusicController.getMusicSource().getAtIndex(mMusicController.getCurrentIndex()).getTimes();
        songdetailsbinding.txtTitle.setText(titleMusic);
        songdetailsbinding.albumArt.setImageURI(imageMusic);
        songdetailsbinding.txtDuration.setText(timeMusic);
//        songdetailsbinding.txtCurrentTime.setText(timeCurrent);

//        seekBar.setMax(  Integer.parseInt(timeMusic));


    }


}
