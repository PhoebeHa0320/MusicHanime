package com.example.basicmusic.MainFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.basicmusic.Admin.Adapter.CategoryAdapter;
import com.example.basicmusic.Admin.Adapter.SingerAdapter;
import com.example.basicmusic.Admin.ModelAdmin.CategoryMusic;
import com.example.basicmusic.Admin.ModelAdmin.Singer;
import com.example.basicmusic.R;
import com.example.basicmusic.databinding.HomeFragmentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeMusic extends Fragment {
    HomeFragmentBinding homebinding;
    ImageSlider imageSlider;
//    FirebaseDatabase db;
    CategoryAdapter categoryAdapter;
    SingerAdapter singerAdapter;
    LinearLayout linearLayoutHome;
    ProgressDialog progressDialog;
    List<CategoryMusic> categoryList;
    List<Singer> singerList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homebinding = HomeFragmentBinding.inflate(inflater,container,false);
        return homebinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageSlider = homebinding.imageSlider;
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle("Chào mừng bạn đến với MusicApp!");
//        progressDialog.setMessage("Vui lòng chờ...");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        LoadImageSlider();

        LoadAllGenre();
        loadAllSinger();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void loadAllSinger() {
        //setup data for genre
        homebinding.rcvSinger.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        singerList = new ArrayList<>();



        //getAll categories form firebase  >Categories
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SingerMusic");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear arraylist before adđing data info it
                singerList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    //get Data
                    Singer model = ds.getValue(Singer.class);
                    //add arraylist
                    singerList.add(model);

                }
                Log.d("size", "onDataChange: "+singerList.size());
                //setAdapter Recycle view
                singerAdapter = new SingerAdapter(getContext(), singerList) ;



                homebinding.rcvSinger.setAdapter(singerAdapter);
                singerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LoadImageSlider() {
        //image slider
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slide1, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.slide2, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.slide3, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.slide4, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.slide5, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);
    }
    @SuppressLint("NotifyDataSetChanged")
    private void LoadAllGenre() {
        //setup data for genre
        homebinding.rcvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();



        //getAll categories form firebase  >Categories
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CategoryMusic");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear arraylist before adđing data info it
                categoryList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    //get Data
                    CategoryMusic model = ds.getValue(CategoryMusic.class);
                    //add arraylist
                    categoryList.add(model);

                }
                Log.d("size", "onDataChange: "+categoryList.size());



                //setAdapter Recycle view
                categoryAdapter = new CategoryAdapter(getContext(), categoryList);
                homebinding.rcvCategory.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
