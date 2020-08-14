package it.unisa.smartfarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Colture extends AppCompatActivity {
    private TableLayout table;
    private Account accountAttivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colture);

        Intent intent = getIntent();
        accountAttivo = (Account) intent.getSerializableExtra("account");

        table = (TableLayout) findViewById(R.id.tableLayout);

        SharedPreferences spColture = getSharedPreferences(getResources().getString(R.string.file_colture), Context.MODE_PRIVATE);
        Map map = spColture.getAll();

        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;

        TableRow row = new TableRow(this);
        row.setLayoutParams(lp);
        row.setBackgroundResource(R.color.colorPrimary);

        tv1.setText("Prodotto");
        tv1.setGravity(Gravity.CENTER);

        tv2.setText("Ettari");
        tv2.setGravity(Gravity.CENTER);

        tv3.setText("Data Semina");
        tv3.setGravity(Gravity.CENTER);

        tv4.setText("Resa");
        tv4.setGravity(Gravity.CENTER);

        row.addView(tv1);
        row.addView(tv2);
        row.addView(tv3);
        row.addView(tv4);

        table.addView(row);

        for (Object c : map.keySet()) {
            String nomeColtura = c.toString();

            TableRow row2 = new TableRow(this);
            row2.setLayoutParams(lp);
            row2.setBackgroundResource(R.color.colorGreen);

            TextView tv5 = new TextView(this);
            TextView tv6 = new TextView(this);
            TextView tv7 = new TextView(this);
            TextView tv8 = new TextView(this);

            String colturaString = spColture.getString(nomeColtura, "");
            List<String> coltura = new ArrayList<>();
            if (!colturaString.isEmpty()){
                coltura = new ArrayList<>(Arrays.asList(colturaString.split(";")));

                Iterator<String> it = coltura.iterator();
                String prodotto = it.next();
                String ettari = it.next();
                String data = it.next();
                String resa = it.next();

                String uri = "@drawable/border";
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);

                tv5.setText(prodotto);
                tv5.setGravity(Gravity.CENTER);
                tv5.setBackground(res);
                tv6.setText(ettari);
                tv6.setGravity(Gravity.CENTER);
                tv6.setBackground(res);
                tv7.setText(data);
                tv7.setGravity(Gravity.CENTER);
                tv7.setBackground(res);
                tv8.setText(resa);
                tv8.setGravity(Gravity.CENTER);
                tv8.setBackground(res);

                row2.addView(tv5);
                row2.addView(tv6);
                row2.addView(tv7);
                row2.addView(tv8);

                table.addView(row2);
            }
        }
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
