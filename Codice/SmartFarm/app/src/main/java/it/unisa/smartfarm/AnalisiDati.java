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

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class AnalisiDati extends AppCompatActivity {
    private Account accountAttivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analisi);

        Intent intent = getIntent();
        if (intent != null) {
            accountAttivo = (Account) intent.getSerializableExtra("account");

            AnyChartView anyChartView = findViewById(R.id.chartView);
            Cartesian cartesian = AnyChart.column();

            List<DataEntry> data = new ArrayList<>();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
            String dataString;
            Random rnd = new Random();
            calendar.add(Calendar.DATE,-6);
            for(int i = 0 ; i < 6; i++){
                Date dataSuccessiva = calendar.getTime();
                dataString = dateFormat.format(dataSuccessiva);
                data.add(new ValueDataEntry(dataString,rnd.nextInt(15)+20 ));
                calendar.add(Calendar.DATE, 1 );
            }

            Date dataOggi = calendar.getTime();
            dataString = dateFormat.format(dataOggi);
            data.add(new ValueDataEntry(dataString, 30));

            Column column = cartesian.column(data);

            column.tooltip()
                    .titleFormat("{%X}")
                    .position(Position.CENTER_BOTTOM)
                    .anchor(Anchor.CENTER_BOTTOM)
                    .offsetX(0d)
                    .offsetY(5d)
                    .format("${%Value}{groupsSeparator: }");

            cartesian.animation(true);
            cartesian.yScale().minimum(0d);
            cartesian.yAxis(0).labels().format("{%Value}°C");

            cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
            cartesian.interactivity().hoverMode(HoverMode.BY_X);

            cartesian.xAxis(0).title("Giorni");
            cartesian.yAxis(0).title("Temperatura");

            anyChartView.setChart(cartesian);
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

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AnalisiDati.this);
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