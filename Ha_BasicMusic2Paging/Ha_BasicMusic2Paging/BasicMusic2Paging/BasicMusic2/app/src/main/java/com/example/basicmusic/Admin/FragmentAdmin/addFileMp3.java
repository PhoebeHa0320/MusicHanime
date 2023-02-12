package com.example.basicmusic.Admin.FragmentAdmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.basicmusic.databinding.AddFileMp3Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class addFileMp3 extends Fragment {
    AddFileMp3Binding addFileMp3Binding;
    FirebaseAuth mFirebaseAuth;
    private Uri imageUri = null;
    private ProgressDialog progressDialog;
    private static final String TAG ="SINGER_IV";

    //arraylist to hold pdf categories
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

        //handle Click selected Pickture from gellary
        addFileMp3Binding.imgSimger.setOnClickListener(v->{
            showImageAttachMenu();
        });
        //handle Click selected file mp3 from gallery
        addFileMp3Binding.imgFileMp.setOnClickListener(v -> {
            selectFileMp3();
        });
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

    private void showImageAttachMenu() {
        PopupMenu popup = new PopupMenu(requireActivity(),addFileMp3Binding.imgSimger);
        popup.getMenu().add(Menu.NONE,0,0,"Camera");
        popup.getMenu().add(Menu.NONE,1,1,"Gallery");
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int which  = item.getItemId();
                if(which ==0){
                    //Camera
                    pickImageCamera();
                }else if(which ==1){
                    //Gallery
                     pickImageGallery();
                }
                return false;
            }
        });

    }
    private void pickImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryResultLauncher.launch(intent);
    }

    private void pickImageCamera() {
        //intent to pick image from camera
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Chọn Mới");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"");
        imageUri = requireActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        cameraResultLauncher.launch(intent);


    }
    private ActivityResultLauncher<Intent> cameraResultLauncher= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "onActivityResult: "+imageUri);
                    //used to handle result of camera
                    //get uri of image
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data  = result.getData(); //no need here as in camera case we alrealdy  have image in imageuri varible
                        addFileMp3Binding.imgSimger.setImageURI(imageUri);

                    }else{
                        Toast.makeText(requireActivity(), "Thử lại", Toast.LENGTH_SHORT).show();
                    }

                }
            }
    );
    private ActivityResultLauncher<Intent> galleryResultLauncher= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //get uri of image
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Log.d(TAG, "onActivityResult: "+imageUri);
                        Intent data  = result.getData(); //no need here as in camera case we alrealdy  have image in imageuri varible
                        imageUri = data.getData();
                        Log.d(TAG, "onActivityResult: "+imageUri    );
                        addFileMp3Binding.imgSimger.setImageURI(imageUri);

                    }else{
                        Toast.makeText(requireActivity(), "Thử lại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
}
