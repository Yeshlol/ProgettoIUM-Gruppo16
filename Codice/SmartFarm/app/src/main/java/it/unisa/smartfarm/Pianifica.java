package it.unisa.smartfarm;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Locale;


public class Pianifica extends AppCompatActivity {
    private Account accountAttivo;
    private int mYear,mMonth,mDay;
    private TextInputEditText chiave;
    private TextInputEditText prodotto;
    private TextInputEditText ettari;
    private TextInputEditText data;
    private TextInputEditText resa;
    private LinearLayout linearLayout;
    private TextView errortextView;
    private LinearLayout.LayoutParams layoutParams;
    private TextInputLayout textInputLayoutChiave, textInputLayoutProdotto, textInputLayoutEttari,
            textInputLayoutData, textInputLayoutResa;
    private static String myFormat = "dd-MM-yyyy";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pianifica);

        Intent intent = getIntent();
        accountAttivo = (Account) intent.getSerializableExtra("account");

        chiave = findViewById(R.id.et_chiave);
        prodotto = findViewById(R.id.et_prodotto);
        ettari = findViewById(R.id.et_ettari);
        data = findViewById(R.id.et_data);
        resa = findViewById(R.id.et_resa);
        textInputLayoutChiave = findViewById(R.id.tInputChiave);
        textInputLayoutProdotto = findViewById(R.id.tInputProdotto);
        textInputLayoutEttari = findViewById(R.id.tInputEttari);
        textInputLayoutData = findViewById(R.id.tInputData);
        textInputLayoutResa = findViewById(R.id.tInputResa);

        data = (TextInputEditText) findViewById(R.id.et_data);

        linearLayout = findViewById(R.id.linearLayout);

        errortextView = new TextView(getApplicationContext());
        errortextView.setTextSize(12);
        errortextView.setGravity(Gravity.CENTER);
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;


        errortextView.setTypeface(null, Typeface.BOLD);
        errortextView.setPadding(50,20,50,20);
        errortextView.setTextColor(getColor(R.color.colorRed));

        final Calendar myCalendar = Calendar.getInstance();

        new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
                data.setText(sdf.format(myCalendar.getTime()));
            }
        };

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(Pianifica.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if (year < mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (monthOfYear < mMonth && year == mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                    view.updateDate(mYear,mMonth,mDay);

                                data.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void aggiungiColtura(View v) {
        linearLayout.removeView(errortextView);
        textInputLayoutChiave.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext));
        textInputLayoutProdotto.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext));
        textInputLayoutEttari.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext));
        textInputLayoutData.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext));
        textInputLayoutResa.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext));

        String chiaveText = chiave.getText().toString();
        String prodottoText = prodotto.getText().toString();
        String ettariText = ettari.getText().toString();
        String dataText = data.getText().toString();
        String resaText = resa.getText().toString();

        if (chiaveText.equals("")){
            textInputLayoutChiave.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.chiave_vuota);
            linearLayout.addView(errortextView,1,layoutParams);
            return;
        }
        if (prodottoText.equals("")){
            textInputLayoutProdotto.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.prodotto_vuoto);
            linearLayout.addView(errortextView,2,layoutParams);
            return;
        }
        if (ettariText.equals("")){
            textInputLayoutEttari.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.ettari_vuoto);
            linearLayout.addView(errortextView,3,layoutParams);
            return;
        }
        if (!isValidFormat(myFormat,dataText,Locale.ITALIAN)
            && !isValidFormat("d-MM-yyyy",dataText,Locale.ITALIAN)
            && !isValidFormat("dd-M-yyyy",dataText,Locale.ITALIAN)
            && !isValidFormat("d-M-yyyy",dataText,Locale.ITALIAN)) {
            textInputLayoutData.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.data_non_valida);
            linearLayout.addView(errortextView,4,layoutParams);
            return;
        }
        if (resaText.equals("")){
            textInputLayoutResa.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_error));
            errortextView.setText(R.string.resa_vuoto);
            linearLayout.addView(errortextView,5,layoutParams);
            return;
        }


        SharedPreferences spColture = getSharedPreferences(getResources().getString(R.string.file_colture), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spColture.edit();

        String[] coltura = new String[] {prodottoText, ettariText + " ha", dataText, resaText + " t/ha"};

        String colturaString = TextUtils.join(";", coltura);
        editor.putString(chiaveText, colturaString);

        editor.commit();

        Intent i = new Intent();
        i.setClass(this, Colture.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("account", accountAttivo);
        startActivity(i);
    }

    public void homeCliccato(MenuItem item){
        Intent i = new Intent(this, HomePage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
        i.putExtra("account", accountAttivo);
        startActivity(i);
    }

    public void logoutCliccato(MenuItem item) {
        View view = this.getLayoutInflater().inflate(R.layout.dialog_logout,null);

        Button conferma = view.findViewById(R.id.btn_conferma);
        Button annulla = view.findViewById(R.id.btn_annulla);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Pianifica.this);
        alertDialogBuilder.setView(view);
        final AlertDialog dialog = alertDialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(i);
                dialog.dismiss();
            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static boolean isValidFormat(String format, String value, Locale locale) {
        LocalDateTime ldt = null;
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

        try {
            ldt = LocalDateTime.parse(value, fomatter);
            String result = ldt.format(fomatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, fomatter);
                String result = ld.format(fomatter);
                return result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, fomatter);
                    String result = lt.format(fomatter);
                    return result.equals(value);
                } catch (DateTimeParseException e2) {
                    // Debugging purposes
                    //e2.printStackTrace();
                }
            }
        }

        return false;
    }
}
