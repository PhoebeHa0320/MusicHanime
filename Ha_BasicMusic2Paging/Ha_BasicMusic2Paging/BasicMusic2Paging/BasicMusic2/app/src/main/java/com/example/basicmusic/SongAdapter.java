package com.example.basicmusic;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmusic.data.Music;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends PagingDataAdapter<Music, SongAdapter.SongViewHolder> {
    private final Context mContext;
    private final MusicController mMusicController;
    public boolean isLocal = true;
    private List<Music> mData = new ArrayList<>();

    public void setData(List<Music> data){
        mData = data;
        notifyDataSetChanged();
        //  Mỗi lần set lại data thì set lại cả music source
        mMusicController.setMusicSource(new MusicController.MusicSource() {
            @Override
            public int getSize() {
                return getItemCount();
            }


            @Override
            public Music getAtIndex(int index) {
                return isLocal ? mData.get(index) : getItem(index);
            }
        });
    }

    public SongAdapter(Context context, @NonNull DiffUtil.ItemCallback<Music> diffCallback) {
        super(diffCallback);
        mContext = context;

        //  Đổi sang singleton
        mMusicController = MusicController.getInstance(context);
    }

    public MusicController getMusicController() {
        return mMusicController;
    }

    public void start(){
        mMusicController.start();
        notifyItemChanged(mMusicController.getCurrentIndex());
    }


    public void pause(){
        mMusicController.pause();
        notifyItemChanged(mMusicController.getCurrentIndex());
    }

    public void playNext(){
        int lastIndex = mMusicController.getCurrentIndex();
        mMusicController.playNext();
        notifyItemChanged(lastIndex);
        notifyItemChanged(mMusicController.getCurrentIndex());
    }

    public void playPrev(){
        int lastIndex = mMusicController.getCurrentIndex();
        mMusicController.playPrev();
        notifyItemChanged(lastIndex);
        notifyItemChanged(mMusicController.getCurrentIndex());
    }
    public void clickRandom(){

        mMusicController.clickRandom();

        notifyItemChanged(mMusicController.getCurrentIndex());
    }


    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.bindView(isLocal ? mData.get(position) : getItem(position));
        holder.btnMore.setOnClickListener(v->{
            ShowOptionMoreDiaglog(mMusicController,holder);
        });

    }

    private void ShowOptionMoreDiaglog(MusicController mMusicController, SongViewHolder holder) {
        String[] Option = {"Thêm Playlist","Thêm Vào mục yêu thích","Xoá "};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("Choose Option")

                .setIcon(R.drawable.home_24)
                .setItems(Option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(item ==0){
                            Toast.makeText(mContext, "OnClick Playlist", Toast.LENGTH_SHORT).show();

                        }else if(item==1){
                            Toast.makeText(mContext, "OnClick Mục yêu thích", Toast.LENGTH_SHORT).show();
                        }else if(item ==2){
                            Toast.makeText(mContext, "OnClick Delete", Toast.LENGTH_SHORT).show();


                        }
                    }
                })

                .show();
    }


    @Override
    public int getItemCount() {
        return isLocal ? mData.size() : super.getItemCount();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView mAlbumArt;
        TextView mTitle;
        View mItemView;
        TextView mSinger;
        ImageButton btnMore;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            mAlbumArt = itemView.findViewById(R.id.album_art);
            mTitle = itemView.findViewById(R.id.txt_title);

            mSinger= itemView.findViewById(R.id.txt_singer);
            btnMore = itemView.findViewById(R.id.btnMore);
            itemView.setOnClickListener(v -> {
                int lastIndex = mMusicController.getCurrentIndex();
                mMusicController.playSongAt(getLayoutPosition());
                notifyItemChanged(lastIndex);
                notifyItemChanged(mMusicController.getCurrentIndex());
                if (mItemClickListener != null) {
                    int pos = getLayoutPosition();
                    mItemClickListener.onClick(v, isLocal ? mData.get(pos) : getItem(pos), pos);
                }

            });

        }

        public void bindView(Music song) {
            if(mMusicController.getCurrentIndex() == getLayoutPosition()){
                if(mMusicController.isPlaying() || mMusicController.isPreparing()) {
                    mTitle.setTextColor(Color.MAGENTA);
//                    mItemView.setBackgroundColor(Color.GREEN);
                }
                else {
                    mTitle.setTextColor(Color.YELLOW);
                }
            } else {
                mTitle.setTextColor(Color.BLACK);
            }

            mTitle.setText(song.getTitle());
            mSinger.setText(song.getSinger());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, Long.parseLong(song.getId()));
                try {
                    Bitmap bitmap = mContext.getContentResolver().loadThumbnail(trackUri, new Size(72, 72), null);
                    mAlbumArt.setImageBitmap(bitmap);
                } catch (Exception e) {
                }
            } else {
                mAlbumArt.setImageURI(song.getAlbumUri());
            }
        }
    }



    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener Listener) {
        mItemClickListener = Listener;
    }

    public interface OnItemClickListener {
        void onClick(View v, Music song, int pos);
    }
}

class MusicComparator extends DiffUtil.ItemCallback<Music> {
    @Override
    public boolean areItemsTheSame(@NonNull Music oldItem,
                                   @NonNull Music newItem) {
        // Id is unique.
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Music oldItem,
                                      @NonNull Music newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

}