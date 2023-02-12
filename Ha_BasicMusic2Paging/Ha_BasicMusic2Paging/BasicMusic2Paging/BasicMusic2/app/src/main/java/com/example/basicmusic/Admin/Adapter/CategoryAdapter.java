package com.example.basicmusic.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmusic.Admin.ModelAdmin.CategoryMusic;
import com.example.basicmusic.databinding.CategoryItemBinding;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private Context context;
    private List<CategoryMusic> arrlistCategory;
    private CategoryItemBinding categoryitembin;

    public CategoryAdapter(Context context, List<CategoryMusic> arrlistCategory) {
        this.context = context;
        this.arrlistCategory = arrlistCategory;
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
        String categoryTitle = category.getNameCategory();

        //set data
        holder.titleCategory.setText(categoryTitle);



    }

    @Override
    public int getItemCount() {
        return arrlistCategory.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView categoryPickture;
        TextView titleCategory;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryPickture = categoryitembin.categoryIv;
            titleCategory = categoryitembin.categoryTv;

        }
    }

}
