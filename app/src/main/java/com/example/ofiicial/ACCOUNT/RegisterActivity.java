package com.example.ofiicial.ACCOUNT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ofiicial.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener
{
    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout lay_Name, lay_Email, lay_Password, lay_ConfPassword  ;

    private EditText et_Name, et_Email, et_Password, et_ConfPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private User user;

    private UserDataBaseAccess userDataBaseAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews()
    {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        lay_Name = findViewById(R.id.lay_Name);
        lay_Email = findViewById(R.id.lay_Email);
        lay_Password = findViewById(R.id.lay_Password);
        lay_ConfPassword = findViewById(R.id.lay_ConfPassword);
        et_Name = findViewById(R.id.et_Name);
        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        et_ConfPassword = findViewById(R.id.et_ConfPassword);
        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister);
        appCompatTextViewLoginLink = findViewById(R.id.appCompatTextViewLoginLink);
    }

    private void initListeners()
    {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }

    private void initObjects()
    {
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.appCompatButtonRegister:
                postDatatoSQLite();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    private void postDatatoSQLite()
    {
        if (!inputValidation.isInputEditTextFilled(et_Name, lay_Name, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(et_Email, lay_Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(et_Email, lay_Email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(et_Password, lay_Password, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(et_Password, et_ConfPassword,
                lay_ConfPassword, getString(R.string.error_password_match)))
        {
            return;
        }

        userDataBaseAccess =  UserDataBaseAccess.getProfile_instance(getApplicationContext());
        userDataBaseAccess.open();

        if (!userDataBaseAccess.checkUser(et_Email.getText().toString().trim()))
        {

            String user_name = et_Name.getText().toString().trim();
            String user_email = et_Email.getText().toString().trim();
            String user_password = et_Password.getText().toString().trim();

            userDataBaseAccess.addUser(user_name, user_email, user_password);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        }
        else
        {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText()
    {
        et_Name.setText(null);
        et_Email.setText(null);
        et_Password.setText(null);
        et_ConfPassword.setText(null);
    }
}