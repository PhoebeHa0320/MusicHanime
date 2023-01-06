package com.example.basicmusic.Admin.FragmentAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.basicmusic.databinding.AddCategoryMusicBinding;

public class AddCategoryMusic extends Fragment {
    AddCategoryMusicBinding addCategoryMusicBinding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addCategoryMusicBinding = AddCategoryMusicBinding.inflate(inflater, container,false);

        //hanlder click back Category
        addCategoryMusicBinding.btnBack.setOnClickListener(v->{
            requireActivity().onBackPressed();
        });
        return addCategoryMusicBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
