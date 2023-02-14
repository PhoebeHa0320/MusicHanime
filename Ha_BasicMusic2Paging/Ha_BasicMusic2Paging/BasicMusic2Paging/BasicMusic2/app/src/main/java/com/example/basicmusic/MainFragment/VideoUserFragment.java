package com.example.basicmusic.MainFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmusic.Admin.Adapter.VideoAdapter;
import com.example.basicmusic.Admin.ModelAdmin.VideoModel;
import com.example.basicmusic.databinding.VideoUserFragmentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VideoUserFragment extends Fragment {

    VideoUserFragmentBinding videouserbin;
    VideoAdapter adaptervideo;
    List<VideoModel> videolist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        videouserbin =VideoUserFragmentBinding.inflate(inflater, container,false);
        return videouserbin.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adaptervideo = new VideoAdapter(getContext(), new ArrayList<>());
        videouserbin.rcvVideo.setAdapter(adaptervideo);
        loadAllVideo();
    }

    private void loadAllVideo() {
        //setup data for genre
        videouserbin.rcvVideo.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
       videolist  = new ArrayList<>();



        //getAll categories form firebase  >Categories
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Videos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear arraylist before adđing data info it
                videolist.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    //get Data
                    VideoModel model = ds.getValue(VideoModel.class);
                    //add arraylist
                    Log.d(">>>", "singer: "+model.getName());

                    videolist.add(model);

                }
                //setAdapter Recycle view
                adaptervideo.setData(videolist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
