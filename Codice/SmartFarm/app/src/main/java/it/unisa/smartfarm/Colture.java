package it.unisa.smartfarm;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Map;

public class Colture extends AppCompatActivity {
    private LinearLayout containerListaColture;
    private Account accountAttivo;
    private TextView tvTitolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco);

        tvTitolo = findViewById(R.id.tvTitolo);
        tvTitolo.setText(getResources().getString(R.string.colture));

        containerListaColture = findViewById(R.id.containerListaColture);
        Intent intent = getIntent();
        if (intent != null) {
            accountAttivo = (Account) intent.getSerializableExtra("account");
            SharedPreferences spColture = getSharedPreferences(getResources().getString(R.string.file_colture), Context.MODE_PRIVATE);

            Map map = spColture.getAll();

            for (Object c : map.keySet()) {
                String nomeColtura = c.toString();
                creaColtura(nomeColtura);
            }
        }
    }

    void creaColtura(final String nomeColtura){
        LinearLayout ll = new LinearLayout(this);
        getLayoutInflater().inflate(R.layout.list_element,ll);
        Button buttonColtura = ll.findViewById(R.id.buttonColtura);
        buttonColtura.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_primary_bg));
        buttonColtura.setText(nomeColtura);

        @SuppressLint({"NewApi", "LocalSuppress"}) Typeface face = getResources().getFont(R.font.opensans_bold);
        buttonColtura.setTypeface(face);

        buttonColtura.setTextColor(getResources().getColor(R.color.black));
        buttonColtura.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Coltura.class);
                i.putExtra("coltura", nomeColtura);
                i.putExtra("account",accountAttivo);
                startActivity(i);
            }
        });
        ll.setTag(nomeColtura);
        containerListaColture.addView(ll);
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

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Colture.this);
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