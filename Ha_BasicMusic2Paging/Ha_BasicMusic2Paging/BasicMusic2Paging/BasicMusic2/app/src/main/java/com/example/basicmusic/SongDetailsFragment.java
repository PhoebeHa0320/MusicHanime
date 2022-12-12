package com.example.basicmusic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

import phucdv.android.musichelper.Song;

public class SongDetailsFragment extends Fragment {

MusicController mMusicController;
    SongFragmentDetailBinding songdetailsbinding;
    NavController mNavController;
    private SongAdapter mAdapter;

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


        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        mNavController =
                Navigation.findNavController(requireActivity(), R.id.idFragmentContainer);
//        String titleMusic2 = mMusicController.getData().get(mAdapter.getItemCount()).getTitle();
        // PhucDV: - start
        String titleMusic = mMusicController.getMusicSource().getAtIndex(mMusicController.getCurrentIndex()).getTitle();
        // PhucDV: - end
//                String titleMusic3 = String.valueOf(mMusicController.getData().get(mMusicController.getCurrentIndex()).getTitle());
        songdetailsbinding.txtTitle.setText(titleMusic);

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
