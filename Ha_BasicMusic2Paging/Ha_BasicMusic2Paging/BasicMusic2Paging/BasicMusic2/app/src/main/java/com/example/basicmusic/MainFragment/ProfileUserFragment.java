package com.example.basicmusic.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.basicmusic.Admin.FragmentAdmin.addFileMp3;
import com.example.basicmusic.MainActivity;
import com.example.basicmusic.R;
import com.example.basicmusic.databinding.ProfileUserFramentBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileUserFragment extends Fragment {

    ProfileUserFramentBinding profileUserFramentBinding;
    FirebaseAuth mFirebaseAuth;
    NavController mNavController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profileUserFramentBinding = ProfileUserFramentBinding.inflate(inflater, container,false);
        return profileUserFramentBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileUserFramentBinding.logout.setOnClickListener(v ->{
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            intent.putExtra("LOGOUT","SUCCESSLOGOUT");
            requireActivity().startActivity(intent);
        });
    }
}
