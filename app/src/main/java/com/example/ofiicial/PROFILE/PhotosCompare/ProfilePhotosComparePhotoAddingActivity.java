package com.example.ofiicial.PROFILE.PhotosCompare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ofiicial.PROFILE.PhotosCompare.PhotoDatabaseStuff.ProfilePhotosCompareDataBaseAccess;
import com.example.ofiicial.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.ofiicial.PROFILE.ProfileFastSettings.IMAGE_PICK_CODE;
import static com.example.ofiicial.PROFILE.ProfileFastSettings.PERMISSION_CODE;

public class ProfilePhotosComparePhotoAddingActivity extends AppCompatActivity implements View.OnClickListener
{
    private ProfilePhotosCompareDataBaseAccess profilePhotosCompareDataBaseAccess;

    ImageView photos_compare_photo_add_url_imageView;
    TextView photos_compare_photo_add_url_textView;
    EditText photos_compare_photo_add_date_editText, photos_compare_photo_add_weight_editText;
    Button photos_compare_photo_add_btn;

    private boolean isImageChanged = false;
    private Uri imageFilePath;
    private Bitmap imageToStore;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photos_compare_photo_adding);
        getSupportActionBar().hide();
        initViews();
        initListeners();
    }

    private void initViews()
    {
      photos_compare_photo_add_url_imageView = findViewById(R.id.photos_compare_photo_add_url_imageView);
      photos_compare_photo_add_url_textView = findViewById(R.id.photos_compare_photo_add_url_textView);
      photos_compare_photo_add_date_editText = findViewById(R.id.photos_compare_photo_add_date_editText);
      photos_compare_photo_add_weight_editText = findViewById(R.id.photos_compare_photo_add_weight_editText);
      photos_compare_photo_add_btn = findViewById(R.id.photos_compare_photo_add_btn);
    }

    private void initListeners()
    {
        photos_compare_photo_add_url_textView.setOnClickListener(this);
        photos_compare_photo_add_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
     switch (view.getId())
     {
         case R.id.photos_compare_photo_add_url_textView:
             PC_change_photo();
             break;
         case R.id.photos_compare_photo_add_btn:
             PC_add_image_to_recyclerView();
             break;
     }
    }

    private void PC_change_photo()
    {
        //Check runtime permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED)
            {
                //permission not granted, request it
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show popup for runtime permission
                requestPermissions(permission, PERMISSION_CODE);
            }
            else
            {
                //permision already granted
                pickImageFromGallery();
            }
        }
        else
        {
            //system os is less then marshmallow
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery()
    {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    // handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                }
                else
                {
                    // permission was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK)
        {
            imageFilePath = data.getData();
            try
            {
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);
                isImageChanged = true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            photos_compare_photo_add_url_imageView.setImageBitmap(imageToStore);

        }
    }

    private void PC_add_image_to_recyclerView()
    {
        if(!isImageChanged || photos_compare_photo_add_date_editText.getText().toString().equals("") || photos_compare_photo_add_weight_editText.getText().toString().equals(""))
        {
            Toast.makeText(this, "Fill photo, date and weight!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            profilePhotosCompareDataBaseAccess = ProfilePhotosCompareDataBaseAccess.getPhoto_instance(getApplicationContext());
            profilePhotosCompareDataBaseAccess.open();

            Photos photo_add = new Photos(imageToStore, photos_compare_photo_add_date_editText.getText().toString(),Integer.parseInt(photos_compare_photo_add_weight_editText.getText().toString()));

            profilePhotosCompareDataBaseAccess.addPhoto(photo_add);

            Toast.makeText(this, "Photo added", Toast.LENGTH_SHORT).show();

            profilePhotosCompareDataBaseAccess.close();

            Intent intent = new Intent();
            setResult(3, intent);
            finish();
        }
    }


}