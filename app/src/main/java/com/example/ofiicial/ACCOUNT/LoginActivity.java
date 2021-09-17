package com.example.ofiicial.ACCOUNT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ofiicial.Fragment.ProfileFragment;
import com.example.ofiicial.MainActivity;
import com.example.ofiicial.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
//    public static final String EMAIL_PASS = "com.example.official_project.EMAIL_PASS";

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout lay_Email, lay_Password;

    private EditText et_Email, et_Password;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;

    private UserDataBaseAccess userDataBaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }



    private void initViews()
    {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        lay_Email = findViewById(R.id.lay_Email);
        lay_Password = findViewById(R.id.lay_Password);
        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);
        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);
    }

    private void initListeners()
    {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects()
    {
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intent_register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent_register);

                break;
        }
    }

    private void verifyFromSQLite()
    {
        if(!inputValidation.isInputEditTextFilled(et_Email, lay_Email, getString(R.string.error_message_email)))
        {
            return;
        }
        if(!inputValidation.isInputEditTextEmail(et_Email, lay_Email, getString(R.string.error_message_email)))
        {
            return;
        }
        if(!inputValidation.isInputEditTextFilled(et_Email, lay_Password, getString(R.string.error_message_email)))
        {
            return;
        }

        userDataBaseAccess = UserDataBaseAccess.getProfile_instance(getApplicationContext());
        userDataBaseAccess.open();

        if(userDataBaseAccess.checkUser(et_Email.getText().toString().trim(), et_Password.getText().toString().trim()))
        {
            Intent accountsIntent = new Intent(activity, MainActivity.class);
            accountsIntent.putExtra("EMAIL_PASS", et_Email.getText().toString().trim());
            accountsIntent.putExtra("USER_ID", userDataBaseAccess.getUserIdByUserEmail(et_Email.getText().toString().trim()));
            emptyInputEditText();
            startActivity(accountsIntent);
        }
        else
        {
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }

        userDataBaseAccess.close();
    }

    private void emptyInputEditText()
    {
        et_Email.setText(null);
        et_Password.setText(null);
    }


}