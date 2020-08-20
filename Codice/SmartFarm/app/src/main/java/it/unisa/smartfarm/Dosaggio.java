package it.unisa.smartfarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Dosaggio extends AppCompatActivity {
    private Account accountAttivo;
    private TextView tvTitoloDosaggio;
    private EditText et_insetticida;
    private EditText et_concime;
    private EditText et_acqua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosaggio);

        tvTitoloDosaggio = findViewById(R.id.tvTitoloDosaggio);
        et_insetticida = findViewById(R.id.et_insetticida);
        et_concime = findViewById(R.id.et_concime);
        et_acqua = findViewById(R.id.et_acqua);

        Intent intent = getIntent();
        if (intent != null) {
            accountAttivo = (Account) intent.getSerializableExtra("account");
            String nomeColtura = (String) intent.getSerializableExtra("coltura");

            tvTitoloDosaggio.setText(nomeColtura);

            Button btn_modifica = findViewById(R.id.btn_modifica);

            if(accountAttivo.getRuolo().equals("Dipendente"))
                btn_modifica.setVisibility(View.GONE);

            SharedPreferences spDosaggi = getSharedPreferences(getResources().getString(R.string.file_dosaggi), Context.MODE_PRIVATE);
            String dosaggioString = spDosaggi.getString(nomeColtura, "");
            List<String> dosaggio = new ArrayList<>();

            if (!dosaggioString.isEmpty()) {
                dosaggio = new ArrayList<>(Arrays.asList(dosaggioString.split(";")));

                Iterator<String> it = dosaggio.iterator();
                String insetticida = it.next();
                String concime = it.next();
                String acqua = it.next();

                et_insetticida.setText(insetticida);
                et_concime.setText(concime);
                et_acqua.setText(acqua);
            }
        }
    }

    public void modificaDosaggi(View v) {
        String coltura = tvTitoloDosaggio.getText().toString();
        String insetticida = et_insetticida.getText().toString();
        String concime = et_concime.getText().toString();
        String acqua = et_acqua.getText().toString();

        SharedPreferences spDosaggi = getSharedPreferences(getResources().getString(R.string.file_dosaggi), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spDosaggi.edit();

        String[] dosaggio = new String[] {insetticida, concime, acqua};

        String dosaggioString = TextUtils.join(";", dosaggio);
        editor.putString(coltura, dosaggioString);

        editor.commit();

        Intent i = new Intent();
        i.setClass(this, Dosaggi.class);
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

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Dosaggio.this);
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
}