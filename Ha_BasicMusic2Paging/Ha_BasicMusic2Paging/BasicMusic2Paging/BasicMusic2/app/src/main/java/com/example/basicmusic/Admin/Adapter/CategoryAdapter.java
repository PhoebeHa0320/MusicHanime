package com.example.basicmusic.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.basicmusic.Admin.ModelAdmin.CategoryMusic;
import com.example.basicmusic.R;
import com.example.basicmusic.databinding.CategoryItemBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private Context context;
    private List<CategoryMusic> arrlistCategory;
    private CategoryItemBinding categoryitembin;

    private FirebaseAuth mFirebaseAuth;
    public CategoryAdapter(Context context, List<CategoryMusic> arrlistCategory) {
        this.context = context;
        this.arrlistCategory = arrlistCategory;
        mFirebaseAuth =  FirebaseAuth.getInstance();

    }

    public void setData(List<CategoryMusic> arrlistCategory){
        this.arrlistCategory = arrlistCategory;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        categoryitembin = CategoryItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CategoryViewHolder(categoryitembin.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        CategoryMusic category = arrlistCategory.get(position);
//        String categoryTitle = category.getNameCategory();
//
//        //set data
//        holder.titleCategory.setText(categoryTitle);
        loadDetailCategory(category,holder);


    }

    private void loadDetailCategory(CategoryMusic category, CategoryViewHolder holder) {
        holder.titleCategory.setText(category.getTitle());
        Glide.with(context)
                .load(category.getUrl())
                .placeholder(R.drawable.person_24)
                .into(holder.categoryPickture);
        //        Log.d(TAG, "loadUserInfo:Đang tải dữ liệu "+mFirebaseAuth.getUid());
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CategoryMusic");
//        ref.child(category.getId())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        //get all info user here from snapshot
//                        String fullName = ""+snapshot.child("title").getValue();
//
//                        String profileImage = "" + snapshot.child("url").getValue();
//
//
//
//                        //setdatato ui
//                        categoryitembin.categoryTv.setText(fullName);
//
//                        //set image ,using glide
//                        Glide.with(context)
//                                .load(profileImage)
//                                .placeholder(R.drawable.person_24)
//                                .into(holder.categoryPickture);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
    }

    @Override
    public int getItemCount() {
        return arrlistCategory.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryPickture;
        TextView titleCategory;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryPickture = categoryitembin.categoryIv;
            titleCategory = categoryitembin.categoryTv;

        }
    }

}
