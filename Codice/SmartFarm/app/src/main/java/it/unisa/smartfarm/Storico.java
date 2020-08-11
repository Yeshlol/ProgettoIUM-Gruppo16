package it.unisa.smartfarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class Storico extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private static final String[] anni = {"2019", "2020"};
    private TableLayout table;
    private Account accountAttivo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico);

        Intent intent = getIntent();
        accountAttivo = (Account) intent.getSerializableExtra("account");

        table = (TableLayout) findViewById(R.id.tableLayout);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Storico.this,
                android.R.layout.simple_spinner_item, anni);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        table.removeAllViewsInLayout();

        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);
        TextView tv5 = new TextView(this);
        TextView tv6 = new TextView(this);

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;

        TableRow row = new TableRow(this);
        row.setLayoutParams(lp);
        row.setBackgroundResource(R.color.colorPrimary);

        TableRow row2 = new TableRow(this);
        row2.setLayoutParams(lp);
        row2.setBackgroundResource(R.color.colorGreen);

        TableRow row3 = new TableRow(this);
        row3.setLayoutParams(lp);
        row3.setBackgroundResource(R.color.colorGreen);

        tv1.setText("Prodotti");
        tv1.setGravity(Gravity.CENTER);

        tv2.setText("Quantità");
        tv2.setGravity(Gravity.CENTER);

        row.addView(tv1);
        row.addView(tv2);

        table.addView(row);

        switch (position) {
            case 0:
                tv3.setText("Cavolo");
                tv3.setGravity(Gravity.CENTER);

                tv4.setText("10,5 Tonnellate");
                tv4.setGravity(Gravity.CENTER);

                row2.addView(tv3);
                row2.addView(tv4);

                table.addView(row2, 1);

                tv5.setText("Carota");
                tv5.setGravity(Gravity.CENTER);

                tv6.setText("6,7 Tonnellate");
                tv6.setGravity(Gravity.CENTER);

                row3.addView(tv5);
                row3.addView(tv6);

                table.addView(row3, 2);

                break;
            case 1:
                tv3.setText("Patata");
                tv3.setGravity(Gravity.CENTER);

                tv4.setText("20,5 Tonnellate");
                tv4.setGravity(Gravity.CENTER);

                row2.addView(tv3);
                row2.addView(tv4);

                table.addView(row2, 1);

                tv5.setText("Zucchina");
                tv5.setGravity(Gravity.CENTER);

                tv6.setText("9,8 Tonnellate");
                tv6.setGravity(Gravity.CENTER);

                row3.addView(tv5);
                row3.addView(tv6);

                table.addView(row3, 2);

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Storico.this);
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
