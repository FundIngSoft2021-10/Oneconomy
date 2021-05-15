package src.Controler.Graficas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneconomy.R;

import java.util.ArrayList;

import src.Controler.HistorialMovimientos;
import src.Controler.Mis_Informes;

public class MisGraficas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_graficas);
        System.out.println("******* lllll ********");
        pruebaLectura();
        System.out.println("******* lllll ********");
    }

    public void graficaPastel(View view) {
        Intent i = new Intent(this, GraficaPastel.class);
        startActivity(i);
    }

    public void pruebaLectura() {
        for (ArrayList<String> actual : Mis_Informes.getListOListsMovimiento()) {
            System.out.println(actual.get(0)+","+actual.get(1)+","+actual.get(2)+
                    ","+actual.get(3)+","+actual.get(4)+","+actual.get(5)+","+actual.get(6)+","+actual.get(7));
        }
    }
}