package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.koreanrestaurantapp.model.Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddCategory extends AppCompatActivity {

    EditText edtNameCategory;
    Button btnSelect, btnUpload;
    ImageView imagecategory;
    Uri saveUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    Category newCategory;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        edtNameCategory=findViewById(R.id.edtNameCategory1);
        imagecategory= findViewById(R.id.imagecategory);
        btnSelect= findViewById(R.id.selectbtn1);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        btnUpload= findViewById(R.id.uploadbtn1);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

    }

    private void uploadImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();

        String imageName=UUID.randomUUID().toString();
        storageReference = FirebaseStorage.getInstance().getReference("menu/"+edtNameCategory.getText()+"/"+imageName);


        storageReference.putFile(saveUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        imagecategory.setImageURI(null);
                        Toast.makeText(AddCategory.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(AddCategory.this,"Failed to Upload",Toast.LENGTH_SHORT).show();


                    }
                });

    }

    private void selectImage() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && data!=null && data.getData()!=null){
            saveUri= data.getData();
            imagecategory.setImageURI(saveUri);
            btnSelect.setText("Image Selected");
        }
    }

}