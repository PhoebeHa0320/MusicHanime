package com.example.basicmusic.Admin.FragmentAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.basicmusic.R;
import com.example.basicmusic.SongDetailsFragment;
import com.example.basicmusic.databinding.HomeAdminBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homeAdmin extends Fragment {
    HomeAdminBinding homeadminbinding;
    NavController mNavController;
    NavigationView mNavigationView;
    FirebaseAuth  mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeadminbinding = HomeAdminBinding.inflate(inflater,container,false);

        mNavController =
                Navigation.findNavController(requireActivity(), R.id.idFragmentContainerAdmin);
        //handler click add Category
        homeadminbinding.buttonAddCategory.setOnClickListener(v->{
            mNavController.navigate(R.id.action_homeAdmin_to_addCategoryMusic);
            //  start
            AddCategoryMusic addCategoryMusic = new AddCategoryMusic();
            //  end
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cateloryMusic,addCategoryMusic);
        });
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String mEmail = mFirebaseUser.getEmail();
        mNavigationView = homeadminbinding.navgationView;
        View viewHeager = mNavigationView.getHeaderView(0);
        TextView mUser = viewHeager.findViewById(R.id.tvName);
        mUser.setText(mEmail);
        
        
        
        //hanlder click Open NavigationView

        homeadminbinding.openNav.setOnClickListener(v->{
            homeadminbinding.drawerLayout.openDrawer(GravityCompat.START);
        });
        //handler click add file mp3
        homeadminbinding.addFileMp3.setOnClickListener(v->{
            mNavController.navigate(R.id.action_homeAdmin_to_addFileMp32);
            //  start
            addFileMp3 addfileMp3 = new addFileMp3();
            //  end
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerfileMp3,addfileMp3);
        });
        homeadminbinding.navgationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_dashboard:
                        mNavController.navigate(R.id.homeAdmin);
                        break;

                    case R.id.item_out:
                        mNavController.navigate(R.id.mainActivity);
                        break;

                }
                homeadminbinding.drawerLayout.closeDrawer(homeadminbinding.navgationView);
                return true;
            }
        });
        return homeadminbinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
