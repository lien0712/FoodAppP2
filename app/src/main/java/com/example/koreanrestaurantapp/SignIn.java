package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koreanrestaurantapp.Common.Common;
import com.example.koreanrestaurantapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    Button btnSignIn;
    EditText edtPhone, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn= (Button) findViewById(R.id.btnSignIn);
        edtPassword= (EditText) findViewById(R.id.edtPassword);
        edtPhone= (EditText) findViewById(R.id.edtPhone);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user= database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check if user not exit
                        if( dataSnapshot.child(edtPhone.getText().toString()).exists()){
                            //Get user information
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            user.setPhone(edtPhone.getText().toString());//set phone

                            if( user.getPassword().equals(edtPassword.getText().toString())){
//                                Toast.makeText(SignIn.this,"Sign in successfully",Toast.LENGTH_SHORT).show();
                                Common.currentUser= user;
                                if(user.getActive().equals("user")){
                                    Intent homeIntent = new Intent(SignIn.this,Home.class);

                                    startActivity(homeIntent);
                                    finish();
                                }
                                else if(user.getActive().equals("admin")){
                                    Intent homeIntent = new Intent(SignIn.this,AdminActivity.class);
                                    //Common.currentUser= user;
                                    startActivity(homeIntent);
                                    finish();
                                }

                            }
                            else {
                                Toast.makeText(SignIn.this,"Sign in failed",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignIn.this,"User not exist",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}
//32