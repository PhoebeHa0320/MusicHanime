package com.example.basicmusic.Admin.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.basicmusic.Admin.ModelAdmin.Singer;
import com.example.basicmusic.R;
import com.example.basicmusic.databinding.SingerItemBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerViewHolder> {

    private Context context;
    private     List<Singer> arrayListSinger;
    private SingerItemBinding singerbin;
    private FirebaseAuth mFirebaseAuth;
    public SingerAdapter(Context context, List<Singer> arrayListSinger) {
        this.context = context;
        this.arrayListSinger = arrayListSinger;
        mFirebaseAuth =  FirebaseAuth.getInstance();
    }

    public void setData(List<Singer> arrayListSinger){
        this.arrayListSinger = arrayListSinger;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //init ui into singer_item.xml
        singerbin = SingerItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new SingerViewHolder(singerbin.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull SingerViewHolder holder, int position) {
        Singer singer = arrayListSinger.get(position);

//        String title = singer.getNameSinger();



//        //set data
//        holder.nameSingerTv.setText(title);
//        //

        loadDetailsSinger(singer,holder);
        holder.itemView.setOnClickListener(v->{

        });


    }

    private void loadDetailsSinger(Singer singer, SingerViewHolder holder) {
//        Log.d(TAG, "loadUserInfo:Đang tải dữ liệu "+mFirebaseAuth.getUid());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SingerMusic");
        ref.child(singer.getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get all info user here from snapshot
                        String fullName = ""+snapshot.child("title").getValue();

                        String profileImage = "" + snapshot.child("url").getValue();



                        //setdatato ui
                        singerbin.singerTv.setText(fullName);

                        //set image ,using glide
                        Glide.with(context)
                                .load(profileImage)
                                .placeholder(R.drawable.person_24)
                                .into(singerbin.singerIv);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return arrayListSinger.size();
    }

    class SingerViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView singerPickture;
        TextView nameSingerTv;
        public SingerViewHolder(@NonNull View itemView) {
            super(itemView);
            singerPickture = singerbin.singerIv;
            nameSingerTv = singerbin.singerTv;
        }
    }}
