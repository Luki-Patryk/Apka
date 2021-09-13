package com.example.ofiicial.PROFILE;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileInputValidation
{
    private Context context;

    public ProfileInputValidation (Context context)
    {
        this.context = context;
    }

    public boolean isInputEditTextFilled(EditText editText, String message)
    {
        String value = editText.getText().toString().trim();
        if(value.isEmpty())
        {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            hideKeyboardFrom(editText);
            return false;
        }
        else
        {
//            Toast.makeText(context, "DONE!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void hideKeyboardFrom(View view)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
