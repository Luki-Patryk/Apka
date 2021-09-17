package com.example.ofiicial.PROFILE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ofiicial.ACCOUNT.UserDataBaseAccess;
import com.example.ofiicial.MainActivity;
import com.example.ofiicial.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileFastSettings extends AppCompatActivity implements View.OnClickListener
{
    TextView fast_settings_change_photo, fast_settings_txt_change_username, fast_settings_txt_change_gender, fast_settings_txt_change_location;
    TextView fast_settings_txt_username, fast_settings_txt_gender, fast_settings_txt_location, fast_settings_txt_email_verification;
    ImageView fast_settings_username_right_arrow, fast_settings_gender_right_arrow, fast_settings_location_right_arrow;
    Button fast_settings_email_verify_btn, fast_settings_email_change_btn, fast_settings_save_btn;
    ImageView fast_settings_img_profile;
    RadioGroup fast_settings_radioGroup;
    RadioButton fast_settings_radio_btn_man, fast_settings_radio_btn_woman;
    EditText fast_settings_username_change_edit_txt, fast_settings_location_change_edit_txt;

    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;

    private final AppCompatActivity activity = ProfileFastSettings.this;

    private ProfileInputValidation profileInputValidation;

    private UserDataBaseAccess userDataBaseAccess;

    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] user_img_url_to_save_inByte;

    private Uri image_file_path;
    private Bitmap image_to_store;
    private boolean is_image_changed;

    boolean u_right_arrow_clicked = false;
    boolean g_right_arrow_clicked = false;
    boolean l_right_arrow_clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fast_settings);
        getSupportActionBar().hide();

        initViews();
        setStart();
        initListeners();
        initObjects();
        Toast.makeText(activity, String.valueOf(getIntent().getIntExtra("USER_ID", -1)), Toast.LENGTH_SHORT).show();
    }

    private void initViews()
    {
     fast_settings_change_photo = findViewById(R.id.photos_compare_photo_add_url_textView);
     fast_settings_txt_change_username = findViewById(R.id.fast_settings_txt_change_username);
     fast_settings_txt_change_gender = findViewById(R.id.fast_settings_txt_change_gender);
     fast_settings_txt_change_location = findViewById(R.id.fast_settings_txt_change_location);

     fast_settings_txt_username =findViewById(R.id.fast_settings_txt_username);
     fast_settings_txt_gender = findViewById(R.id.fast_settings_txt_gender);
     fast_settings_txt_location = findViewById(R.id.fast_settings_txt_location);
     fast_settings_txt_email_verification = findViewById(R.id.fast_settings_txt_email_verification);

     fast_settings_username_right_arrow = findViewById(R.id.fast_settings_username_right_arrow);
     fast_settings_gender_right_arrow = findViewById(R.id.fast_settings_gender_right_arrow);
     fast_settings_location_right_arrow = findViewById(R.id.fast_settings_location_right_arrow);

     fast_settings_email_verify_btn = findViewById(R.id.fast_settings_email_verify_btn);
     fast_settings_email_change_btn = findViewById(R.id.fast_settings_email_change_btn);
     fast_settings_save_btn = findViewById(R.id.fast_settings_save_btn);

     fast_settings_img_profile = findViewById(R.id.fast_settings_img_url);

     fast_settings_radioGroup = findViewById(R.id.fast_settings_radio_group);

     fast_settings_radio_btn_man = findViewById(R.id.fast_settings_radio_btn_man);
     fast_settings_radio_btn_woman = findViewById(R.id.fast_settings_radio_btn_woman);

     fast_settings_username_change_edit_txt = findViewById(R.id.fast_settings_username_change_edit_txt);
     fast_settings_location_change_edit_txt = findViewById(R.id. fast_settings_location_change_edit_txt);
    }

    private void initListeners()
    {
        fast_settings_change_photo.setOnClickListener(this);
        fast_settings_username_right_arrow.setOnClickListener(this);
        fast_settings_gender_right_arrow.setOnClickListener(this);
        fast_settings_location_right_arrow.setOnClickListener(this);
        fast_settings_save_btn.setOnClickListener(this);
    }

    private void initObjects()
    {
        profileInputValidation = new ProfileInputValidation(activity);
    }

    private void setStart()
    {
        Intent intent = getIntent();
        String fs_user_name = intent.getStringExtra("PROFILE_NAME");
        byte [] fs_user_image_inByte = getIntent().getByteArrayExtra("PROFILE_IMAGE");

        if (fs_user_image_inByte != null)
        {
            Bitmap fs_user_image_bitmap = BitmapFactory.decodeByteArray(fs_user_image_inByte, 0, fs_user_image_inByte.length);
            fast_settings_img_profile.setImageBitmap(fs_user_image_bitmap);
        }

        userDataBaseAccess = UserDataBaseAccess.getProfile_instance(getApplicationContext());
        userDataBaseAccess.open();

        String fs_user_gender = userDataBaseAccess.getFastSettingsStartDataByProfileName(fs_user_name)[0];
        String fs_user_location = userDataBaseAccess.getFastSettingsStartDataByProfileName(fs_user_name)[1];

        userDataBaseAccess.close();

        fast_settings_txt_change_username.setText(fs_user_name);

        if(fs_user_gender != null)
        {
            fast_settings_txt_change_gender.setText(fs_user_gender);
        }
        if(fs_user_location != null)
        {
            fast_settings_txt_change_location.setText(fs_user_location);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.photos_compare_photo_add_url_textView:
                FS_change_photo();
                break;
            case R.id.fast_settings_username_right_arrow:
                FS_change_username();
                break;
            case R.id.fast_settings_gender_right_arrow:
                FS_change_gender();
                break;
            case R.id.fast_settings_location_right_arrow:
                FS_change_location();
                break;
            case R.id.fast_settings_save_btn:
                FS_save();
                break;
            default:
                Toast.makeText(activity, "Something is wrong :/", Toast.LENGTH_SHORT).show();
        }
    }

    private void FS_change_photo()
    {
        //check runtime permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED)
            {
                //permission not granted, request it
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show popup for runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            }
            else
            {
                //permission already granted
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
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults)
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
                    Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    // handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE)
        {
            image_file_path = data.getData();
            try
            {
                image_to_store = MediaStore.Images.Media.getBitmap(getContentResolver(), image_file_path);
                is_image_changed = true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            fast_settings_img_profile.setImageBitmap(image_to_store);
        }
    }

    private void FS_change_username()
    {
        //TODO: Connect in right way!

        u_right_arrow_clicked = !u_right_arrow_clicked;

        if(u_right_arrow_clicked)
        {
            fast_settings_txt_change_username.setVisibility(View.GONE);
            fast_settings_txt_username.setVisibility(View.GONE);
            fast_settings_username_change_edit_txt.setVisibility(View.VISIBLE);
        }
        else
        {
            if(!profileInputValidation.isInputEditTextFilled(fast_settings_username_change_edit_txt, getString(R.string.error_message_username)))
            {
                return;
            }
            else
            {
                userDataBaseAccess = UserDataBaseAccess.getProfile_instance(getApplicationContext());
                userDataBaseAccess.open();

                if(userDataBaseAccess.checkUsername(fast_settings_username_change_edit_txt.getText().toString().trim()))
                {
                    fast_settings_txt_change_username.setText(fast_settings_username_change_edit_txt.getText().toString().trim());
                }
                userDataBaseAccess.close();
            }

            fast_settings_txt_change_username.setVisibility(View.VISIBLE);
            fast_settings_txt_username.setVisibility(View.VISIBLE);
            fast_settings_username_change_edit_txt.setVisibility(View.GONE);
        }
    }

    private void FS_change_gender()
    {

        //TODO:Connect with RIGhT DATABASE!

        g_right_arrow_clicked = !g_right_arrow_clicked;

        if(g_right_arrow_clicked)
        {
            fast_settings_txt_change_gender.setVisibility(View.GONE);
            fast_settings_txt_gender.setVisibility(View.GONE);
            fast_settings_radioGroup.setVisibility(View.VISIBLE);
        }
        else
        {
            userDataBaseAccess = UserDataBaseAccess.getProfile_instance(getApplicationContext());
            userDataBaseAccess.open();

            if(fast_settings_radio_btn_man.isChecked())
            {
                fast_settings_txt_change_gender.setText("M");
            }
            else
            {
                fast_settings_txt_change_gender.setText("W");
            }

            fast_settings_txt_change_gender.setVisibility(View.VISIBLE);
            fast_settings_txt_gender.setVisibility(View.VISIBLE);
            fast_settings_radioGroup.setVisibility(View.GONE);
        }

    }

    private void FS_change_location()
    {

        //TODO:Connect with RIGhT DATABASE!

        l_right_arrow_clicked = !l_right_arrow_clicked;

        if(l_right_arrow_clicked)
        {
            fast_settings_txt_change_location.setVisibility(View.GONE);
            fast_settings_txt_location.setVisibility(View.GONE);
            fast_settings_location_change_edit_txt.setVisibility(View.VISIBLE);
        }
        else
        {
            if(!profileInputValidation.isInputEditTextFilled(fast_settings_username_change_edit_txt, getString(R.string.error_message_location)))
            {
                return;
            }
            else
            {
                fast_settings_txt_change_location.setText(fast_settings_location_change_edit_txt.getText().toString().trim());
            }
            fast_settings_txt_change_location.setVisibility(View.VISIBLE);
            fast_settings_txt_location.setVisibility(View.VISIBLE);
            fast_settings_location_change_edit_txt.setVisibility(View.GONE);
        }
    }


    private void FS_save()
    {
        //TODO: CHANGE THIS FUN IN DATABASE FOR WORKING BY ID NO BY USERNAME!!!!
        Bitmap user_img_url_to_save = image_to_store;
        objectByteArrayOutputStream = new ByteArrayOutputStream();
        user_img_url_to_save.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
        user_img_url_to_save_inByte = objectByteArrayOutputStream.toByteArray();


        // Change bitmap / username / gender / location in database
        userDataBaseAccess = UserDataBaseAccess.getProfile_instance(getApplicationContext());
        userDataBaseAccess.open();

        boolean isUpdated = userDataBaseAccess.changeUserDataFS_test(getIntent().getIntExtra("user_id", -1), user_img_url_to_save_inByte,
                fast_settings_txt_change_username.getText().toString().trim(),
                fast_settings_txt_change_gender.getText().toString().trim(),
                fast_settings_txt_change_location.getText().toString().trim());

        if(isUpdated)
        {
            Toast.makeText(activity, "YEA :)", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(activity, "NO :(", Toast.LENGTH_SHORT).show();
        }
        userDataBaseAccess.close();

        // Pass result for ProfileFragment (If necessary)
        String user_name_from_FS = fast_settings_txt_change_username.getText().toString().trim();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("USER_IMAGE_FROM_FS", user_img_url_to_save_inByte);
        resultIntent.putExtra("USER_NAME_FROM_FS", user_name_from_FS);

        setResult(2, resultIntent);

        //Finish!
        finish();
    }

}
