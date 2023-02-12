package com.example.basicmusic.MainFragment;

import android.content.Intent;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.basicmusic.Admin.Activityadmin.DashBoardAdmin;
import com.example.basicmusic.MainActivity;
import com.example.basicmusic.R;
import com.example.basicmusic.databinding.HomeAdminBinding;
import com.example.basicmusic.databinding.LoginFragmentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginMusic extends Fragment {
    FirebaseAuth mFirebaseAuth;
    LoginFragmentBinding loginbinding;
    NavController mNavController;
    NavigationView mNavView;
    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginbinding = LoginFragmentBinding.inflate(inflater,container,false);
        mNavController =
                Navigation.findNavController(requireActivity(), R.id.idFragmentContainer);
        loginbinding.btnResigter.setOnClickListener(v ->{
            mNavController.navigate(R.id.action_loginMusic_to_registerMusic);

        });
        loginbinding.btnLogin.setOnClickListener(v ->{
            validateData();
        });
        return loginbinding.getRoot();
    }

    private String Email = "" ,Password = "";
    private void validateData() {
        Email =loginbinding.tvEmail.getText().toString().trim();
        Password=loginbinding.tvPassword.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            Toast.makeText(requireActivity(), "Invalid email Pattern..", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Password)){
            Toast.makeText(requireActivity(),"Enter Password...", Toast.LENGTH_SHORT).show();
        }else{
            loginUser();
        }
    }

    private void loginUser() {
        //login user
        mFirebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login success check user or admin
                        checkUser();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
//                        progressDialog.dismiss();
                        Toast.makeText(requireActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void checkUser() {
//        progressDialog.setMessage("Checking user...");
//        //Check user or admin in realtimebase
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot snapshot) {
//                        progressDialog.dismiss();
                        //get user type
                        String  userType =""+snapshot.child("userType").getValue();
                        //check user type
                        if(userType.equals("user")){
                            //this is simple user ,open user dashboard
                            Intent intent = new Intent(requireActivity(),MainActivity.class);

                            startActivity(intent);

                        }else if(userType.equals("admin")){
                            //this is simple user ,open admin dashboard
                            startActivity(new Intent(requireActivity(), DashBoardAdmin.class));


                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });
    }
}

