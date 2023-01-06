package com.example.basicmusic.Admin.Activityadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.basicmusic.R;
import com.example.basicmusic.databinding.ActivityDashBoardAdminBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashBoardAdmin extends AppCompatActivity {
    ActivityDashBoardAdminBinding dashbinding;
    NavController mNavController;

    NavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashbinding = ActivityDashBoardAdminBinding.inflate(getLayoutInflater());
        setContentView(dashbinding.getRoot());



//        mNavigationView = dashbinding.ope
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.idFragmentContainerAdmin);
        NavGraph navGraph =
                navHostFragment.getNavController()
                        .getNavInflater().inflate(R.navigation.admin_nav);
        navHostFragment.getNavController().setGraph(navGraph);

        mNavController = navHostFragment.getNavController();


        //Handerler Click open Profile



        //hanlder Click Open Add Categories Music
        //handler click Add file Mp3


    }
}