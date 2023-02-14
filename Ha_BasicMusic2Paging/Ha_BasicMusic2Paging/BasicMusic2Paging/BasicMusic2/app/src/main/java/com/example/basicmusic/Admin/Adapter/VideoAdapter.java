package com.example.basicmusic.Admin.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmusic.Admin.ModelAdmin.VideoModel;
import com.example.basicmusic.databinding.VideoItemBinding;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{
    private Context context;
    private List<VideoModel> arrlistVideo;
    private VideoItemBinding videoitemBinding;
    MediaController mediaController;
//    SimpleExoPlayer exoPlayer;
    public VideoAdapter(Context context, List<VideoModel> arrlistVideo) {
        this.context = context;
        this.arrlistVideo = arrlistVideo;
    }
    public void setData(List<VideoModel> arrlistVideo){
        this.arrlistVideo=arrlistVideo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        videoitemBinding= VideoItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new VideoViewHolder(videoitemBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoModel model = arrlistVideo.get(position);
        loadVideo(model,holder);

    }

    private void loadVideo(VideoModel model, VideoViewHolder holder) {
        String url = model.getUrl_video();

        Uri uri = Uri.parse(url);
        holder.nameVideo.setText(model.getName());
        holder.videoView.setVideoURI(uri);
        mediaController = new MediaController(context);
        holder.videoView.setMediaController(mediaController);
//        holder.videoView.start();


    }

    @Override
    public int getItemCount() {
        return arrlistVideo.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        TextView nameVideo;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = videoitemBinding.videoPlayer;
            nameVideo = videoitemBinding.tvName;
        }
    }
}
