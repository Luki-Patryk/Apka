package com.example.ofiicial.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ofiicial.ACCOUNT.UserDataBaseAccess;
import com.example.ofiicial.MainActivity;
import com.example.ofiicial.PROFILE.ProfileFastSettings;
import com.example.ofiicial.PROFILE.PhotosCompare.ProfilePhotosCompareActivity;
import com.example.ofiicial.R;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;

import static android.app.Activity.RESULT_CANCELED;
import static androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;


public class ProfileFragment extends Fragment implements View.OnClickListener
{
    Context  mContext;

//    public ProfileFragment (Context mContext)
//    {
//        this.mContext = mContext;
//    }

    private UserDataBaseAccess userDataBaseAccess;

    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] profile_photo_to_pass_inByte;

    MainActivity mainActivity = (MainActivity) getActivity();

    ImageButton profile_btn_goTo_fast_settings, profile_btn_goTo_photos_comapare;
    TextView profile_name;
    ImageView fast_settings_img_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View profile_view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(profile_view);
        initlisteners(profile_view);
        setStart();

        return profile_view;
    }

    private void initViews(View profile_view)
    {
        profile_btn_goTo_fast_settings = profile_view.findViewById(R.id.profile_btn_goTo_fast_settings);
        profile_btn_goTo_photos_comapare = profile_view.findViewById(R.id.profile_btn_goTo_photos_compare);
        profile_name = profile_view.findViewById(R.id.profile_name);
        fast_settings_img_url = profile_view.findViewById(R.id.profile_img_url);
    }

    private void setStart()
    {
        //TODO: Don't know yet but there is probability of problem with getting everything by email :/
        MainActivity mainActivity = (MainActivity) getActivity();
        String start_email = mainActivity.getUserEmailFromLoginActivity();
        Toast.makeText(getActivity().getApplicationContext(), String.valueOf(mainActivity.getUserIdFromLoginActivity()), Toast.LENGTH_SHORT).show();

        userDataBaseAccess = UserDataBaseAccess.getProfile_instance(getContext());
        userDataBaseAccess.open();

        String start_name  = userDataBaseAccess.getUserNameByUserID(mainActivity.getUserIdFromLoginActivity());
        profile_name.setText(start_name);

        byte[] start_img_url = userDataBaseAccess.getUserPictureByUserID(mainActivity.getUserIdFromLoginActivity());
        Bitmap start_img_url_bitmap = BitmapFactory.decodeByteArray( start_img_url, 0,  start_img_url.length);
        if(start_img_url_bitmap != null)
        {
            fast_settings_img_url.setImageBitmap( start_img_url_bitmap);
        }

        userDataBaseAccess.close();
    }

    private void initlisteners(View profile_view)
    {
        profile_btn_goTo_fast_settings.setOnClickListener(ProfileFragment.this);
        profile_btn_goTo_photos_comapare.setOnClickListener(ProfileFragment.this);
    }

    @Override
    public void onClick(View view)
    {
        MainActivity mainActivity = (MainActivity) getActivity();

        switch (view.getId())
        {
            case R.id.profile_btn_goTo_fast_settings:
                Intent intent = new Intent(getContext(), ProfileFastSettings.class);
                intent.putExtra("PROFILE_NAME", profile_name.getText().toString().trim());
                intent.putExtra("PROFILE_ID", mainActivity.getUserIdFromLoginActivity());

                fast_settings_img_url.buildDrawingCache();
                Bitmap profile_photo_to_pass_bitmap = fast_settings_img_url.getDrawingCache();
                System.out.println("!!!!!!!!! CHODZI O TO !!!!!!!!!!");
                System.out.println(profile_photo_to_pass_bitmap);
                if(profile_photo_to_pass_bitmap != null)
                {
                    objectByteArrayOutputStream = new ByteArrayOutputStream();
                    profile_photo_to_pass_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
                    profile_photo_to_pass_inByte = objectByteArrayOutputStream.toByteArray();
                    intent.putExtra("PROFILE_IMAGE", profile_photo_to_pass_inByte);

                }
                startActivityForResult(intent, 2);
                break;

            case R.id.profile_btn_goTo_photos_compare:
                Intent intent_2 = new Intent(getContext(), ProfilePhotosCompareActivity.class);
                intent_2.putExtra("PROFILE_ID", mainActivity.getUserIdFromLoginActivity());
                startActivity(intent_2);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == 2)
        {
            byte [] user_image_from_FS_inByte = data.getByteArrayExtra("USER_IMAGE_FROM_FS");
            Bitmap user_image_from_FS_bitmap = BitmapFactory.decodeByteArray(user_image_from_FS_inByte, 0, user_image_from_FS_inByte.length);
            fast_settings_img_url.setImageBitmap(user_image_from_FS_bitmap);

            profile_name.setText(data.getStringExtra("USER_NAME_FROM_FS"));

            if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(mContext, "OHOHOOHOHO :(", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
