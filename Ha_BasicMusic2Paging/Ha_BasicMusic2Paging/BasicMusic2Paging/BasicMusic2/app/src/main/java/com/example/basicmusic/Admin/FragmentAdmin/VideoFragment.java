package com.example.basicmusic.Admin.FragmentAdmin;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.basicmusic.Admin.ModelAdmin.VideoModel;
import com.example.basicmusic.databinding.VideoFragmentBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class VideoFragment extends Fragment {
    VideoFragmentBinding videobinding;
    StorageReference storageReference;
    DatabaseReference reference;
    private Uri urlVideo;
    VideoModel modelvideo;
    UploadTask uploadTask;
    MediaController mediaController;
    private static final int PICK_VIDEO = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        videobinding = VideoFragmentBinding.inflate(inflater,container,false);
        return videobinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference("Videos");
        reference = FirebaseDatabase.getInstance().getReference("Videos");
        modelvideo = new VideoModel();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videobinding.btnUpdateVideo.setOnClickListener(v ->{
            UploadVideo();

        });
        mediaController = new MediaController(requireActivity());
        videobinding.videoPlayer.setMediaController(mediaController);
        videobinding.videoPlayer.start();
        videobinding.btnChooseVideo.setOnClickListener(v ->{
            ChooseVideo(view);
        });
    }

    private void UploadVideo() {
        String videoName  = videobinding.etTitleEv.getText().toString().trim();
        if(urlVideo != null || TextUtils.isEmpty(videoName)){
            final StorageReference ref = storageReference.child(System.currentTimeMillis()+"."+getExt(urlVideo));
            uploadTask = ref.putFile(urlVideo);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();

                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        Toast.makeText(requireActivity(), "Thanh Cong", Toast.LENGTH_SHORT).show();
                        modelvideo.setName(videoName);
                        modelvideo.setUrl_video(downloadUri.toString());
                        String i = reference.push().getKey();
                        reference.child(i).setValue(modelvideo);
                    }else{

                    }
                }
            });

        }else{
            Toast.makeText(getContext(), "That bai", Toast.LENGTH_SHORT).show();
        }
    }

    public void ChooseVideo(View view){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_VIDEO);
    }
    private String getExt(Uri uri){
        ContentResolver contentResolver= requireActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_VIDEO || resultCode == RESULT_OK || data != null||data.getData() !=null){
            urlVideo =  data.getData();
            videobinding.videoPlayer.setVideoURI(urlVideo);

        }
    }
    private void ShowVideo(){

    }
}
