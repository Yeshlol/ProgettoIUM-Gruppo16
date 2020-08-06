package it.unisa.smartfarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class HomePage extends AppCompatActivity {
    private Account accountAttivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.tastoHome).setBackgroundColor(getResources().getColor(R.color.light_gray));
        findViewById(R.id.tastoHome).setEnabled(false);

        Intent intent = getIntent();
        if(intent != null ){
            accountAttivo = (Account)intent.getSerializableExtra("account");
            SharedPreferences spColture = getSharedPreferences(getResources().getString(R.string.file_colture), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = spColture.edit();
            Set<String> set = new HashSet<String>();
            set.addAll(accountAttivo.getColture());
            editor.putStringSet(accountAttivo.getEmail(), set);
            editor.commit();

            if(accountAttivo.getRuolo().equals("Amministratore"))
                setContentView(R.layout.activity_homepage_admin);
            else if(accountAttivo.getRuolo().equals("Analista"))
                setContentView(R.layout.activity_homepage_analista);
            else
                setContentView(R.layout.activity_homepage_dipendente);
        }
    }

    public void homeCliccato(MenuItem item){
        Intent i = new Intent(this, HomePage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
        startActivity(i);
    }

    public void storicoCliccato(View v) {
        Intent i = new Intent(this, Storico.class);
        i.putExtra("account",accountAttivo);
        startActivity(i);
    }

    public void coltureCliccato(View v) {
        /*Intent i = new Intent(this, Colture.class);
        i.putExtra("account",accountAttivo);
        startActivity(i);*/
    }

    public void pianificaCliccato(View v) {
        /*Intent i = new Intent(this, Pianifica.class);
        i.putExtra("account",accountAttivo);
        startActivity(i);*/
    }

    public void dosaggiCliccato(View v) {
        /*Intent i = new Intent(this, Dosaggi.class);
        i.putExtra("account",accountAttivo);
        startActivity(i);*/
    }

    public void analisiCliccato(View v) {
        /*Intent i = new Intent(this, Analisi.class);
        i.putExtra("account",accountAttivo);
        startActivity(i);*/
    }
}