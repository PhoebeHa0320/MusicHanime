package com.example.basicmusic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.basicmusic.data.Music;
import com.example.basicmusic.databinding.SongFragmentDetailBinding;

public class SongDetailsFragment extends Fragment {
    SongFragmentDetailBinding songdetailsbinding;
    private SongAdapter mAdapter;

    private MainActivityViewModel mViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        songdetailsbinding = SongFragmentDetailBinding.inflate(inflater,container,false);
        return songdetailsbinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mAdapter = new SongAdapter(getContext(), new MusicComparator());
        mAdapter.setItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, Music song, int pos) {
                songdetailsbinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            }
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
                mAdapter.playNext();
                songdetailsbinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
            }
        });

        songdetailsbinding.btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.playPrev();
                songdetailsbinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
            }
        });
    }
}
