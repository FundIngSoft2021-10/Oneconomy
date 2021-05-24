package src.Controler.Graficas;

import androidx.appcompat.app.AppCompatActivity;
import com.example.oneconomy.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

import src.Model.DatoGrafica;

public class GraficaBarrasH extends AppCompatActivity {

    HorizontalBarChart horizontalBarChartT;
    HorizontalBarChart horizontalBarChartC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica_barras_h);
        horizontalBarChartT = findViewById(R.id.barcharth_x);
        horizontalBarChartC = findViewById(R.id.barcharth_y);
        CheckBox ingreso = (CheckBox) findViewById(R.id.checkBoxIB);
        CheckBox egreso = (CheckBox) findViewById(R.id.checkBoxEB);
        setUp(horizontalBarChartT);
        try {
            cargarDatos();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setUp(horizontalBarChartC);
    }

    private void setUp(HorizontalBarChart horizontalBarChart){
        horizontalBarChart.setFitBars(true);
        Legend l = horizontalBarChart.getLegend();
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
        horizontalBarChartT.setData(data);
        XAxis x = horizontalBarChartT.getXAxis();
        x.setValueFormatter(new IndexAxisValueFormatter(xLabel));
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setDrawAxisLine(false);
        x.setLabelCount(xLabel.size());
        x.setTextSize(14f);
        x.setGranularity(1f);
        horizontalBarChartT.animateY(2000);
        horizontalBarChartT.invalidate();
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
        horizontalBarChartC.setData(data);
        XAxis x = horizontalBarChartC.getXAxis();
        x.setValueFormatter(new IndexAxisValueFormatter(xLabel));
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setDrawAxisLine(false);
        x.setLabelCount(xLabel.size());
        x.setTextSize(14f);
        x.setGranularity(1f);
        horizontalBarChartC.animateX(2000);
        horizontalBarChartC.invalidate();
    }

    public void checkIBH(View view) {
        CheckBox egreso = (CheckBox) findViewById(R.id.checkBoxEBH);
        if (egreso.isChecked()) {
            egreso.setChecked(false);
        }
        try {
            cargarDatosPorCategoria("Ingreso");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkEBH(View view) {
        CheckBox ingreso = (CheckBox) findViewById(R.id.checkBoxIBH);
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