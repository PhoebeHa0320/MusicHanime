package com.example.basicmusic.MainFragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.basicmusic.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends Fragment {
 FragmentRegisterBinding registerbinding;
 FirebaseAuth mFirebaseAuth;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth= FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        registerbinding = FragmentRegisterBinding.inflate(inflater,container,false);
        return registerbinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        
        super.onViewCreated(view, savedInstanceState);
        registerbinding.btnRegister.setOnClickListener(v ->{

            validateData();
        });
    }
    private String name ="",email = "" ,password= "";

    private void validateData() {
        name = registerbinding.etTitleName.getText().toString().trim();
        email=registerbinding.etTitleEmail.getText().toString().trim();
        password=registerbinding.etTitlePassword.getText().toString().trim();
        String rePassword=registerbinding.etTitleRepass.getText().toString().trim();
        //Validate data
        if(TextUtils.isEmpty(name)){
            Toast.makeText(requireActivity(), "Enter your name ...", Toast.LENGTH_SHORT).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(requireActivity(), "Invalid email Pattern..", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(requireActivity(), "Enter Password...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(rePassword)){
            Toast.makeText(requireActivity(), "Confirm Password...", Toast.LENGTH_SHORT).show();
        }else if(!password.equals(rePassword)){
            Toast.makeText(requireActivity(), "Password doesn't password...", Toast.LENGTH_SHORT).show();

        }else{
            CreateAccount();
        }

    }

    private void CreateAccount() {
        //create user in firebase auth
        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //account create success
                        updateUserInfo();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //account create failed
//                        progressDialog.dismiss();
                        Toast.makeText(requireActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUserInfo() {
        long timetamp = System.currentTimeMillis();
        //get current user uid,since user is regiser
        String uid =mFirebaseAuth.getUid();
        //setup data add in db
        HashMap<String,Object> hashMap= new HashMap<>();
        hashMap.put("uid",uid);
        hashMap.put("email",email);
        hashMap.put("name",name);
        hashMap.put("profileimage","");
        hashMap.put("userType","user");
        hashMap.put("timetamp",timetamp);
        //set data to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //data added to database
//                        progressDialog.dismiss();
                        Toast.makeText(requireActivity(), "Account created...", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(Register_Form.this, UserDashBoard.class));
//                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        //data failed adding to database
//                        progressDialog.dismiss();
                        Toast.makeText(requireActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
}
