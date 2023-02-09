package com.example.basicmusic.Admin.FragmentAdmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmusic.Admin.Adapter.SingerAdapter;
import com.example.basicmusic.Admin.Adapter.SingerRcvAdapter;
import com.example.basicmusic.Admin.ModelAdmin.Singer;
import com.example.basicmusic.databinding.SingerFragmentBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class SingerFragment extends Fragment {
    SingerFragmentBinding singerbinding;

    //adapter
    private SingerAdapter adapter;
////    private SingerRcvAdapter adapter;
//    RecyclerView recycleview;
    //array list to hold list of data of type Singer
    private ArrayList<Singer> singerList;

    private FirebaseAuth mfiFirebaseAuth;
    private Uri imageUri = null;
    private ProgressDialog progressDialog;
    private static final String TAG ="SINGER_IV";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        singerbinding= SingerFragmentBinding.inflate(inflater,container,false);
        return singerbinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mfiFirebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        singerbinding.imgSinger.setOnClickListener(v->{
            showImageAttachMenu();
        });
        //setup progress dialog
        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setTitle("Please wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        singerbinding.btnAddSinger.setOnClickListener(v->{
            validatedata();
        });
//        recycleview = singerbinding.rcvSinger;
//        recycleview.setLayoutManager(new LinearLayoutManager(requireActivity()));


//        FirebaseRecyclerOptions<Singer> options =
//                new FirebaseRecyclerOptions.Builder<Singer>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference("SingerMusic"),Singer.class)
//                        .build();
        //adapter
        loadSingerInfoFromDb();
//        adapter = new SingerAdapter(options);
//        recycleview.setAdapter(adapter);
////        loadSingerInfoFromDb();

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//    }
        private void loadSingerInfoFromDb() {
            //init arraylisst
            singerList = new ArrayList<>();

            //getAll categories form firebase  >Categories
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SingerMusic");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //clear arraylist before adđing data info it
                    singerList.clear();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        //get Data
                        Singer model = ds.getValue(Singer.class);
                        //add arraylist
                        singerList.add(model);

                    }
                    //setAdapter Recycle view
                    adapter = new SingerAdapter(getActivity(),singerList);
                    singerbinding.rcvSinger.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    private String titleSinger = "";

    private void validatedata() {
        //Step:validate Data
        Log.d(TAG, "validateData: validating data...");
        //Step getData
        titleSinger = singerbinding.etTitleCategory.getText().toString().trim();

        //validate Data
        if(TextUtils.isEmpty(titleSinger)){
            Toast.makeText(requireActivity(), "Enter Title..", Toast.LENGTH_SHORT).show();
        }
        else if(imageUri==null){
            Toast.makeText(requireActivity(), "Enter Pick Image..", Toast.LENGTH_SHORT).show();
        }else{
            //all dataa is valid can upload now
            uploadImageStorage();
        }
    }

    private void uploadImageStorage() {
        //Step2:Validate data
        Log.d(TAG, "uploadPdfStorage: ");


        //show progressdialog
        progressDialog.setMessage("Uploading dialog...");
        progressDialog.show();
        //timestamp
        long timetamp = System.currentTimeMillis();
        //path off pdfin firebase storage
        String filepathName = "SingerMusic/" +timetamp;
        //Storage refendence
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filepathName);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onSuccess: PDF uploaded to storage...");
                        Log.d(TAG, "onSuccess: geting pdf url");
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        //get pdf url
                        while (!uriTask.isSuccessful());
                        String uploadImagegUrl = "" +uriTask.getResult();
                        //upload to firebase db
                        uploadToInfoDb(uploadImagegUrl,timetamp);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onFailure: PDF upload failed..."+e.getMessage());
                        Toast.makeText(requireActivity(), "PDF upload failed due to", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void uploadToInfoDb(String uploadImagegUrl, long timetamp) {
        //Step3:Upload pdf info to firebase db

        Log.d(TAG, "uploadPdfStorage:Uploading Image info to firebase db... ");
        progressDialog.setMessage("Uploading Image info...");
        String uid = mfiFirebaseAuth.getUid();
        //Setup data to upload also add view count ,download count wwhile adding pdf/book

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid",""+uid);
        hashMap.put("id",""+timetamp);
        hashMap.put("title",""+titleSinger);
        hashMap.put("url",""+uploadImagegUrl);

        //db reference DB>Books
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SingerMusic");
        ref.child("" +timetamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onSuccess:Successfully uploaded...");
                        Toast.makeText(requireActivity(), "Successfully uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onFailure: Failed to upload to db due to"+e.getMessage());
                        Toast.makeText(requireActivity(), "Failed to upload to db due to", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showImageAttachMenu() {
        PopupMenu popup = new PopupMenu(requireActivity(),singerbinding.imgSinger);
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
                        singerbinding.imgSinger.setImageURI(imageUri);

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
                        singerbinding.imgSinger.setImageURI(imageUri);

                    }else{
                        Toast.makeText(requireActivity(), "Thử lại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
}
