package com.example.basicmusic.MainFragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.basicmusic.R;
import com.example.basicmusic.databinding.FragmentContainerBinding;
import com.google.android.material.navigation.NavigationView;

public class FragmentContainer extends Fragment {
    DrawerLayout drawerLayout;
    NavigationView mNavView;
    NavController mNavController;
    FragmentContainerBinding containerbinding;
    ImageButton btnMore;
    Menu menu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        containerbinding = FragmentContainerBinding.inflate(inflater, container, false);
        mNavController =
                Navigation.findNavController(requireActivity(), R.id.idFragmentContainer);
        drawerLayout = containerbinding.iddrawerlayout;
        btnMore = containerbinding.btnMore;

//
//        NavHostFragment navHostFragment =
//                (NavHostFragment) getActivity().getSupportFragmentManager()
//                        .findFragmentById(R.id.idContainer);
//        NavGraph navGraph =
//                navHostFragment.getNavController()
//                        .getNavInflater().inflate(R.navigation.main_nav);
//        navHostFragment.getNavController().setGraph(navGraph);

        containerbinding.btnDrawer.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        mNavView = containerbinding.navView;
//        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.item_home:
//                        mNavController.navigate(R.id.action_fragmentContainer_to_homeMusic);
//                        break;
//                    case R.id.item_library:
//                        mNavController.navigate(R.id.action_fragmentContainer_to_fragmentListSong);
//                        break;
//                    case R.id.item_playlist:
//                        mNavController.navigate(R.id.action_fragmentContainer_to_playlistMusic);
//                        break;
//                    case R.id.item_login:
//                        mNavController.navigate(R.id.action_fragmentContainer_to_loginMusic);
//                        break;
//                    case R.id.item_setting:
//                        mNavController.navigate(R.id.action_fragmentContainer_to_settingMusic);
//                        break;
//
//
//                }
//                return true;
//            }
//        });

        return containerbinding.getRoot();


    }

    public void onCreateMenuItem(Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.submenu_item, menu);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
