package it.unisa.smartfarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashSet;
import java.util.Set;

public class Registrazione extends AppCompatActivity {
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText password2;
    private LinearLayout linearLayout;
    private TextView errortextView;
    private LinearLayout.LayoutParams layoutParams;
    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword, textInputLayoutPassword2;
    private RadioButton radioButton;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        password2 = findViewById(R.id.et_Ripetipassword);
        linearLayout=findViewById(R.id.linearLayout);
        textInputLayoutEmail=findViewById(R.id.tInputEmail);
        textInputLayoutPassword=findViewById(R.id.tInputPassword);
        textInputLayoutPassword2=findViewById(R.id.tInputPasswordRipeti);

        errortextView=new TextView(getApplicationContext());
        errortextView.setTextSize(18);
        layoutParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity= Gravity.CENTER;
        //errortextView.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.error));
        errortextView.setTypeface(null, Typeface.BOLD);
        errortextView.setPadding(50,20,50,20);
        errortextView.setTextColor(getColor(R.color.colorRed));
    }

    public void registra (View v){
        linearLayout.removeView(errortextView);
        textInputLayoutEmail.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext));
        textInputLayoutPassword.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext));
        textInputLayoutPassword2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext));

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        int radioButtonID = radioGroup.getCheckedRadioButtonId();

        radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);

        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String passwordRipetiText = password2.getText().toString();
        SharedPreferences spUtenti = getSharedPreferences(getResources().getString(R.string.file_utenti), Context.MODE_PRIVATE);
        Set<String> set = spUtenti.getStringSet(emailText, null);
        
        if (set != null) {
            textInputLayoutEmail.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.account_esistente);
            linearLayout.addView(errortextView,3,layoutParams);
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            textInputLayoutEmail.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.formato_errato);
            linearLayout.addView(errortextView,3,layoutParams);
            return;
        }
        if (passwordText.equals("")){
            textInputLayoutPassword.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.empty_password);
            linearLayout.addView(errortextView,3,layoutParams);
            return;
        }
        if (passwordRipetiText.equals("")){
            textInputLayoutPassword2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.empty_password2);
            linearLayout.addView(errortextView,3,layoutParams);
            return;
        }
        if (!passwordRipetiText.equals(passwordText)){
            textInputLayoutPassword.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            textInputLayoutPassword2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.password_diverse);
            linearLayout.addView(errortextView,3,layoutParams);
            return;
        }

        String ruolo = (String) radioButton.getText();

        SharedPreferences.Editor editor = spUtenti.edit();

        Set<String> hash_Set = new HashSet<String>();
        hash_Set.add(passwordText);
        hash_Set.add(ruolo);

        System.out.println("Account da registrare: " + emailText + " " + passwordText + " " + ruolo);

        editor.putStringSet(emailText, hash_Set);

        editor.commit();

        Intent i = new Intent();
        i.setClass(this, HomePage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("account", new Account(emailText,passwordText,ruolo));
        startActivity(i);
    }
}