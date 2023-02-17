package com.example.basicmusic.Admin.FragmentAdmin;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.basicmusic.Admin.ModelAdmin.ModelMusic;
import com.example.basicmusic.Admin.ModelAdmin.VideoModel;
import com.example.basicmusic.FileUtils;
import com.example.basicmusic.R;
import com.example.basicmusic.databinding.AddFileMp3Binding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class addFileMp3 extends Fragment {
    AddFileMp3Binding addFileMp3Binding;
    FirebaseAuth mFirebaseAuth;
    private Uri imageUri = null;
    private ProgressDialog progressDialog;
    private static final String TAG ="SINGER_IV";
    //uri of picked pdf
    Uri AudioUri;
    private final int PICK_AUDIO = 1;
    TextView titleNameMusic;
    ArrayList<String> categoriesTitleArrayList,CategoryIdArrayList;
    ArrayList<String> singerTitleArrayList,singerIdArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addFileMp3Binding = AddFileMp3Binding.inflate(inflater, container,false);

        //handle click back
        addFileMp3Binding.btnBack.setOnClickListener(v->{
            requireActivity().onBackPressed();
        });


        return addFileMp3Binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        addFileMp3Binding.tvCategory.setOnClickListener(v ->{
            categoryPickDialog();
        });
        addFileMp3Binding.tvTitleSinger.setOnClickListener(v ->{
            SingerPickDialog();
        });


        loadCategory();
        loadSinger();
        //setup progress dialog
        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setTitle("Please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        addFileMp3Binding.btnUploadMp.setOnClickListener(v ->{
            Mp3PickIntent();

        });
    addFileMp3Binding.AdMusic.setOnClickListener(v->{
        validateData();
    });

    }
    private String title = "";
    private void validateData() {
        //Step:validate Data
        Log.d(TAG, "validateData: validating data...");
        //Step getData
        title = addFileMp3Binding.etTitleMusic.getText().toString().trim();

        //validate Data
        if(TextUtils.isEmpty(title)){
            Toast.makeText(requireActivity(), "Nhập tên bài hát..", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(selectedSingerTitle)){
            Toast.makeText(requireActivity(), "Chọn Ca sĩ..", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(selectedCategoryTitle)){
            Toast.makeText(requireActivity(), "Yêu cầu chọn thể loại..", Toast.LENGTH_SHORT).show();
        }
        else if(AudioUri==null){
            Toast.makeText(requireActivity(), "Chọn Nhạc..", Toast.LENGTH_SHORT).show();
        }else{
            //all dataa is valid can upload now
            uploadMp3Storage();
        }


    }

    private void uploadMp3Storage() {
        //Step2:Validate data
        Log.d(TAG, "uploadMp3Storage: ");


        //show progressdialog
        progressDialog.setMessage("Uploading dialog...");
        progressDialog.show();
        //timestamp
        long timetamp = System.currentTimeMillis();
        //path off pdfin firebase storage
        String filepathName = "MusicMp3/" +timetamp;
        //Storage refendence
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filepathName);
        storageReference.putFile(AudioUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onSuccess: Mp3 uploaded to storage...");
                        Log.d(TAG, "onSuccess: geting mp3 url");
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        //get pdf url
                        while (!uriTask.isSuccessful());
                        String uploadMp3url = "" +uriTask.getResult();
                        //upload to firebase db
                        uploadToInfoDb(uploadMp3url,timetamp);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.d("mp6", "onFailure: Mp3 upload failed..."+e.getMessage());
                        Toast.makeText(requireActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void uploadToInfoDb(String uploadMp3url, long timetamp) {
        //Step3:Upload pdf info to firebase db

        Log.d(TAG, "uploadPdfStorage:Uploading pdf info to firebase db... ");
        progressDialog.setMessage("Uploading pdf info...");
        String uid = mFirebaseAuth.getUid();
        //Setup data to upload also add view count ,download count wwhile adding pdf/book

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid",""+uid);
        hashMap.put("id",""+timetamp);
        hashMap.put("title",""+title);
        hashMap.put("categoryId",""+selectedCategoryId);
        hashMap.put("singerId",""+selectedSingerId);
        hashMap.put("url",""+uploadMp3url);

        //db reference DB>Books
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("MusicMp3");
        ref.child("" +timetamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onSuccess:Successfully uploaded...");
                        Toast.makeText(requireActivity(), "Cập nhật mp3 Thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onFailure: Failed to upload to db due to"+e.getMessage());
                        Toast.makeText(requireActivity(), "Cập nhật mp3 Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void Mp3PickIntent() {
        Intent audio = new Intent();
        audio.setType("audio/*");
        audio.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(audio, "Select Audio"), PICK_AUDIO);

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_AUDIO && resultCode == RESULT_OK) {
            // Audio is Picked in format of URI
            AudioUri = data.getData();

//            addFileMp3Binding.nameFileMp3.setText(AudioUri.getUserInfo());
        }
    }
    private  String selectedCategoryId,selectedCategoryTitle;
    private  String selectedSingerId,selectedSingerTitle;
    private void SingerPickDialog() {
//        //first we nedd to get category from firebase
//        Log.d(TAG, "categoryPickDialog: showlog category pick dialog");
        //get String array of categories from arraylist
        String[] singerArray = new String[singerTitleArrayList.size()];
        for(int i= 0;i<singerTitleArrayList.size();i++){
            singerArray[i] = singerTitleArrayList.get(i);


        }
        //Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Chọn Nghệ sỹ")
                .setItems(singerArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //handle item click
                        //get clicked item from list
                        selectedSingerTitle = singerTitleArrayList.get(which);
                        selectedSingerId = singerIdArrayList.get(which);

                        //set to category textView
                        addFileMp3Binding.tvTitleSinger.setText(selectedSingerTitle);
//                        Log.d("category", "onClick: "+category);

                    }
                })
                .show();

    }
    private void categoryPickDialog() {
//        //first we nedd to get category from firebase
//        Log.d(TAG, "categoryPickDialog: showlog category pick dialog");
        //get String array of categories from arraylist
        String[] categoriesArray = new String[categoriesTitleArrayList.size()];
        for(int i= 0;i<categoriesTitleArrayList.size();i++){
            categoriesArray[i] = categoriesTitleArrayList.get(i);


        }
        //Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Pick Category")
                .setItems(categoriesArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //handle item click
                        //get clicked item from list
                        selectedCategoryTitle  = categoriesTitleArrayList.get(which);
                        selectedCategoryId = CategoryIdArrayList.get(which);

                        //set to category textView
                        addFileMp3Binding.tvCategory.setText(selectedCategoryTitle);
//                        Log.d("category", "onClick: "+category);

                    }
                })
                .show();

    }
    private void loadSinger() {
        Log.d(TAG, "load Singer: Loading");
        singerTitleArrayList = new ArrayList<>();
        singerIdArrayList = new ArrayList<>();
        //db refenrence to load categories > Categories
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SingerMusic");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                singerTitleArrayList.clear();//clear before adding data
                singerIdArrayList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    //get id and title category
                    String categoryId =""+ds.child("id").getValue();
                    String categoryTitle =""+ds.child("title").getValue();
                    //add to respective arraylist
                    singerTitleArrayList.add(categoryTitle);
                    singerIdArrayList.add(categoryId);
                    Log.d(">>", "onDataChange: "+singerTitleArrayList);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadCategory() {
        Log.d(TAG, "loadCategory: Loading");
        categoriesTitleArrayList = new ArrayList<>();
        CategoryIdArrayList = new ArrayList<>();
        //db refenrence to load categories > Categories
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CategoryMusic");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoriesTitleArrayList.clear();//clear before adding data
                CategoryIdArrayList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    //get id and title category
                    String categoryId =""+ds.child("id").getValue();
                    String categoryTitle =""+ds.child("title").getValue();
                    //add to respective arraylist
                    categoriesTitleArrayList.add(categoryTitle);
                    CategoryIdArrayList.add(categoryId);
                    Log.d(">>", "onDataChange: "+categoriesTitleArrayList);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void selectFileMp3() {

        
    }





}
