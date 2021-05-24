package src.Controler.Graficas;

import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import com.example.oneconomy.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import src.Model.DatoGrafica;

public class GraficaBarras extends AppCompatActivity {

    private BarChart barChartT;
    private BarChart barChartC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica_barras);
        barChartT = findViewById(R.id.barchart_x);
        barChartC = findViewById(R.id.barchart_y);
        CheckBox ingreso = (CheckBox) findViewById(R.id.checkBoxIB);
        CheckBox egreso = (CheckBox) findViewById(R.id.checkBoxEB);
        setUp(barChartT);
        try {
            cargarDatos();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setUp(barChartC);
    }

    private void setUp(BarChart barChart){
        barChart.setFitBars(true);
        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void cargarDatos() throws InterruptedException {
        ArrayList<BarEntry> entradas = new ArrayList<>();
        ArrayList<DatoGrafica> orgData = src.Libraries.Utils.analisisDatos();
        ArrayList<String> xLabel = new ArrayList<>();

        float ing = 0f;
        float egr = 0f;

        for(DatoGrafica dato : orgData){
            ing += dato.getIngreso();
            egr += dato.getEgreso();
        }

        System.out.println("ING-> " + ing + " EGR-> " + egr);
        xLabel.add("Ingreso");
        entradas.add(new BarEntry(1, ing));
        xLabel.add("Egreso");
        entradas.add(new BarEntry(2, egr));

        ArrayList<Integer> colores = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS) {
            colores.add(c);
        }
        BarDataSet set = new BarDataSet(entradas, "Categorias");
        set.setColors(colores);
        BarData data = new BarData(set);
        data.setDrawValues(true);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        barChartT.setData(data);
        XAxis x = barChartT.getXAxis();
        x.setValueFormatter(new IndexAxisValueFormatter(xLabel));
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setDrawAxisLine(false);
        x.setLabelCount(xLabel.size());
        x.setTextSize(14f);
        x.setGranularity(1f);
        barChartT.animateX(2000);
        barChartT.invalidate();
    }

    private void cargarDatosPorCategoria(String tipo) throws InterruptedException {
        ArrayList<BarEntry> entradas = new ArrayList<>();
        ArrayList<DatoGrafica> orgData = src.Libraries.Utils.analisisDatos();
        ArrayList<String> xLabel = new ArrayList<>();

        float ing = 0f;
        float egr = 0f;
        int i = 0;

        for(DatoGrafica dato : orgData){
            if(tipo.equals("Ingreso")) {
                if(dato.getIngreso() != 0f){
                    entradas.add(new BarEntry(i, dato.getIngreso()));
                    xLabel.add(dato.getNombre());
                }
            }else {
                if(dato.getEgreso() != 0f){
                    entradas.add(new BarEntry(i, dato.getEgreso()));
                    xLabel.add(dato.getNombre());
                }
            }
            i++;
        }

        ArrayList<Integer> colores = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
            colores.add(c);
        }
        BarDataSet set = new BarDataSet(entradas, "Categorias");
        set.setColors(colores);
        BarData data = new BarData(set);
        data.setDrawValues(true);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        barChartC.setData(data);
        XAxis x = barChartC.getXAxis();
        x.setValueFormatter(new IndexAxisValueFormatter(xLabel));
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setDrawAxisLine(false);
        x.setLabelCount(xLabel.size());
        x.setTextSize(14f);
        x.setGranularity(1f);
        barChartC.animateX(2000);
        barChartC.invalidate();
    }

    public void checkIB(View view) {
        CheckBox egreso = (CheckBox) findViewById(R.id.checkBoxEB);
        if (egreso.isChecked()) {
            egreso.setChecked(false);
        }
        try {
            cargarDatosPorCategoria("Ingreso");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkEB(View view) {
        CheckBox ingreso = (CheckBox) findViewById(R.id.checkBoxIB);
        if (ingreso.isChecked()) {
            ingreso.setChecked(false);
        }
        try {
            cargarDatosPorCategoria("Egreso");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}