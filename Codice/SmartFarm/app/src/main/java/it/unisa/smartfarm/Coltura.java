package it.unisa.smartfarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Coltura extends AppCompatActivity {
    private Account accountAttivo;
    String nomeColtura;
    private TextView tvTitoloColtura;
    private TextView tvProdotto;
    private TextView tvEttari;
    private TextView tvData;
    private TextView tvResa;
    private Button btn_elimina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coltura);

        tvTitoloColtura = findViewById(R.id.tvTitoloColtura);
        tvProdotto = findViewById(R.id.tv_prodotto);
        tvEttari = findViewById(R.id.tv_ettari);
        tvData = findViewById(R.id.tv_data);
        tvResa = findViewById(R.id.tv_resa);
        btn_elimina = findViewById(R.id.btn_elimina);

        Intent intent = getIntent();
        if (intent != null) {
            accountAttivo = (Account) intent.getSerializableExtra("account");
            nomeColtura = (String) intent.getSerializableExtra("coltura");

            tvTitoloColtura.setText(nomeColtura);

            if (!accountAttivo.getRuolo().equals("Amministratore"))
                btn_elimina.setVisibility(View.GONE);

            SharedPreferences spColture = getSharedPreferences(getResources().getString(R.string.file_colture), Context.MODE_PRIVATE);

            String colturaString = spColture.getString(nomeColtura, "");
            List<String> coltura = new ArrayList<>();

            if (!colturaString.isEmpty()){
                coltura = new ArrayList<>(Arrays.asList(colturaString.split(";")));

                Iterator<String> it = coltura.iterator();
                String prodotto = it.next();
                String ettari = it.next();
                String data = it.next();
                String resa = it.next();

                tvProdotto.setText(prodotto);
                tvEttari.setText(ettari);
                tvData.setText(data);
                tvResa.setText(resa);
            }
        }
    }

    public void eliminaColtura(View v) {
        View view = this.getLayoutInflater().inflate(R.layout.dialog_delete,null);

        Button conferma = view.findViewById(R.id.btn_conferma);
        Button annulla = view.findViewById(R.id.btn_annulla);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Coltura.this);
        alertDialogBuilder.setView(view);
        final AlertDialog dialog = alertDialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences spColture = getSharedPreferences(getResources().getString(R.string.file_colture), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spColture.edit();

                editor.remove(nomeColtura);
                editor.commit();

                SharedPreferences spDosaggi = getSharedPreferences(getResources().getString(R.string.file_dosaggi), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = spDosaggi.edit();

                editor2.remove(nomeColtura);
                editor2.commit();

                Intent i = new Intent();
                i.setClass(getApplicationContext(), Colture.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("account", accountAttivo);
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

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Coltura.this);
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
