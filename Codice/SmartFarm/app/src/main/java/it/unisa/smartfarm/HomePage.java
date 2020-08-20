package it.unisa.smartfarm;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {
    private Account accountAttivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent != null ){
            accountAttivo = (Account) intent.getSerializableExtra("account");

            if(accountAttivo.getRuolo().equals("Amministratore"))
                setContentView(R.layout.activity_homepage_admin);
            else if(accountAttivo.getRuolo().equals("Analista"))
                setContentView(R.layout.activity_homepage_analista);
            else
                setContentView(R.layout.activity_homepage_dipendente);
        }

        findViewById(R.id.tastoHome).setBackgroundColor(getResources().getColor(R.color.light_gray));
        findViewById(R.id.tastoHome).setEnabled(false);
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

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomePage.this);
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

    public void storicoCliccato(View v) {
        Intent i = new Intent(this, Storico.class);
        i.putExtra("account", accountAttivo);
        startActivity(i);
    }

    public void coltureCliccato(View v) {
        Intent i = new Intent(this, Colture.class);
        i.putExtra("account",accountAttivo);
        startActivity(i);
    }

    public void pianificaCliccato(View v) {
        Intent i = new Intent(this, Pianifica.class);
        i.putExtra("account",accountAttivo);
        startActivity(i);
    }

    public void dosaggiCliccato(View v) {
        Intent i = new Intent(this, Dosaggi.class);
        i.putExtra("account",accountAttivo);
        startActivity(i);
    }

    public void analisiCliccato(View v) {
        Intent i = new Intent(this, AnalisiDati.class);
        i.putExtra("account",accountAttivo);
        startActivity(i);
    }
}