package src.Controler.Graficas;

import android.os.Bundle;
import android.graphics.Color;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

public class GraficaPastel extends AppCompatActivity {


    private PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica_pastel);
        pieChart = findViewById(R.id.piechart_x);
        setUp();
        cargarDatos();
    }



    private void setUp(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Muestra por categoria");
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void cargarDatos() {
        ArrayList<PieEntry> entradas = new ArrayList<>();
        entradas.add(new PieEntry(0.2f, "Bancos"));
        entradas.add(new PieEntry(0.9f, "Entretenimiento"));
        entradas.add(new PieEntry(0.1f, "Educacion"));
        ArrayList<Integer> colores = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS) {
            colores.add(c);
        }
        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
            colores.add(c);
        }
        PieDataSet set = new PieDataSet(entradas, "Categorias");
        set.setColors(colores);
        PieData data = new PieData(set);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
        pieChart.invalidate();
    }
    //aqui iria la funcion del llamado get a movimientos en el servidor
}