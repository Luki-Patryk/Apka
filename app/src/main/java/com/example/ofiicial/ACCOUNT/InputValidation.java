package com.example.ofiicial.ACCOUNT;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class InputValidation
{
    private Context context;

    public InputValidation (Context context )
    {
        this.context = context;
    }

    public boolean isInputEditTextFilled(EditText editText, TextInputLayout textInputLayout, String message)
    {
        String value = editText.getText().toString().trim();
        if(value.isEmpty())
        {
            textInputLayout.setError(message);
            hideKeyboardFrom(editText);
            return false;
        }
        else
        {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextEmail(EditText editText, TextInputLayout textInputLayout, String message)
    {
        String value = editText.getText().toString().trim();
        if(value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches())
        {
            textInputLayout.setError(message);
            hideKeyboardFrom(editText);
            return false;
        }
        else
        {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextMatches(EditText editText_1, EditText editText_2, TextInputLayout textInputLayout, String message)
    {
        String value_1 = editText_1.getText().toString().trim();
        String value_2 = editText_2.getText().toString().trim();

        if(!value_1.contentEquals(value_2))
        {
            textInputLayout.setError(message);
            hideKeyboardFrom(editText_2);
            return false;
        }
        else
        {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private void hideKeyboardFrom(View view)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
