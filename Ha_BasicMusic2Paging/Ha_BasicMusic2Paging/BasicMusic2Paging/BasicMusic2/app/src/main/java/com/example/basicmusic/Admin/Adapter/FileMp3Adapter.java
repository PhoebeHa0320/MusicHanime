package com.example.basicmusic.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmusic.Admin.ModelAdmin.ModelMusic;
import com.example.basicmusic.SongDetailsFragment;
import com.example.basicmusic.databinding.SongFragmentDetailBinding;

import java.util.ArrayList;

public class FileMp3Adapter extends RecyclerView.Adapter<FileMp3Adapter.FileMp3ViewHolder>{
    private Context context;
    private ArrayList<ModelMusic> arraylistMusic = new ArrayList<>();

    //init ui from song_details_fragment.xml
    private SongFragmentDetailBinding detailsbinding;
    //Contructor params
    public FileMp3Adapter(Context context, ArrayList<ModelMusic> arraylistMusic) {
        this.context = context;
        this.arraylistMusic = arraylistMusic;
    }

    @NonNull
    @Override
    public FileMp3ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        detailsbinding = SongFragmentDetailBinding.inflate(LayoutInflater.from(context),parent,false);
        return new FileMp3ViewHolder(detailsbinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull FileMp3ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return arraylistMusic.size();
    }

    class FileMp3ViewHolder extends RecyclerView.ViewHolder {

        public FileMp3ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
