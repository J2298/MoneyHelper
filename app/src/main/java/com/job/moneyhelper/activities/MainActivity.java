package com.job.moneyhelper.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.job.moneyhelper.models.Operacion;
import com.job.moneyhelper.R;
import com.job.moneyhelper.repositories.Repository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = NewOperationActivity.class.getSimpleName();
    private static int REGISTER_FORM_REQUEST = 100;
    private TextView saldoahorro;
    private TextView saldocredito;
    private TextView saldoefectivo;
    private ProgressBar barra1;
    PieChart pieChart;

    String[] xData = {"Ingreso", "Egreso"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saldoahorro = (TextView) findViewById(R.id.saldoAhorro);
        saldoefectivo = (TextView) findViewById(R.id.saldoEfectivo);
        saldocredito = (TextView) findViewById(R.id.saldoCredito);
        barra1 = (ProgressBar) findViewById(R.id.barra1);
        pieChart = (PieChart) findViewById(R.id.grafica);
    }

    public void agregar(View view) {
        startActivityForResult(new Intent(this, NewOperationActivity.class), REGISTER_FORM_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Repository opeRepository = Repository.getInstance();
        List<Operacion> operaciones = opeRepository.getGastos();

        double sum_aho = 0;
        double sum_cre = 0;
        double sum_efe = 0;
        double sum_ing = 0;
        double sum_eg = 0;

        for (Operacion operacion : operaciones) {
            //   Toast.makeText(this,operacion.getCuenta(), Toast.LENGTH_SHORT).show();

            if (operacion.getTipo().equals("Egreso") && operacion.getMonto() > sum_ing) {
                Toast.makeText(getApplicationContext(), "Operacion Invalida", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Intente Nuevamente", Toast.LENGTH_SHORT).show();
            } else {
                if (operacion.getTipo().equals("Ingreso")) {
                    sum_ing = sum_ing + operacion.getMonto();
                } else {
                    sum_eg = sum_eg + operacion.getMonto();
                }
                if (operacion.getCuenta().equals("Ahorro")) {
                    if (operacion.getTipo().equals("Ingreso")) {
                        sum_aho = sum_aho + operacion.getMonto();
                    } else if (operacion.getTipo().equals("Egreso") && sum_aho != 0) {
                        sum_aho = sum_aho - operacion.getMonto();
                    }
                } else if (operacion.getCuenta().equals("Tarjeta de Credito")) {
                    if (operacion.getTipo().equals("Ingreso")) {
                        sum_cre = sum_cre + operacion.getMonto();
                    } else if (operacion.getTipo().equals("Egreso") && sum_cre != 0) {
                        sum_cre = sum_cre - operacion.getMonto();
                    }
                } else if (operacion.getCuenta().equals("Efectivo")) {
                    if (operacion.getTipo().equals("Ingreso")) {
                        sum_efe = sum_efe + operacion.getMonto();
                    } else if (operacion.getTipo().equals("Egreso") && sum_efe != 0) {
                        sum_efe = sum_efe - operacion.getMonto();
                    }
                }
            }

            saldoahorro.setText("S/. " + sum_aho);
            saldocredito.setText("S/. " + sum_cre);
            saldoefectivo.setText("S/. " + sum_efe);

            double sum_tot;
            sum_tot = sum_ing + sum_eg;
            double porcent = (sum_ing * 100) / sum_tot;
            double porcent1 = 100 - porcent;
            barra1.setProgress((int) porcent);

            float[] yData = {(float) porcent, (float) porcent1};
            ArrayList<PieEntry> yEntrys = new ArrayList<>();
            ArrayList<String> xEntrys = new ArrayList<>();

            for (int i = 0; i < yData.length; i++) {
                yEntrys.add(new PieEntry(yData[i], i));
            }

            for (int i = 0; i < xData.length; i++) {
                xEntrys.add(xData[i]);
            }

            PieDataSet pieDataSet = new PieDataSet(yEntrys, " ");

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.CYAN);
            colors.add(Color.YELLOW);
            pieDataSet.setColors(colors);
            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.invalidate();
        }
    }
}
