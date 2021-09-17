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

//    MainActivity mainActivity = (MainActivity) getActivity();

    ImageButton profile_btn_goTo_fast_settings, profile_btn_goTo_photos_comapare;
    TextView profile_name;
    ImageView profile_img_picture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View profile_view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(profile_view);
        setStart();
        initlisteners(profile_view);

        return profile_view;
    }

    private void initViews(View profile_view)
    {
        profile_btn_goTo_fast_settings = profile_view.findViewById(R.id.profile_btn_goTo_fast_settings);
        profile_btn_goTo_photos_comapare = profile_view.findViewById(R.id.profile_btn_goTo_photos_compare);
        profile_name = profile_view.findViewById(R.id.profile_name);
        profile_img_picture = profile_view.findViewById(R.id.profile_img_url);
    }

    private void setStart()
    {
        MainActivity mainActivity = (MainActivity) getActivity();
        String start_email = mainActivity.getUserEmailFromLoginActivity();
        Toast.makeText(mContext, String.valueOf(mainActivity.getUserIdFromLoginActivity()), Toast.LENGTH_SHORT).show();

        userDataBaseAccess = UserDataBaseAccess.getProfile_instance(getContext());
        userDataBaseAccess.open();

        String start_name  = userDataBaseAccess.getUserNameByUserEmail(start_email);
        profile_name.setText(start_name);

        String start_img_url = userDataBaseAccess.getUserPictureByUserEmail(start_email);
        if(start_img_url != null)
        {
            profile_img_picture.setImageURI( Uri.parse(start_img_url));
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
        switch (view.getId())
        {
            case R.id.profile_btn_goTo_fast_settings:
                Intent intent = new Intent(getContext(), ProfileFastSettings.class);
                intent.putExtra("PROFILE_NAME", profile_name.getText().toString().trim());

                Bitmap profile_photo_to_pass = profile_img_picture.getDrawingCache();
                objectByteArrayOutputStream = new ByteArrayOutputStream();
                if(profile_photo_to_pass != null)
                {
                    profile_photo_to_pass.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
                    profile_photo_to_pass_inByte = objectByteArrayOutputStream.toByteArray();
                    intent.putExtra("PROFILE_IMAGE", profile_photo_to_pass_inByte);

                }
                startActivityForResult(intent, 2);
                break;

            case R.id.profile_btn_goTo_photos_compare:
                Intent intent_2 = new Intent(getContext(), ProfilePhotosCompareActivity.class);
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
            profile_img_picture.setImageBitmap(user_image_from_FS_bitmap);

            profile_name.setText(data.getStringExtra("USER_NAME_FROM_FS"));

            if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(mContext, "OHOHOOHOHO :(", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
