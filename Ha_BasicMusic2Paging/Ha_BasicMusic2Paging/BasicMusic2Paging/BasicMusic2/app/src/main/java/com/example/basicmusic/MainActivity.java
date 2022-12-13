package com.example.basicmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.basicmusic.data.Music;
import com.example.basicmusic.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import autodispose2.AutoDispose;
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.rxjava3.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView mNavView;
    ImageButton btnMore;
    NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.idFragmentContainer);
        NavGraph navGraph =
                navHostFragment.getNavController()
                        .getNavInflater().inflate(R.navigation.main_nav);
        navHostFragment.getNavController().setGraph(navGraph);

        mNavController = navHostFragment.getNavController();
        drawerLayout = binding.iddrawerlayout;
        btnMore = binding.btnMore;

        binding.btnDrawer.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });


        mNavView = binding.navView;
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_home:
                        mNavController.navigate(R.id.homeMusic);
                        break;
                    case R.id.item_library:
                        mNavController.navigate(R.id.fragmentListSong);
                        break;
                    case R.id.item_playlist:
                        mNavController.navigate(R.id.playlistMusic);
                        break;
                    case R.id.item_login:
                        mNavController.navigate(R.id.loginMusic);
                        break;
                    case R.id.item_setting:
                        mNavController.navigate(R.id.settingMusic);
                        break;


                }
                binding.txtHeader.setText(item.getTitle());
                drawerLayout.closeDrawer(mNavView);
                return true;
            }
        });

    }


}