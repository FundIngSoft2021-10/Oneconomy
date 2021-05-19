package src.Controler.Graficas;

import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import com.example.oneconomy.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import src.Model.DatoGrafica;

public class GraficaPastel extends AppCompatActivity {

    private PieChart pieChartT;
    private PieChart pieChartC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica_pastel);
        pieChartT = findViewById(R.id.piechart_x);
        pieChartC = findViewById(R.id.piechart_y);
        CheckBox ingreso = (CheckBox) findViewById(R.id.checkBoxIP);
        CheckBox egreso = (CheckBox) findViewById(R.id.checkBoxEP);
        setUp(pieChartT, "Ingresos/Egresos Totales");
        try {
            cargarDatos();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setUp(PieChart pieChart, String s){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(20f);
        pieChart.setCenterTextSize(20f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText(s);
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }



    private void cargarDatos() throws InterruptedException {
        ArrayList<PieEntry> entradas = new ArrayList<>();
        ArrayList<DatoGrafica> orgData = src.Libraries.Utils.analisisDatos();

        float ing = 0f;
        float egr = 0f;

        for(DatoGrafica dato : orgData){
            ing += dato.getIngreso();
            egr += dato.getEgreso();
        }

        System.out.println("ING-> " + ing + " EGR-> " + egr);
        entradas.add(new PieEntry(ing, "Ingresos"));
        entradas.add(new PieEntry(egr, "Egresos"));

        ArrayList<Integer> colores = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS) {
            colores.add(c);
        }
        PieDataSet set = new PieDataSet(entradas, "Categorias");
        set.setColors(colores);
        PieData data = new PieData(set);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChartT));
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);
        pieChartT.setData(data);
        pieChartT.invalidate();
    }

    private void cargarDatosPorCategoria(String tipo) throws InterruptedException {
        ArrayList<PieEntry> entradas = new ArrayList<>();
        ArrayList<DatoGrafica> orgData = src.Libraries.Utils.analisisDatos();

        float ing = 0f;
        float egr = 0f;

        for(DatoGrafica dato : orgData){
            if(tipo.equals("Ingreso")) {
                if(dato.getIngreso() != 0f){
                    entradas.add(new PieEntry(dato.getIngreso(), dato.getNombre()));
                }
            }else {
                if(dato.getEgreso() != 0f){
                    entradas.add(new PieEntry(dato.getEgreso(), dato.getNombre()));
                }
            }
        }

        ArrayList<Integer> colores = new ArrayList<>();
        for (int c : ColorTemplate.COLORFUL_COLORS) {
            colores.add(c);
        }
        PieDataSet set = new PieDataSet(entradas, "Categorias");
        set.setColors(colores);
        PieData data = new PieData(set);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChartC));
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);
        pieChartC.setData(data);
        pieChartC.invalidate();
    }

    public void checkIP(View view) {
        CheckBox egreso = (CheckBox) findViewById(R.id.checkBoxEP);
        if (egreso.isChecked()) {
            egreso.setChecked(false);
        }
        try {
            setUp(pieChartC, "Total de ingreso por categoria");
            cargarDatosPorCategoria("Ingreso");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkEP(View view) {
        CheckBox ingreso = (CheckBox) findViewById(R.id.checkBoxIP);
        if (ingreso.isChecked()) {
            ingreso.setChecked(false);
        }
        try {
            setUp(pieChartC, "Total de egreso por categoria");
            cargarDatosPorCategoria("Egreso");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}