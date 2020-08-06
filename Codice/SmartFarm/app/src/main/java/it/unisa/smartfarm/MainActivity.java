package it.unisa.smartfarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText password;
    private LinearLayout linearLayout;
    private TextView errortextView;
    private LinearLayout.LayoutParams layoutParams;
    private TextInputLayout textInputLayoutEmail, textInputLayoutPassword;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        linearLayout = findViewById(R.id.linearLayout);
        textInputLayoutEmail = findViewById(R.id.tInputEmail);
        textInputLayoutPassword = findViewById(R.id.tInputPassword);
        errortextView = new TextView(getApplicationContext());
        errortextView.setTextSize(18);
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity= Gravity.CENTER;
        errortextView.setTypeface(null, Typeface.BOLD);
        errortextView.setPadding(50,20,50,20);
        errortextView.setTextColor(getColor(R.color.colorRed));

        SharedPreferences spUtenti = getSharedPreferences(getResources().getString(R.string.file_utenti),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spUtenti.edit();

        Set<String> hash_Set = new HashSet<String>();

        hash_Set.add("lucio");
        hash_Set.add("Amministratore");

        editor.putStringSet("luciodurso@smartfarm.it", hash_Set);
        editor.commit();

        SharedPreferences spColture = getSharedPreferences(getResources().getString(R.string.file_colture),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = spColture.edit();

        editor2.putString("luciodurso@smartfarm.it", "1");
        editor2.putString("luciodurso@smartfarm.it", "2");
        editor2.putString("luciodurso@smartfarm.it", "3");
        editor2.putString("luciodurso@smartfarm.it", "4");
        editor2.commit();
    }

    public void registratiCliccato(View v){
        Intent intent = new Intent(this, Registrazione.class);
        startActivity(intent);
    }

    public void loginCliccato(View v){
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        linearLayout.removeView(errortextView);
        textInputLayoutEmail.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext));
        textInputLayoutPassword.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext));

        if (emailText.equals("")){
            textInputLayoutEmail.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.empty_email);
            linearLayout.addView(errortextView,2,layoutParams);
            return;
        }
        if (passwordText.equals("")){
            textInputLayoutPassword.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.empty_password);
            linearLayout.addView(errortextView,2,layoutParams);
            return;
        }

        SharedPreferences spUtenti = getSharedPreferences(getResources().getString(R.string.file_utenti),Context.MODE_PRIVATE);

        Set<String> set = spUtenti.getStringSet(emailText, null);
        Iterator<String> it = set.iterator();
        String password = null;
        String ruolo = null;

        if(it.hasNext())
             password = it.next();

        if(it.hasNext())
            ruolo = it.next();

        System.out.println(emailText + " " + password + " " + ruolo);

        if (password == null || !passwordText.equals(password)){
            errortextView.setText(R.string.invalid_emailPassword);
            linearLayout.addView(errortextView,2,layoutParams);
            return;
        }

        Account accountLog = new Account(emailText, passwordText, ruolo);

        SharedPreferences spColture = getSharedPreferences(getResources().getString(R.string.file_colture),Context.MODE_PRIVATE);
        ArrayList<String> colture = new ArrayList<String>(spColture.getStringSet("luciodurso@smartfarm.it",new HashSet<String>()));
        accountLog.setColture(colture);

        Intent i = new Intent();
        i.setClass(this,HomePage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("account",accountLog);
        startActivity(i);
    }
}
