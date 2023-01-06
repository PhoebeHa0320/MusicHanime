package com.example.basicmusic.Admin.FragmentAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.basicmusic.databinding.AddFileMp3Binding;

public class addFileMp3 extends Fragment {
    AddFileMp3Binding addFileMp3Binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addFileMp3Binding = AddFileMp3Binding.inflate(inflater, container,false);

        //handle click back
        addFileMp3Binding.btnBack.setOnClickListener(v->{
            requireActivity().onBackPressed();
        });
        return addFileMp3Binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
