package com.example.basicmusic.MainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.basicmusic.R;
import com.example.basicmusic.SongDetailsFragment;
import com.example.basicmusic.databinding.FragmentListMusicBinding;
import com.example.basicmusic.databinding.FragmentMvplayBinding;
import com.example.basicmusic.databinding.SongFragmentDetailBinding;

public class PlayMv extends Fragment {
    FragmentMvplayBinding fragmentMvplayBinding;
    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentMvplayBinding = FragmentMvplayBinding.inflate(inflater,container,false);
        return fragmentMvplayBinding.getRoot();

}}

