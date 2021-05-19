package src.Controler.Graficas;

import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import com.example.oneconomy.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import src.Model.DatoGrafica;

import com.example.oneconomy.R;

public class GraficaLineal extends AppCompatActivity {

    LineChart lineChartT;
    LineChart lineChartC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica_lineal);
        lineChartT = findViewById(R.id.linchart_x);
        lineChartC = findViewById(R.id.linchart_y);
        CheckBox ingreso = (CheckBox) findViewById(R.id.checkBoxIL);
        CheckBox egreso = (CheckBox) findViewById(R.id.checkBoxEL);
        setUp(lineChartT);
        try {
            cargarDatos();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setUp(lineChartC);
    }

    private void setUp(LineChart lineChart){
        Legend l = lineChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void cargarDatos() throws InterruptedException {
        ArrayList<Entry> entradas = new ArrayList<>();
        ArrayList<DatoGrafica> orgData = src.Libraries.Utils.analisisDatos();

        float ing = 0f;
        float egr = 0f;

        for(DatoGrafica dato : orgData){
            ing += dato.getIngreso();
            egr += dato.getEgreso();
        }

        System.out.println("ING-> " + ing + " EGR-> " + egr);
        entradas.add(new Entry(1, ing));
        entradas.add(new Entry(2, egr));

        ArrayList<Integer> colores = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS) {
            colores.add(c);
        }
        LineDataSet set = new LineDataSet(entradas, "Categorias");
        set.setColors(colores);
        LineData data = new LineData(set);
        data.setDrawValues(true);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        lineChartT.setData(data);
        lineChartT.invalidate();
    }

    public void checkEL(View view) {
    }

    public void checkIL(View view) {
    }
}