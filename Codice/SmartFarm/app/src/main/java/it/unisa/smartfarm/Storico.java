package it.unisa.smartfarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Storico extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private static final String[] paths = {"2019", "2020"};
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico);

        table = (TableLayout) findViewById(R.id.tableLayout);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Storico.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView tv = new TextView(this);
        TextView tv2 = new TextView(this);

        tv.setText("Prodotti");
        tv2.setText("Quantità");

        row.addView(tv);
        row.addView(tv2);

        table.addView(row, 0);

        switch (position) {
            case 0:
                tv.setText("Cavolo");
                tv2.setText("10,5 Tonnellate");

                row.addView(tv);
                row.addView(tv2);

                table.addView(row, 1);

                tv.setText("Carota");
                tv2.setText("6,7 Tonnellate");

                row.addView(tv);
                row.addView(tv2);

                table.addView(row, 2);

                break;
            case 1:
                tv.setText("Patate");
                tv2.setText("20,5 Tonnellate");

                row.addView(tv);
                row.addView(tv2);

                table.addView(row, 1);

                tv.setText("Zucchina");
                tv2.setText("9,8 Tonnellate");

                row.addView(tv);
                row.addView(tv2);

                table.addView(row, 2);

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
