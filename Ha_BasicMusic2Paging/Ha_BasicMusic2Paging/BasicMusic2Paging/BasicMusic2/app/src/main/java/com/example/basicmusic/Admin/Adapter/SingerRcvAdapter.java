package com.example.basicmusic.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.basicmusic.Admin.ModelAdmin.Singer;
import com.example.basicmusic.databinding.SingerItemBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class SingerRcvAdapter extends FirebaseRecyclerAdapter <Singer,SingerRcvAdapter.SingerRcvViewHolder> {

    private SingerItemBinding singerbindingg;
//    private Context context;
//    private ArrayList<Singer> arrlistsinger;


    public SingerRcvAdapter(@NonNull FirebaseRecyclerOptions<Singer> options) {
        super(options);



    }

    @Override
    protected void onBindViewHolder(@NonNull SingerRcvViewHolder holder, int position, @NonNull Singer model) {

        holder.titleSinger.setText(model.getNameSinger());
        Glide.with(holder.singerPickture.getContext())
                .load(model.getUrl_image())
////                .placeholder(R.drawable.person_24)
//                .circleCrop()
//                .error(R.drawable.ic_baseline_person_24)
                .into(holder.singerPickture);
        holder.itemView.setOnClickListener(v->{

        });

    }

    @NonNull
    @Override
    public SingerRcvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        singerbindingg = SingerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new SingerRcvViewHolder(singerbindingg.getRoot());
    }

    class SingerRcvViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView singerPickture;
        TextView titleSinger;
        public SingerRcvViewHolder(@NonNull View itemView) {

            super(itemView);
            singerPickture = singerbindingg.singerIv;
            titleSinger=singerbindingg.singerTv;
        }
    }
}
