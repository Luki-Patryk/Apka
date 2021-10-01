package com.example.ofiicial.PROFILE.PhotosCompare;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ofiicial.PROFILE.PhotosCompare.PhotoDatabaseStuff.ProfilePhotosCompareDataBaseAccess;
import com.example.ofiicial.R;
//TODO: Upgrade layout

//TODO: make function for showing only imageView!
public class ProfilePhototComparePhotoShow extends AppCompatActivity implements View.OnClickListener
{
    private ProfilePhotosCompareDataBaseAccess profilePhotosCompareDataBaseAccess;

    private ProfilePhotosCompareAdapter mAdapter;

    ImageView photos_compare_photo_show_image;
    TextView photos_compare_photo_show_txt, photos_compare_photo_delete_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photot_compare_photo_show);
        getSupportActionBar().hide();

        initViews();
        setStart();
        initListeners();
    }

    private void initViews()
    {
        photos_compare_photo_show_image = findViewById(R.id.photos_compare_photo_show_image);
        photos_compare_photo_show_txt = findViewById(R.id.photos_compare_photo_show_txt);
        photos_compare_photo_delete_txt = findViewById(R.id.photos_compare_photo_delete_txt);
    }

    private void setStart()
    {
        photos_compare_photo_show_txt.setText(getIntent().getStringExtra("WEIGHT") + " kg");

        byte [] passed_photo_url_in_byte = getIntent().getByteArrayExtra("IMAGE");
        Bitmap passed_photo_url_bitmap = BitmapFactory.decodeByteArray(passed_photo_url_in_byte, 0, passed_photo_url_in_byte.length);
        photos_compare_photo_show_image.setImageBitmap(passed_photo_url_bitmap);
    }

    private void initListeners()
    {
        photos_compare_photo_delete_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.photos_compare_photo_delete_txt:
                deletePhotoFromRecyclerView();
                break;
        }
    }

    private void deletePhotoFromRecyclerView()
    {
        profilePhotosCompareDataBaseAccess = ProfilePhotosCompareDataBaseAccess.getPhoto_instance(getApplicationContext());
        profilePhotosCompareDataBaseAccess.open();
        profilePhotosCompareDataBaseAccess.deletePhotoById(getIntent().getIntExtra("ID", -1));
        profilePhotosCompareDataBaseAccess.close();

        finish();
    }
}