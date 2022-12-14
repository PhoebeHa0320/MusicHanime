package com.example.basicmusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmusic.ViewModel.MainActivityViewModel;
import com.example.basicmusic.data.Music;
import com.example.basicmusic.databinding.FragmentListMusicBinding;

import java.util.List;

public class FragmentListSong extends Fragment {
    FragmentListMusicBinding listMusicBinding;
    private RecyclerView mRecyclerView;
    private SongAdapter mAdapter;

    private ImageButton mPrev;
    private ImageButton mPlayPause;
    private ImageButton mNext;
    NavController mNavController;
    private MainActivityViewModel mViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listMusicBinding = FragmentListMusicBinding.inflate(inflater, container, false);
        return listMusicBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        mNavController =
                Navigation.findNavController(requireActivity(), R.id.idFragmentContainer);
//        mRecyclerView = findViewById(R.id.rcy_song);
        mAdapter = new SongAdapter(getContext(), new MusicComparator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listMusicBinding.rcySong.setLayoutManager(linearLayoutManager);
        listMusicBinding.rcySong.setAdapter(mAdapter);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mAdapter.setItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, Music song, int pos) {
                listMusicBinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
                mNavController.navigate(R.id.action_fragmentListSong_to_songDetailsFragment);
                //  start
                SongDetailsFragment songDetailsFragment = new SongDetailsFragment();
                //  end
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerDetails,songDetailsFragment);



            }

        });
        if (mAdapter.getMusicController().isPlaying()) {

            listMusicBinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
        }

        listMusicBinding.btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getMusicController().getCurrentIndex() >= 0) {
                    if (mAdapter.getMusicController().isPlaying()) {
                        mAdapter.pause();
                        listMusicBinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    } else {
                        mAdapter.start();
                        listMusicBinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
                    }
                }
            }
        });

        listMusicBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.playNext();
                listMusicBinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
            }
        });

        listMusicBinding.btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.playPrev();
                listMusicBinding.btnPlayPause.setImageResource(R.drawable.ic_baseline_pause_24);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        doRetrieveAllSong();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 999) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                doRetrieveAllSong();
            }
        }
    }

    private void doRetrieveAllSong() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);
//        } else {
//            mViewModel.getMusics().observe(this, new Observer<List<Music>>() {
//                @Override
//                public void onChanged(List<Music> music) {
//                    mAdapter.setData(music);
//                    mAdapter.notifyDataSetChanged();
//                }
//            });
//        }
//        mViewModel.flowable.to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
//                .subscribe(musicPagingData -> {
//                    mAdapter.submitData(getLifecycle(), musicPagingData);
//                });
        mAdapter.isLocal = true;
        mViewModel.getLocalMusic(getContext()).observe(this, new Observer<List<Music>>() {
            @Override
            public void onChanged(List<Music> music) {
                mAdapter.setData(music);
            }
        });
    }


}