package com.example.ofiicial.PROFILE.PhotosCompare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ofiicial.PROFILE.PhotosCompare.PhotoDatabaseStuff.ProfilePhotosCompareDataBaseAccess;
import com.example.ofiicial.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProfilePhotosCompareActivity extends AppCompatActivity implements View.OnClickListener
{
    private ProfilePhotosCompareDataBaseAccess profilePhotosCompareDataBaseAccess;

    private ImageView photos_compare_photo_before, photos_compare_photo_after;
    private FloatingActionButton photos_compare_floating_btn;

    private RecyclerView photosRecView;
    private ProfilePhotosCompareAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Context context;

    private ArrayList<Photos> photos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photos_compare);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
        recyclerViewStuff();

    }

    private void initViews()
    {
        photos_compare_photo_before = findViewById(R.id.photos_compare_photo_before);
        photos_compare_photo_after = findViewById(R.id.photos_compare_photo_after);
        photos_compare_floating_btn = findViewById(R.id.photos_compare_floating_btn);
    }

    private void initListeners()
    {
        photos_compare_floating_btn.setOnClickListener(this);
    }

    private void initObjects()
    {
        photosRecView = findViewById(R.id.PC_recyclerView);
    }

    private void recyclerViewStuff()
    {
        //use this setting to improve performance
        photosRecView.setHasFixedSize(true);

        //set a grid layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        photosRecView.setLayoutManager(gridLayoutManager);

        //specify an adapter
        mAdapter = new ProfilePhotosCompareAdapter(getApplicationContext());
        photosRecView.setAdapter(mAdapter);

        profilePhotosCompareDataBaseAccess = ProfilePhotosCompareDataBaseAccess.getPhoto_instance(getApplicationContext());

        profilePhotosCompareDataBaseAccess.open();
        photos = profilePhotosCompareDataBaseAccess.getAllPhotos();
        profilePhotosCompareDataBaseAccess.close();

        mAdapter.setPhotos(photos);
    }




    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.photos_compare_floating_btn:
                Intent intent = new Intent(getApplicationContext(), ProfilePhotosComparePhotoAddingActivity.class);
                startActivityForResult(intent, 3);
                break;
        }
    }

    //if request code is correct
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 3)
        {
            profilePhotosCompareDataBaseAccess.open();
            photos = profilePhotosCompareDataBaseAccess.getAllPhotos();
            profilePhotosCompareDataBaseAccess.close();
            mAdapter.setPhotos(photos);
            mAdapter.notifyDataSetChanged();
        }
    }

}