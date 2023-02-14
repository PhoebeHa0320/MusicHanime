package com.example.basicmusic.MainFragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.basicmusic.databinding.ActivityDetailsVideoBinding;

public class DetailsVideo extends AppCompatActivity {
     ActivityDetailsVideoBinding detailsVideoBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsVideoBinding = ActivityDetailsVideoBinding.inflate(getLayoutInflater());
        setContentView(detailsVideoBinding.getRoot());
    }
}